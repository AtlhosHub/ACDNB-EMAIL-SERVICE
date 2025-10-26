package school.sptech.acdnbemailservice.infrastructure.di;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import school.sptech.acdnbemailservice.core.application.gateway.RabbitMqGateway;
import school.sptech.acdnbemailservice.core.application.usecase.EnviarParaFilaRabbitUseCase;
import school.sptech.acdnbemailservice.core.application.usecase.EnviarParaFilaRabbitUseCaseImpl;
import school.sptech.acdnbemailservice.infrastructure.gateway.RabbitMqRepositoryGateway;

@Configuration
public class RabbitMqBeanConfig {

    @Bean
    public RabbitMqGateway rabbitMqGateway(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        return new RabbitMqRepositoryGateway(
                rabbitTemplate,
                objectMapper,
                "exchange-comprovantes",
                "comprovante.processado"
        );
    }

    @Bean
    public EnviarParaFilaRabbitUseCase enviarParaFilaRabbitUseCase(RabbitMqGateway rabbitMqGateway) {
        return new EnviarParaFilaRabbitUseCaseImpl(rabbitMqGateway);
    }
}

