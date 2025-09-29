package school.sptech.acdnbemailservice.infrastructure.gateway;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;
import school.sptech.acdnbemailservice.core.application.gateway.RemetenteGateway;
import org.springframework.web.client.RestTemplate;

@Component
public class RemetenteRepositoryGateway implements RemetenteGateway {

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://colocarURLDepois/api/usuarios";

    public RemetenteRepositoryGateway(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean existeEmail(String email) {
        String url = UriComponentsBuilder
                .fromHttpUrl(baseUrl + "/existe")
                .queryParam("email", email)
                .toUriString();

        try {
            Boolean existe = restTemplate.getForObject(url, Boolean.class);
            return existe != null && existe;
        } catch (RestClientException e) {
            System.err.println("Erro ao verificar remetente: " + e.getMessage());
            return false;
        }
    }
}

