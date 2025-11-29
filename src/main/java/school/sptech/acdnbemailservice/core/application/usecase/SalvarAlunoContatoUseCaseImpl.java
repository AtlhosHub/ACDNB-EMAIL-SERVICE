package school.sptech.acdnbemailservice.core.application.usecase;

import school.sptech.acdnbemailservice.core.application.gateway.RemetenteGateway;
import school.sptech.acdnbemailservice.infrastructure.dto.EmailContatoDTO;

public class SalvarAlunoContatoUseCaseImpl implements SalvarAlunoContatoUseCase {

    private final RemetenteGateway gateway;

    public SalvarAlunoContatoUseCaseImpl(RemetenteGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void execute(EmailContatoDTO dto) {
        if ("CREATE".equals(dto.getOperacao())) {
            gateway.salvarAluno(dto);
        } else if ("UPDATE".equals(dto.getOperacao())) {
            gateway.atualizarAluno(dto);
        } else {
            System.out.println("❌ Operação desconhecida: " + dto.getOperacao());
        }
    }
}
