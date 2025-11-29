package school.sptech.acdnbemailservice.infrastructure.gateway;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;
import school.sptech.acdnbemailservice.core.application.gateway.RemetenteGateway;
import org.springframework.web.client.RestTemplate;
import school.sptech.acdnbemailservice.core.domain.AlunoContato;
import school.sptech.acdnbemailservice.infrastructure.dto.EmailContatoDTO;

import java.util.Optional;

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
        if (!repository.existsByEmail(dto.getEmail())) {
            AlunoContato aluno = new AlunoContato(dto.getNome(), dto.getEmail());
            repository.save(aluno);
            System.out.println("✅ Aluno criado no banco: " + aluno.getNome());
        } else {
            System.out.println("⚠️ Aluno já existe com email: " + dto.getEmail());
        }
    }

    @Override
    public void atualizarAluno(EmailContatoDTO dto) {
        Optional<AlunoContato> alunoExistente = repository.findByEmail(dto.getEmailAntigo());

        if (alunoExistente.isPresent()) {
            AlunoContato aluno = alunoExistente.get();
            aluno.setNome(dto.getNome());
            aluno.setEmail(dto.getEmail());
            repository.save(aluno);
            System.out.println("✅ Aluno atualizado no banco: " + aluno.getNome());
        } else {
            System.out.println("⚠️ Aluno não encontrado com email antigo, criando novo...");
            salvarAluno(dto);
        }
    }

}