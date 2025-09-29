package school.sptech.acdnbemailservice.core.application.usecase;

import jakarta.mail.Message;
import school.sptech.acdnbemailservice.core.application.gateway.EmailGateway;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExtrairAnexosUseCaseImpl implements ExtrairAnexosUseCase {

    private final EmailGateway emailGateway;

    public ExtrairAnexosUseCaseImpl(EmailGateway emailGateway) {
        this.emailGateway = emailGateway;
    }

    @Override
    public List<File> execute(List<Message> emails) throws Exception {
        List<File> anexos = new ArrayList<>();
        for (Message email : emails) {
            anexos.addAll(emailGateway.extrairAnexos(email));
        }

        if (anexos.isEmpty()) {
            throw new IllegalStateException("Nenhum anexo encontrado nos emails processados.");
        }

        return anexos;
    }
}