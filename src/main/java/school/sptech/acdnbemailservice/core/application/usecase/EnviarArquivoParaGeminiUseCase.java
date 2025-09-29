package school.sptech.acdnbemailservice.core.application.usecase;

import java.io.File;

public interface EnviarArquivoParaGeminiUseCase {
    String execute(File arquivo) throws Exception;
}
