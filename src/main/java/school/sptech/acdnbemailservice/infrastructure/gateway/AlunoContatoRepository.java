package school.sptech.acdnbemailservice.infrastructure.gateway;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.acdnbemailservice.core.domain.AlunoContato;

import java.util.Optional;

public interface AlunoContatoRepository extends JpaRepository<AlunoContato, Long> {
    boolean existsByEmail(String email);
    Optional<AlunoContato> findByEmail(String email);
}
