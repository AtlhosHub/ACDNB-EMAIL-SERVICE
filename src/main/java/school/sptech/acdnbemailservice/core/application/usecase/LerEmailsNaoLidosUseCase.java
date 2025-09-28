package school.sptech.acdnbemailservice.core.application.usecase;

import jakarta.mail.Message;
import java.util.List;

public interface LerEmailsNaoLidosUseCase {
    List<Message> execute() throws Exception;
}
