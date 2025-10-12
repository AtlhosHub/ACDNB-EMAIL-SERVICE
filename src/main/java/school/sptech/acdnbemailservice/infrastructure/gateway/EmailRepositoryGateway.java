package school.sptech.acdnbemailservice.infrastructure.gateway;

import jakarta.mail.*;
import jakarta.mail.internet.MimeBodyPart;
import org.springframework.stereotype.Component;
import school.sptech.acdnbemailservice.core.application.gateway.EmailGateway;
import school.sptech.acdnbemailservice.infrastructure.security.config.EmailProperties;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Component
public class EmailRepositoryGateway implements EmailGateway {

    private final EmailProperties emailProperties;

    public EmailRepositoryGateway(EmailProperties emailProperties) {
        this.emailProperties = emailProperties;
    }

    @Override
    public Store conectar() throws Exception {
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");

        Session session = Session.getDefaultInstance(props, null);
        Store store = session.getStore("imaps");
        store.connect(
                "imap.gmail.com",
                emailProperties.getUsername(),
                emailProperties.getPassword()
        );

        return store;
    }


    @Override
    public List<Message> lerEmailsNaoLidos(Store store) throws MessagingException {
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_WRITE);

        Message[] messages = inbox.getMessages();
        List<Message> naoLidos = Arrays.stream(messages)
                .filter(msg -> {
                    try {
                        return !msg.isSet(Flags.Flag.SEEN);
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        return naoLidos;
    }

    @Override
    public void marcarComoLidos(Store store, List<Message> emails) throws MessagingException {
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_WRITE);

        for (Message msg : emails) {
            msg.setFlag(Flags.Flag.SEEN, true);
        }

        inbox.close(false);
    }

    @Override
    public List<File> extrairAnexos(Message message) throws Exception {
        List<File> anexos = new ArrayList<>();

        if (message .isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) message.getContent();

            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);

                if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())
                        || bodyPart.getFileName() != null) {
                    MimeBodyPart mimeBodyPart = (MimeBodyPart) bodyPart;

                    File file = File.createTempFile("anexo-", "-" + mimeBodyPart.getFileName());
                    try (InputStream is = mimeBodyPart.getInputStream();
                         FileOutputStream fos = new FileOutputStream(file)) {
                        byte[] buf = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = is.read(buf)) != -1) {
                            fos.write(buf, 0, bytesRead);
                        }
                    }

                    anexos.add(file);
                }
            }
        }

        return anexos;
    }
}
