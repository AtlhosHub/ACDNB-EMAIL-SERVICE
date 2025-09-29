package school.sptech.acdnbemailservice.core.application.usecase;

import java.io.File;
import java.util.List;

public interface ConverterListaAnexoUseCase {
    List<File> execute(List<File> arquivos) throws Exception;
}
