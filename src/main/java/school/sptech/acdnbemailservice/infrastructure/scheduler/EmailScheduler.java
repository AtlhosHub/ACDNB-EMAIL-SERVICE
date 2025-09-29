package school.sptech.acdnbemailservice.infrastructure.scheduler;

import jakarta.mail.Message;
import jakarta.mail.Store;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import school.sptech.acdnbemailservice.core.application.usecase.*;

import java.io.File;
import java.util.List;

@Component
public class EmailScheduler {

    private final LerEmailsNaoLidosUseCase lerEmailsNaoLidosUseCase;
    private final ValidarRemetenteUseCase validarRemetenteUseCase;
    private final ConectarEmailUseCase conectarEmailUseCase;
    private final ExtrairAnexosUseCase extrairAnexosUseCase ;
    private final ConverterListaAnexoUseCase converterListaAnexouseCase;
    private final EnviarArquivoParaGeminiUseCase enviarArquivoParaGeminiUseCase;

    public EmailScheduler(
            LerEmailsNaoLidosUseCase lerEmailsNaoLidosUseCase,
            ValidarRemetenteUseCase validarRemetenteUseCase,
            ConectarEmailUseCase conectarEmailUseCase,
            ExtrairAnexosUseCase extrairAnexosUseCase,
            ConverterListaAnexoUseCase converterListaAnexouseCase,
            EnviarArquivoParaGeminiUseCase enviarArquivoParaGeminiUseCase

    ) {
        this.lerEmailsNaoLidosUseCase = lerEmailsNaoLidosUseCase;
        this.validarRemetenteUseCase = validarRemetenteUseCase;
        this.conectarEmailUseCase = conectarEmailUseCase;
        this.extrairAnexosUseCase = extrairAnexosUseCase;
        this.converterListaAnexouseCase = converterListaAnexouseCase;
        this.enviarArquivoParaGeminiUseCase = enviarArquivoParaGeminiUseCase;
    }

    @Scheduled(fixedRate = 60000)
    public void processarEmails() {
        try {
            // 1️⃣ Conectar ao email
            Store store = conectarEmailUseCase.execute();

            // 2️⃣ Ler emails não lidos
            List<Message> naoLidos = lerEmailsNaoLidosUseCase.execute(store);

            // 3️⃣ Validar remetente
            List<Message> emailsValidos = validarRemetenteUseCase.execute(naoLidos);

            // 4️⃣ Extrair anexos
            List<File> anexos = extrairAnexosUseCase.execute(emailsValidos);

            // 5️⃣ Converter PDFs em imagens ou manter imagens originais
            List<File> imagens = converterListaAnexouseCase.execute(anexos);

            for (File imagem : imagens) {
                String jsonResposta = enviarArquivoParaGeminiUseCase.execute(imagem);
                System.out.println(jsonResposta);
            }

            //ProcessarRespostaGeminiUseCase → limpa JSON do Gemini e devolve JSON puro (string).
            //ValidarComprovanteUseCase → valida as informações do json.
            //PublicarMensagemUseCase → publica no RabbitMQ o JSON final.


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}