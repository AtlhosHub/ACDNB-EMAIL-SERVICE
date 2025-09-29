package school.sptech.acdnbemailservice.infrastructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import school.sptech.acdnbemailservice.core.application.gateway.EmailGateway;
import school.sptech.acdnbemailservice.core.application.gateway.GeminiGateway;
import school.sptech.acdnbemailservice.core.application.usecase.*;
import school.sptech.acdnbemailservice.infrastructure.gateway.EmailRepositoryGateway;
import school.sptech.acdnbemailservice.infrastructure.gateway.GeminiRepositoryGateway;
import school.sptech.acdnbemailservice.infrastructure.security.config.EmailProperties;

import java.io.IOException;

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
            EmailGateway emailGateway
    ) {
        return new LerEmailsNaoLidosUseCaseImpl(emailGateway);
    }

    @Bean
    public ExtrairAnexosUseCase extrairAnexosUseCase(EmailGateway emailGateway) {
        return new ExtrairAnexosUseCaseImpl(emailGateway);
    }

    @Bean
    public ConverterPdfParaImagemUseCase converterPdfParaImagemUseCase() {
        return new ConverterPdfParaImagemUseCaseImpl();
    }

    @Bean
    public ConverterListaAnexoUseCase converterListaAnexouseCase(
            ConverterPdfParaImagemUseCase converterPdfParaImagemUseCase
    ) {
        return new ConverterListaAnexoUseCaseImpl(converterPdfParaImagemUseCase);
    }


    @Bean
    public GeminiGateway geminiGateway(ConverterPdfParaImagemUseCase converterPdfParaImagemUseCase) {
        String apiKey = "AIzaSyCS_Nyk5_7eZE7dceMiZDngNJufOqWtKgI"; //variavel de ambiente?
        try {
            return new GeminiRepositoryGateway(apiKey, converterPdfParaImagemUseCase);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar GeminiGateway", e);
        }
    }


    @Bean
    public EnviarArquivoParaGeminiUseCase enviarArquivoParaGeminiUseCase(GeminiGateway geminiGateway) {
        return new EnviarArquivoParaGeminiUseCaseImpl(geminiGateway);
    }

}
