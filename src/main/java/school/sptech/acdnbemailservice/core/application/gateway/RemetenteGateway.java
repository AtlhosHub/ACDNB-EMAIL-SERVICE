package school.sptech.acdnbemailservice.core.application.gateway;

import school.sptech.acdnbemailservice.infrastructure.dto.EmailContatoDTO;

public interface RemetenteGateway {
    boolean existeEmail(String email);
    void salvarAluno(EmailContatoDTO dto);
}
