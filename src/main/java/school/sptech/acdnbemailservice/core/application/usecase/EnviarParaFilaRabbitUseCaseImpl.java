package school.sptech.acdnbemailservice.core.application.usecase;

import school.sptech.acdnbemailservice.core.application.gateway.RabbitMqGateway;
import school.sptech.acdnbemailservice.infrastructure.dto.ComprovanteDto;

public class EnviarParaFilaRabbitUseCaseImpl implements EnviarParaFilaRabbitUseCase {

    private final RabbitMqGateway rabbitMqGateway;

    public EnviarParaFilaRabbitUseCaseImpl(RabbitMqGateway rabbitMqGateway) {
        this.rabbitMqGateway = rabbitMqGateway;
    }

    @Override
    public void execute(ComprovanteDto comprovante) throws Exception {
        rabbitMqGateway.enviarParaFila(comprovante);
    }
}
