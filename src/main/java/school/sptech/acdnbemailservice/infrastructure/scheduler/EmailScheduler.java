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

    public EmailScheduler(
            LerEmailsNaoLidosUseCase lerEmailsNaoLidosUseCase,
            ValidarRemetenteUseCase validarRemetenteUseCase,
            ConectarEmailUseCase conectarEmailUseCase,
            ExtrairAnexosUseCase extrairAnexosUseCase,
            ConverterListaAnexoUseCase converterListaAnexouseCase,
            EnviarArquivoParaGeminiUseCase enviarArquivoParaGeminiUseCase,
            ProcessarRespostaGeminiUseCase processarRespostaGeminiUseCase,
            ValidarComprovanteUseCase validarComprovanteUseCase,
            EnviarParaFilaRabbitUseCase enviarParaFilaRabbitUseCase
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
    }

    @Scheduled(fixedRate = 60000)
    public void processarEmails() {
        try {
            // 1️⃣ Conectar ao email
            Store store = conectarEmailUseCase.execute();

            // 2️⃣ Ler emails não lidos
            List<Message> naoLidos = lerEmailsNaoLidosUseCase.execute(store);
            if (naoLidos.isEmpty()) {
                System.out.println("📭 Nenhum email não lido encontrado.");
                return; // encerra o ciclo do scheduler
            }

            // 3️⃣ Validar remetente
            List<Message> emailsValidos = validarRemetenteUseCase.execute(naoLidos);
            if (emailsValidos.isEmpty()) {
                System.out.println("❌ Nenhum remetente válido encontrado.");
                return;
            }

            // 4️⃣ Extrair anexos
            List<File> anexos = extrairAnexosUseCase.execute(emailsValidos);
            if (anexos.isEmpty()) {
                System.out.println("📎 Nenhum anexo encontrado.");
                return;
            }

            // 5️⃣ Converter PDFs em imagens ou manter imagens originais
            List<File> imagens = converterListaAnexouseCase.execute(anexos);
            if (imagens.isEmpty()) {
                System.out.println("🖼️ Nenhuma imagem extraída/convertida.");
                return;
            }

            for (File imagem : imagens) {
                // 1️⃣ Envia a imagem para o Gemini e recebe a resposta bruta
                String jsonResposta = enviarArquivoParaGeminiUseCase.execute(imagem);

                System.out.println("📩 Resposta bruta Gemini:");
                System.out.println(jsonResposta);

                // 2️⃣ Processa a resposta e recebe direto o DTO
                ComprovanteDto dto = processarRespostaGeminiUseCase.execute(jsonResposta);

                System.out.println("✅ Comprovante processado:");
                System.out.println(dto);

                // 3️⃣ Valida o DTO
                ComprovanteDto comprovanteValido = validarComprovanteUseCase.execute(dto);

                // 4️⃣ Envia para a fila
                enviarParaFilaRabbitUseCase.execute(comprovanteValido);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
