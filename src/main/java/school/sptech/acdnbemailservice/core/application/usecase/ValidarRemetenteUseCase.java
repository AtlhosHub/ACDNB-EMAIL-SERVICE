package school.sptech.acdnbemailservice.core.application.usecase;
import jakarta.mail.Message;

import java.util.List;

public interface ValidarRemetenteUseCase {
    List<Message> execute(List<Message> emails);
}