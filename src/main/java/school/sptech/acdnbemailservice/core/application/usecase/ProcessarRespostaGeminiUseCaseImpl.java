package school.sptech.acdnbemailservice.core.application.usecase;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import school.sptech.acdnbemailservice.infrastructure.dto.ComprovanteDto;
import school.sptech.acdnbemailservice.core.application.gateway.GeminiGateway;

@Component
public class ProcessarRespostaGeminiUseCaseImpl implements ProcessarRespostaGeminiUseCase {

    private final GeminiGateway geminiGateway;
    private final ObjectMapper mapper;

    public ProcessarRespostaGeminiUseCaseImpl(GeminiGateway geminiGateway, ObjectMapper mapper) {
        this.geminiGateway = geminiGateway;
        this.mapper = mapper;
    }

    @Override
    public ComprovanteDto execute(String respostaGemini) throws Exception {

        String jsonLimpo = geminiGateway.limparJson(respostaGemini);

        JsonNode root = mapper.readTree(jsonLimpo);
        JsonNode textNode = root
                .path("candidates")
                .path(0)
                .path("content")
                .path("parts")
                .path(0)
                .path("text");

        if (textNode.isMissingNode() || textNode.isNull()) {
            throw new IllegalArgumentException("Não foi possível extrair o JSON do comprovante do Gemini");
        }

        String textoComprovante = textNode.asText()
                .replaceAll("^```json\\s*", "")
                .replaceAll("\\s*```$", "")
                .trim();

        return mapper.readValue(textoComprovante, ComprovanteDto.class);
    }
}