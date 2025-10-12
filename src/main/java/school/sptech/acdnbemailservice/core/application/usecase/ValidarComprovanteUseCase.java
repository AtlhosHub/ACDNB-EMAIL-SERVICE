package school.sptech.acdnbemailservice.core.application.usecase;

import school.sptech.acdnbemailservice.infrastructure.dto.ComprovanteDto;

public interface ValidarComprovanteUseCase {
    ComprovanteDto execute(ComprovanteDto comprovante) throws Exception;
}
