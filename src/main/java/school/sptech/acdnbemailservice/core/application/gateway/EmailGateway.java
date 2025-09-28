package school.sptech.acdnbemailservice.core.application.gateway;

import jakarta.mail.Message;
import jakarta.mail.Store;

import java.util.List;

public interface EmailGateway {
    Store conectar() throws Exception;
    List<Message> lerEmailsNaoLidos(Store store) throws Exception;
    void marcarComoLidos(List<Message> emails) throws Exception;
}
