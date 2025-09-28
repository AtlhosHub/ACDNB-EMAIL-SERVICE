package school.sptech.acdnbemailservice.infrastructure.security.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailProperties {

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
