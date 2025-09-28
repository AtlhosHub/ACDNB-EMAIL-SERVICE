package school.sptech.acdnbemailservice.infrastructure.gateway;

import jakarta.mail.*;
import org.springframework.stereotype.Component;
import school.sptech.acdnbemailservice.core.application.gateway.EmailGateway;
import school.sptech.acdnbemailservice.infrastructure.security.config.EmailProperties;

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

        inbox.close(false);
        return naoLidos;
    }

    @Override
    public void marcarComoLidos(List<Message> emails) throws MessagingException {
        for (Message msg : emails) {
            msg.setFlag(Flags.Flag.SEEN, true);
        }
    }
}
