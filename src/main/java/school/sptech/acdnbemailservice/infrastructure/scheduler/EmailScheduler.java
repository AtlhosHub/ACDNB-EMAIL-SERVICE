package school.sptech.acdnbemailservice.infrastructure.scheduler;

import jakarta.mail.Message;
import jakarta.mail.Store;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import school.sptech.acdnbemailservice.core.application.usecase.*;
import school.sptech.acdnbemailservice.infrastructure.dto.ComprovanteDto;

import java.io.File;
import java.util.List;

@Component
public class EmailScheduler {

    private final LerEmailsNaoLidosUseCase lerEmailsNaoLidosUseCase;
    private final ValidarRemetenteUseCase validarRemetenteUseCase;
    private final ConectarEmailUseCase conectarEmailUseCase;
    private final ExtrairAnexosUseCase extrairAnexosUseCase;
    private final ConverterListaAnexoUseCase converterListaAnexouseCase;
    private final EnviarArquivoParaGeminiUseCase enviarArquivoParaGeminiUseCase;
    private final ProcessarRespostaGeminiUseCase processarRespostaGeminiUseCase;
    private final ValidarComprovanteUseCase validarComprovanteUseCase;
    private final EnviarParaFilaRabbitUseCase enviarParaFilaRabbitUseCase;
    private final AtribuirEmailRemetenteUseCase atribuirEmailRemetenteUseCase;

    public EmailScheduler(
            LerEmailsNaoLidosUseCase lerEmailsNaoLidosUseCase,
            ValidarRemetenteUseCase validarRemetenteUseCase,
            ConectarEmailUseCase conectarEmailUseCase,
            ExtrairAnexosUseCase extrairAnexosUseCase,
            ConverterListaAnexoUseCase converterListaAnexouseCase,
            EnviarArquivoParaGeminiUseCase enviarArquivoParaGeminiUseCase,
            ProcessarRespostaGeminiUseCase processarRespostaGeminiUseCase,
            ValidarComprovanteUseCase validarComprovanteUseCase,
            EnviarParaFilaRabbitUseCase enviarParaFilaRabbitUseCase,
            AtribuirEmailRemetenteUseCase atribuirEmailRemetenteUseCase
    ) {
        this.lerEmailsNaoLidosUseCase = lerEmailsNaoLidosUseCase;
        this.validarRemetenteUseCase = validarRemetenteUseCase;
        this.conectarEmailUseCase = conectarEmailUseCase;
        this.extrairAnexosUseCase = extrairAnexosUseCase;
        this.converterListaAnexouseCase = converterListaAnexouseCase;
        this.enviarArquivoParaGeminiUseCase = enviarArquivoParaGeminiUseCase;
        this.processarRespostaGeminiUseCase = processarRespostaGeminiUseCase;
        this.validarComprovanteUseCase = validarComprovanteUseCase;
        this.enviarParaFilaRabbitUseCase = enviarParaFilaRabbitUseCase;
        this.atribuirEmailRemetenteUseCase = atribuirEmailRemetenteUseCase;
    }

    @Scheduled(fixedRate = 60000)
    public void processarEmails() {
        try {

            Store store = conectarEmailUseCase.execute();

            List<Message> naoLidos = lerEmailsNaoLidosUseCase.execute(store);
            if (naoLidos.isEmpty()) {
                System.out.println("📭 Nenhum email não lido encontrado.");
                return;
            }

            List<Message> emailsValidos = validarRemetenteUseCase.execute(naoLidos);
            if (emailsValidos.isEmpty()) {
                System.out.println("❌ Nenhum remetente válido encontrado.");
                return;
            }

            for (Message email : emailsValidos) {
                try {
                    System.out.println("📧 Processando email: " + email.getSubject());

                    List<File> anexos = extrairAnexosUseCase.execute(List.of(email));
                    if (anexos.isEmpty()) {
                        System.out.println("📎 Nenhum anexo encontrado no email: " + email.getSubject());
                        continue;
                    }
                    System.out.println("📎 Anexos encontrados: " + anexos.size());

                    List<File> imagens = converterListaAnexouseCase.execute(anexos);
                    if (imagens.isEmpty()) {
                        System.out.println("🖼️ Nenhuma imagem extraída/convertida do email: " + email.getSubject());
                        continue;
                    }
                    System.out.println("🖼️ Imagens processadas: " + imagens.size());

                    for (File imagem : imagens) {
                        try {
                            System.out.println("🔍 Processando imagem: " + imagem.getName());

                            String jsonResposta = enviarArquivoParaGeminiUseCase.execute(imagem);
                            System.out.println("📩 Resposta bruta Gemini recebida");

                            ComprovanteDto dto = processarRespostaGeminiUseCase.execute(jsonResposta);
                            System.out.println("✅ Comprovante processado:");
                            System.out.println(dto);

                            dto = atribuirEmailRemetenteUseCase.execute(dto, email);
                            System.out.println("📧 Email do remetente atribuído: " + dto.getEmailDestinatario());

                            ComprovanteDto comprovanteValido = validarComprovanteUseCase.execute(dto);
                            System.out.println("✅ Comprovante validado");

                            enviarParaFilaRabbitUseCase.execute(comprovanteValido);
                            System.out.println("🚀 Comprovante enviado para a fila");

                        } catch (Exception imagemException) {
                            System.err.println("❌ Erro ao processar imagem " + imagem.getName() + ": " + imagemException.getMessage());
                            imagemException.printStackTrace();
                        }
                    }
                } catch (Exception emailException) {
                    System.err.println("❌ Erro ao processar email " + email.getSubject() + ": " + emailException.getMessage());
                    emailException.printStackTrace();
                }
            }

        } catch (Exception globalException) {
            System.err.println("❌ Erro global no processamento de emails: " + globalException.getMessage());
            globalException.printStackTrace();
        }
    }
}
