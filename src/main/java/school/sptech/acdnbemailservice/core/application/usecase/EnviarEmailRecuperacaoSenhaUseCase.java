package school.sptech.acdnbemailservice.core.application.usecase;

import school.sptech.acdnbemailservice.infrastructure.dto.EmailRecuperacaoSenhaDTO;

public interface EnviarEmailRecuperacaoSenhaUseCase {
    void execute(EmailRecuperacaoSenhaDTO dto);
}
