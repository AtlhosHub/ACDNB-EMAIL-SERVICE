package school.sptech.acdnbemailservice.infrastructure.security.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailAutoConfig {

    @Value("${mail.host:smtp.gmail.com}")
    private String host;

    @Value("${mail.port:587}")
    private Integer port;

    @Value("${mail.username:#{null}}")
    private String username;

    @Value("${mail.password:#{null}}")
    private String password;

    @PostConstruct
    public void init() {
        System.out.println("📧 Configurando JavaMailSender:");
        System.out.println("   Host: " + host);
        System.out.println("   Port: " + port);
        System.out.println("   Username: " + (username != null ? "configurado" : "não configurado"));
    }

    @Bean
    @Primary
    public JavaMailSender javaMailSender() {
        if (username == null || password == null) {
            System.err.println("⚠️ Credenciais de email não configuradas. Usando mock.");
            return criarMailSenderMock();
        }

        try {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost(host);
            mailSender.setPort(port);
            mailSender.setUsername(username);
            mailSender.setPassword(password);

            var props = mailSender.getJavaMailProperties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.starttls.required", "true");
            props.put("mail.smtp.connectiontimeout", "5000");
            props.put("mail.smtp.timeout", "5000");
            props.put("mail.smtp.writetimeout", "5000");

            System.out.println("✅ JavaMailSender configurado com sucesso");
            return mailSender;

        } catch (Exception e) {
            System.err.println("❌ Erro ao configurar JavaMailSender: " + e.getMessage());
            return criarMailSenderMock();
        }
    }

    private JavaMailSender criarMailSenderMock() {
        return new JavaMailSenderImpl() {
            @Override
            public void send(org.springframework.mail.SimpleMailMessage simpleMessage) {
                System.out.println("📧 [MOCK] Simulando envio de email para: " +
                        java.util.Arrays.toString(simpleMessage.getTo()));
            }

            @Override
            public void send(jakarta.mail.internet.MimeMessage... mimeMessages) {
                System.out.println("📧 [MOCK] Simulando envio de " + mimeMessages.length + " email(s)");
            }
        };
    }
}
