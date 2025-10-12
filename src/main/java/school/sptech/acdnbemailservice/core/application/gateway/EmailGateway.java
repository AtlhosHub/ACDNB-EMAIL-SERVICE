package school.sptech.acdnbemailservice.core.application.gateway;

import jakarta.mail.Message;
import jakarta.mail.Store;

import java.io.File;
import java.util.List;

public interface EmailGateway {
    Store conectar() throws Exception;
    List<Message> lerEmailsNaoLidos(Store store) throws Exception;
    void marcarComoLidos(Store store,List<Message> emails) throws Exception;
    List<File> extrairAnexos(Message message) throws Exception;
}
