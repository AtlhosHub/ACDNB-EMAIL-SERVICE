package school.sptech.acdnbemailservice.core.application.usecase;

import jakarta.mail.Message;
import java.io.File;
import java.util.List;

public interface ExtrairAnexosUseCase {
    List<File> execute(List<Message> emails) throws Exception;
}
