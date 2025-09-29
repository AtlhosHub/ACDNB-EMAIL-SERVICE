package school.sptech.acdnbemailservice.core.application.usecase;
import java.io.File;

import school.sptech.acdnbemailservice.core.application.gateway.GeminiGateway;

public class EnviarArquivoParaGeminiUseCaseImpl implements EnviarArquivoParaGeminiUseCase {

    private final GeminiGateway geminiGateway;

    public EnviarArquivoParaGeminiUseCaseImpl(GeminiGateway geminiGateway) {
        this.geminiGateway = geminiGateway;
    }

    @Override
    public String execute(File arquivo) throws Exception {
        return geminiGateway.enviarArquivo(arquivo);
    }
}