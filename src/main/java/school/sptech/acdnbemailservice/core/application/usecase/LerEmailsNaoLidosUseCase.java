package school.sptech.acdnbemailservice.core.application.usecase;

import jakarta.mail.Message;
import jakarta.mail.Store;

import java.util.List;

public interface LerEmailsNaoLidosUseCase {
    List<Message> execute(Store store) throws Exception;
}
