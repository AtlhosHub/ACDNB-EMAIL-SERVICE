package school.sptech.acdnbemailservice.infrastructure.gateway;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import school.sptech.acdnbemailservice.core.application.gateway.GeminiGateway;
import school.sptech.acdnbemailservice.core.application.usecase.ConverterPdfParaImagemUseCase;

import java.util.List;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import school.sptech.acdnbemailservice.infrastructure.util.PromptLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class GeminiRepositoryGateway implements GeminiGateway {

    private final String apiKey;
    private final ConverterPdfParaImagemUseCase converterPdfParaImagemUseCase;
    private final String prompt;

    public GeminiRepositoryGateway(String apiKey, ConverterPdfParaImagemUseCase converter) throws IOException {
        this.apiKey = apiKey;
        this.converterPdfParaImagemUseCase = converter;
        this.prompt = PromptLoader.carregarPrompt("prompts/comprovante_prompt.txt");
    }

    @Override
    public String enviarArquivo(File file) throws Exception {
        File arquivoParaEnviar = file;
        File arquivoTemporario = null;

        try {
            if (isPdf(file)) {
                arquivoParaEnviar = converterPdf(file);
                arquivoTemporario = arquivoParaEnviar;
            }

            String base64 = encodeFileToBase64(arquivoParaEnviar);
            String jsonRequest = montarRequestJson(base64);
            return chamarApiGemini(jsonRequest);

        } finally {
            limparArquivoTemporario(arquivoTemporario);
        }
    }

    @Override
    public String limparJson(String respostaGemini) throws Exception {

        String respostaLimpa = respostaGemini
                .replaceAll("^```json\\s*", "")
                .replaceAll("\\s*```$", "")
                .trim();

        int inicio = respostaLimpa.indexOf("{");
        int fim = respostaLimpa.lastIndexOf("}");

        if (inicio == -1 || fim == -1 || inicio >= fim) {
            throw new IllegalArgumentException("Resposta do Gemini não contém JSON válido: " + respostaGemini);
        }

        String jsonBruto = respostaLimpa.substring(inicio, fim + 1);

        JsonElement jsonElement = JsonParser.parseString(jsonBruto);

        return jsonElement.toString();
    }

    // ================= Métodos privados =================

    private boolean isPdf(File file) {
        return file.getName().toLowerCase().endsWith(".pdf");
    }

    private File converterPdf(File pdf) throws Exception {
        List<File> imagens = converterPdfParaImagemUseCase.execute(pdf);
        if (imagens.isEmpty()) {
            throw new IllegalStateException("Falha ao converter PDF para imagem");
        }
        return imagens.get(0);
    }

    private String encodeFileToBase64(File file) throws Exception {
        byte[] bytes = Files.readAllBytes(file.toPath());
        return Base64.getEncoder().encodeToString(bytes);
    }

    private String montarRequestJson(String base64Image) {
        JsonObject inlineData = new JsonObject();
        inlineData.addProperty("mimeType", "image/png");
        inlineData.addProperty("data", base64Image);

        JsonObject imagePart = new JsonObject();
        imagePart.add("inlineData", inlineData);

        JsonObject textPart = new JsonObject();
        textPart.addProperty("text", prompt);

        JsonArray parts = new JsonArray();
        parts.add(imagePart);
        parts.add(textPart);

        JsonObject content = new JsonObject();
        content.add("parts", parts);

        JsonArray contents = new JsonArray();
        contents.add(content);

        JsonObject requestBody = new JsonObject();
        requestBody.add("contents", contents);

        return requestBody.toString();
    }

    private String chamarApiGemini(String jsonRequest) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + apiKey)
                .post(RequestBody.create(jsonRequest, MediaType.parse("application/json")))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Erro Gemini: " + response.code() + " - " + response.body().string());
            }
            return response.body().string();
        }
    }

    private void limparArquivoTemporario(File arquivo) {
        if (arquivo != null && arquivo.exists()) {
            if (!arquivo.delete()) {
                System.err.println("⚠️ Não foi possível apagar arquivo temporário: " + arquivo.getName());
            }
        }
    }
}
