package school.sptech.acdnbemailservice.infrastructure.gateway;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;
import school.sptech.acdnbemailservice.core.application.gateway.RemetenteGateway;
import org.springframework.web.client.RestTemplate;
import school.sptech.acdnbemailservice.core.domain.AlunoContato;
import school.sptech.acdnbemailservice.infrastructure.dto.EmailContatoDTO;

@Component
public class RemetenteRepositoryGateway implements RemetenteGateway {

    private final RestTemplate restTemplate;
    private final AlunoContatoRepository repository;

    public RemetenteRepositoryGateway(RestTemplate restTemplate, AlunoContatoRepository repository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    @Override
    public boolean existeEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public void salvarAluno(EmailContatoDTO dto) {
        AlunoContato aluno = new AlunoContato(dto.getNome(), dto.getEmail());
        repository.save(aluno);
        System.out.println("✅ Aluno salvo no banco: " + aluno.getNome());
    }

}