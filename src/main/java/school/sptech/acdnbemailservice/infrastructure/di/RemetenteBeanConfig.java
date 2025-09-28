package school.sptech.acdnbemailservice.infrastructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import school.sptech.acdnbemailservice.core.application.gateway.RemetenteGateway;
import school.sptech.acdnbemailservice.infrastructure.gateway.RemetenteRepositoryGateway;

@Configuration
public class RemetenteBeanConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RemetenteGateway remetenteGateway(RestTemplate restTemplate) {
        return new RemetenteRepositoryGateway(restTemplate);
    }
}
