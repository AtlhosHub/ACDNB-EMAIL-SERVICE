package school.sptech.acdnbemailservice.infrastructure.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import school.sptech.acdnbemailservice.core.application.gateway.RabbitMqGateway;
import school.sptech.acdnbemailservice.infrastructure.dto.ComprovanteDto;

public class RabbitMqRepositoryGateway implements RabbitMqGateway {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final String exchange;
    private final String routingKey;

    public RabbitMqRepositoryGateway(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper, String exchange, String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    @Override
    public void enviarParaFila(ComprovanteDto comprovante) throws Exception {
        String mensagem = objectMapper.writeValueAsString(comprovante);
        rabbitTemplate.convertAndSend(exchange, routingKey, mensagem);
        System.out.println("📩 Mensagem enviada para RabbitMQ: " + mensagem);
    }
}

