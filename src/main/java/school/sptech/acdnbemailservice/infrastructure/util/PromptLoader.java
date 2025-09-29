package school.sptech.acdnbemailservice.infrastructure.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class PromptLoader {

    public static String carregarPrompt(String arquivo) throws IOException {
        try (InputStream is = PromptLoader.class.getClassLoader().getResourceAsStream(arquivo)) {
            if (is == null) {
                throw new IOException("Arquivo de prompt não encontrado: " + arquivo);
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}

