package school.sptech.acdnbemailservice.core.application.usecase;

import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;
import school.sptech.acdnbemailservice.infrastructure.security.config.EmailProperties;

import java.util.Properties;

@Service
public class GerarMensagemRetornoUseCaseImpl implements GerarMensagemRetornoUseCase {

    private final EmailProperties emailProperties;

    public GerarMensagemRetornoUseCaseImpl(EmailProperties emailProperties) {
        this.emailProperties = emailProperties;
    }

    @Override
    public void execute(String destinatario, String mensagem) throws Exception {

        String username = emailProperties.getUsername();
        String password = emailProperties.getPassword();

        System.out.println("🔐 Usando email: " + username);

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new jakarta.mail.Authenticator() {
                    protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new jakarta.mail.PasswordAuthentication(username, password);
                    }
                });

        Message email = new MimeMessage(session);
        email.setFrom(new InternetAddress(username));
        email.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        email.setSubject("Confirmação de recebimento do comprovante");
        email.setText(mensagem);

        Transport.send(email);

        System.out.println("📨 Email de retorno enviado para: " + destinatario);
    }
}