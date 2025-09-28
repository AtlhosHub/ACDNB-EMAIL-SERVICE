package school.sptech.acdnbemailservice.core.application.usecase;

import jakarta.mail.Store;

public interface ConectarEmailUseCase {
    Store execute() throws Exception;
}
