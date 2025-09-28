package school.sptech.acdnbemailservice.core.application.usecase;
import jakarta.mail.Store;
import school.sptech.acdnbemailservice.core.application.gateway.EmailGateway;

public class ConectarEmailUseCaseImpl implements ConectarEmailUseCase {

    private final EmailGateway emailGateway;

    public ConectarEmailUseCaseImpl(EmailGateway emailGateway) {
        this.emailGateway = emailGateway;
    }

    @Override
    public Store execute() throws Exception {
        return emailGateway.conectar();
    }
}