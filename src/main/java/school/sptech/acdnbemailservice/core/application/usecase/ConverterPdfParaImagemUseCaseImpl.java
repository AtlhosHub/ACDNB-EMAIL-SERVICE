package school.sptech.acdnbemailservice.core.application.usecase;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ConverterPdfParaImagemUseCaseImpl implements ConverterPdfParaImagemUseCase {

    private static final Set<String> FORMATOS_SUPORTADOS = Set.of("png", "jpeg", "jpg");

    @Override
    public List<File> execute(File arquivo) throws Exception {
        List<File> imagens = new ArrayList<>();
        String nome = arquivo.getName().toLowerCase();

        if (nome.endsWith(".pdf")) {
            try (PDDocument document = PDDocument.load(arquivo)) {
                PDFRenderer renderer = new PDFRenderer(document);
                for (int i = 0; i < document.getNumberOfPages(); i++) {
                    BufferedImage image = renderer.renderImageWithDPI(i, 300);
                    File imagemSaida = new File(
                            arquivo.getParent(),
                            nome.replace(".pdf", "") + "_page_" + (i + 1) + ".png"
                    );
                    ImageIO.write(image, "PNG", imagemSaida);
                    imagens.add(imagemSaida);
                }
            }
        } else if (FORMATOS_SUPORTADOS.stream().anyMatch(nome::endsWith)) {
            imagens.add(arquivo);
        } else {
            throw new IllegalArgumentException("Formato não suportado: " + arquivo.getName());
        }

        return imagens;
    }
}