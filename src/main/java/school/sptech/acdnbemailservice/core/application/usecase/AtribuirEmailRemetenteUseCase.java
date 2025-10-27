package school.sptech.acdnbemailservice.core.application.usecase;

import jakarta.mail.Message;
import school.sptech.acdnbemailservice.infrastructure.dto.ComprovanteDto;

public interface AtribuirEmailRemetenteUseCase {
    ComprovanteDto execute(ComprovanteDto dto, Message email);
}
