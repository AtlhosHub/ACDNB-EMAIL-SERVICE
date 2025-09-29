package school.sptech.acdnbemailservice.core.application.usecase;

import java.io.File;
import java.util.List;

public interface ConverterPdfParaImagemUseCase {
    List<File> execute(File arquivo) throws Exception;
}
