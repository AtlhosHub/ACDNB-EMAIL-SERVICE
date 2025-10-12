package school.sptech.acdnbemailservice.infrastructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import school.sptech.acdnbemailservice.core.application.gateway.RemetenteGateway;
import school.sptech.acdnbemailservice.core.application.usecase.SalvarAlunoContatoUseCaseImpl;
import school.sptech.acdnbemailservice.core.application.usecase.SalvarAlunoContatoUseCase;
import school.sptech.acdnbemailservice.core.application.usecase.ValidarRemetenteUseCase;
import school.sptech.acdnbemailservice.core.application.usecase.ValidarRemetenteUseCaseImpl;
import school.sptech.acdnbemailservice.infrastructure.gateway.AlunoContatoRepository;
import school.sptech.acdnbemailservice.infrastructure.gateway.RemetenteRepositoryGateway;

@Configuration
public class RemetenteBeanConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RemetenteGateway remetenteGateway(RestTemplate restTemplate, AlunoContatoRepository alunoContatoRepository) {
        return new RemetenteRepositoryGateway(restTemplate, alunoContatoRepository);
    }

    @Bean
    public ValidarRemetenteUseCase validarRemetenteUseCase(RemetenteGateway remetenteGateway) {
        return new ValidarRemetenteUseCaseImpl(remetenteGateway);
    }

    @Bean
    public SalvarAlunoContatoUseCase salvarAlunoContatoUseCase(RemetenteGateway remetenteGateway) {
        return new SalvarAlunoContatoUseCaseImpl(remetenteGateway);
    }
}