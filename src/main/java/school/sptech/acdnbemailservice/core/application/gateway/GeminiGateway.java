package school.sptech.acdnbemailservice.core.application.gateway;
import java.io.File;

public interface GeminiGateway {
    String enviarArquivo(File arquivo) throws Exception;
    String limparJson(String respostaGemini) throws Exception;

}
