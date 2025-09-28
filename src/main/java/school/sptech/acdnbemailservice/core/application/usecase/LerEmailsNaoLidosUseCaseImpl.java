package school.sptech.acdnbemailservice.core.application.usecase;

import jakarta.mail.Message;
import jakarta.mail.Store;
import school.sptech.acdnbemailservice.core.application.gateway.EmailGateway;

import java.util.List;

public class LerEmailsNaoLidosUseCaseImpl implements LerEmailsNaoLidosUseCase {

    private final EmailGateway emailGateway;
    private final ConectarEmailUseCase conectarEmailUseCase;

    public LerEmailsNaoLidosUseCaseImpl(EmailGateway emailGateway, ConectarEmailUseCase conectarEmailUseCase) {
        this.emailGateway = emailGateway;
        this.conectarEmailUseCase = conectarEmailUseCase;
    }

    @Override
    public List<Message> execute() throws Exception {
        Store store = conectarEmailUseCase.execute();
        List<Message> naoLidos = emailGateway.lerEmailsNaoLidos(store);
        emailGateway.marcarComoLidos(naoLidos);
        return naoLidos;
    }
}