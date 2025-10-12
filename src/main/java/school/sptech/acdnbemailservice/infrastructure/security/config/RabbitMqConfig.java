package school.sptech.acdnbemailservice.infrastructure.security.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMqConfig {

    @Value("${app.rabbitmq.queue}")
    private String queueName;

    @Bean
    public Queue filaComprovantes() {
        return new Queue(queueName, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("exchange-comprovantes");
    }

    @Bean public Binding binding(Queue filaComprovantes, TopicExchange exchange) {
        return BindingBuilder.bind(filaComprovantes).to(exchange).with("#");
    }
}