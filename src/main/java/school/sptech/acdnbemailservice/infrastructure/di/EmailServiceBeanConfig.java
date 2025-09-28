package school.sptech.acdnbemailservice.infrastructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import school.sptech.acdnbemailservice.core.application.gateway.EmailGateway;
import school.sptech.acdnbemailservice.core.application.usecase.ConectarEmailUseCase;
import school.sptech.acdnbemailservice.core.application.usecase.ConectarEmailUseCaseImpl;
import school.sptech.acdnbemailservice.core.application.usecase.LerEmailsNaoLidosUseCase;
import school.sptech.acdnbemailservice.core.application.usecase.LerEmailsNaoLidosUseCaseImpl;
import school.sptech.acdnbemailservice.infrastructure.gateway.EmailRepositoryGateway;
import school.sptech.acdnbemailservice.infrastructure.security.config.EmailProperties;

@Configuration
public class EmailServiceBeanConfig {
    @Bean
    public EmailGateway emailGateway(EmailProperties emailProperties) {
        return new EmailRepositoryGateway(emailProperties);
    }

    @Bean
    public ConectarEmailUseCase conectarEmailUseCase(EmailGateway emailGateway) {
        return new ConectarEmailUseCaseImpl(emailGateway);
    }

    @Bean
    public LerEmailsNaoLidosUseCase lerEmailsNaoLidosUseCase(
            EmailGateway emailGateway,
            ConectarEmailUseCase conectarEmailUseCase
    ) {
        return new LerEmailsNaoLidosUseCaseImpl(emailGateway, conectarEmailUseCase);
    }


}
