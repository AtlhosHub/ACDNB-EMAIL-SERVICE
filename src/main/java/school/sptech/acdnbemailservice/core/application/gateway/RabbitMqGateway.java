package school.sptech.acdnbemailservice.core.application.gateway;

import school.sptech.acdnbemailservice.infrastructure.dto.ComprovanteDto;

public interface RabbitMqGateway {
    void enviarParaFila(ComprovanteDto comprovante) throws Exception;
}
