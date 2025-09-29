package school.sptech.acdnbemailservice.core.application.usecase;

import jakarta.mail.Message;
import jakarta.mail.Store;
import school.sptech.acdnbemailservice.core.application.gateway.EmailGateway;

import java.util.List;

public class LerEmailsNaoLidosUseCaseImpl implements LerEmailsNaoLidosUseCase {

    private final EmailGateway emailGateway;

    public LerEmailsNaoLidosUseCaseImpl(EmailGateway emailGateway) {
        this.emailGateway = emailGateway;
    }

    @Override
    public List<Message> execute(Store store) throws Exception {
        List<Message> naoLidos = emailGateway.lerEmailsNaoLidos(store);
        emailGateway.marcarComoLidos(naoLidos);
        return naoLidos;
    }
}