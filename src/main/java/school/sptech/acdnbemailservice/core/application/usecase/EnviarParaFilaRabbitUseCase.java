package school.sptech.acdnbemailservice.core.application.usecase;

import school.sptech.acdnbemailservice.infrastructure.dto.ComprovanteDto;

public interface EnviarParaFilaRabbitUseCase {
    void execute(ComprovanteDto comprovante) throws Exception;
}
