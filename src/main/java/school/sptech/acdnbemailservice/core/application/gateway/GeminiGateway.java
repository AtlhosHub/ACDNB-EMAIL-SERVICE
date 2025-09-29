package school.sptech.acdnbemailservice.core.application.gateway;
import java.io.File;

public interface GeminiGateway {
    String enviarArquivo(File arquivo) throws Exception;
}
