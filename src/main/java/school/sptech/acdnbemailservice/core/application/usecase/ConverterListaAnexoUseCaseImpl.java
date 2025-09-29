package school.sptech.acdnbemailservice.core.application.usecase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConverterListaAnexoUseCaseImpl implements ConverterListaAnexoUseCase {

    private final ConverterPdfParaImagemUseCase converter;

    public ConverterListaAnexoUseCaseImpl(ConverterPdfParaImagemUseCase converter) {
        this.converter = converter;
    }

    @Override
    public List<File> execute(List<File> arquivos) throws Exception {
        List<File> todasImagens = new ArrayList<>();
        for (File arquivo : arquivos) {
            todasImagens.addAll(converter.execute(arquivo));
        }
        return todasImagens;
    }
}
