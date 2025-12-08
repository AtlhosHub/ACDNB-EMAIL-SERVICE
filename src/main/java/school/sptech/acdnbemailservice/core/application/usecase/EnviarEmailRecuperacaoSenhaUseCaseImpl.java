package school.sptech.acdnbemailservice.core.application.usecase;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import school.sptech.acdnbemailservice.infrastructure.dto.EmailRecuperacaoSenhaDTO;

@Service
public class EnviarEmailRecuperacaoSenhaUseCaseImpl implements EnviarEmailRecuperacaoSenhaUseCase {

    private final JavaMailSender mailSender;

    @Value("${mail.username}")
    private String remetente;

    private static final String ASSUNTO = "Recuperação de Senha - Sistema CT Vila Formosa";
    private static final String NOME_REMETENTE = "CT Vila Formosa";

    public EnviarEmailRecuperacaoSenhaUseCaseImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void execute(EmailRecuperacaoSenhaDTO dto) {
        try {
            System.out.println("📧 Processando email de recuperação para: " + dto.getEmail());

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(remetente, NOME_REMETENTE);
            helper.setTo(dto.getEmail());
            helper.setSubject(ASSUNTO);
            helper.setReplyTo(remetente);

            String htmlContent = construirHtmlEmail(dto);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            System.out.println("✅ Email enviado com sucesso de: " + remetente + " para: " + dto.getEmail());

        } catch (Exception e) {
            System.err.println("❌ Erro ao enviar email: " + e.getMessage());
            throw new RuntimeException("Falha ao enviar email de recuperação", e);
        }
    }

    private String construirHtmlEmail(EmailRecuperacaoSenhaDTO dto) {
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    body { font-family: Arial, sans-serif; background: #f4f4f4; margin: 0; padding: 20px; }
                    .container { max-width: 600px; margin: auto; background: white; border-radius: 10px; padding: 30px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
                    .header { background: #0D3C53; color: white; padding: 20px; text-align: center; border-radius: 10px 10px 0 0; }
                    .logo { font-size: 24px; font-weight: bold; }
                    .subtitle { font-size: 14px; opacity: 0.9; }
                    .content { padding: 20px; }
                    .greeting { font-size: 18px; color: #0D3C53; margin-bottom: 20px; }
                    .button { display: inline-block; background: #0D3C53; color: white; padding: 12px 24px; text-decoration: none; border-radius: 5px; margin: 20px 0; }
                    .link-box { background: #f8f9fa; padding: 15px; border-radius: 5px; border-left: 4px solid #0D3C53; margin: 15px 0; word-break: break-all; }
                    .warning { background: #fff3cd; border: 1px solid #ffeaa7; padding: 15px; border-radius: 5px; margin: 20px 0; }
                    .footer { margin-top: 30px; padding-top: 20px; border-top: 1px solid #eee; text-align: center; font-size: 12px; color: #666; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <div class="logo">CT Vila Formosa</div>
                        <div class="subtitle">Sistema de Gerenciamento Financeiro</div>
                    </div>
                    
                    <div class="content">
                        <div class="greeting">Olá, <strong>%s</strong>!</div>
                        <p>Recebemos uma solicitação para redefinir sua senha.</p>
                        
                        <div style="text-align: center; margin: 25px 0;">
                            <a href="%s" class="button">Redefinir Senha</a>
                        </div>
                        
                        <p>Ou copie o link:</p>
                        <div class="link-box">%s</div>
                        
                        <div class="warning">
                            ⚠️ <strong>Atenção:</strong> Este link expira em 24 horas.
                        </div>
                        
                        <p>Se não solicitou, ignore este email.</p>
                    </div>
                    
                    <div class="footer">
                        <p>Email automático - Não responda</p>
                        <p>© %d CT Vila Formosa</p>
                    </div>
                </div>
            </body>
            </html>
            """,
                dto.getNome(),
                dto.getLinkRecuperacao(),
                dto.getLinkRecuperacao(),
                java.time.Year.now().getValue()
        );
    }
}