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

    private static final String EXCHANGE_EMAIL = "exchange-email";
    private static final String ROUTING_KEY_EMAIL_RECUPERACAO = "email.recuperacao-senha";

    @Value("${app.rabbitmq.queue.recuperacao-senha}")
    private String filaRecuperacaoSenha;

    @Bean
    public Queue filaEmailRecuperacaoSenha() {
        return new Queue(filaRecuperacaoSenha, true);
    }

    @Bean
    public TopicExchange exchangeEmail() {
        return new TopicExchange(EXCHANGE_EMAIL);
    }

    @Bean
    public Binding bindingEmailRecuperacao() {
        return BindingBuilder
                .bind(filaEmailRecuperacaoSenha())
                .to(exchangeEmail())
                .with(ROUTING_KEY_EMAIL_RECUPERACAO);
    }
}