package school.sptech.acdnbemailservice.core.application.usecase;

import school.sptech.acdnbemailservice.infrastructure.dto.EmailContatoDTO;

public interface SalvarAlunoContatoUseCase {
    void execute(EmailContatoDTO dto);
}
