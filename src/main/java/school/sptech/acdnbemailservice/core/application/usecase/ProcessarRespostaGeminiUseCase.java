package school.sptech.acdnbemailservice.core.application.usecase;

import school.sptech.acdnbemailservice.infrastructure.dto.ComprovanteDto;

public interface ProcessarRespostaGeminiUseCase {
    ComprovanteDto execute(String respostaGemini) throws Exception;
}
