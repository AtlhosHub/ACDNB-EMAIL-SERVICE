package school.sptech.acdnbemailservice.core.application.usecase;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import school.sptech.acdnbemailservice.core.application.gateway.RemetenteGateway;

import java.util.List;
import java.util.stream.Collectors;

public class ValidarRemetenteUseCaseImpl implements ValidarRemetenteUseCase {

    private final RemetenteGateway remetenteGateway;

    public ValidarRemetenteUseCaseImpl(RemetenteGateway remetenteGateway) {
        this.remetenteGateway = remetenteGateway;
    }

    @Override
    public List<Message> execute(List<Message> emails) {
        return emails.stream()
                .filter(msg -> {
                    try {
                        String remetente = ((InternetAddress) msg.getFrom()[0]).getAddress();
                        return remetenteGateway.existeEmail(remetente);
                    } catch (Exception e) {
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }
}
