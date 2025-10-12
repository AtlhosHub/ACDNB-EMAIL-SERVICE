package school.sptech.acdnbemailservice.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import school.sptech.acdnbemailservice.core.application.usecase.SalvarAlunoContatoUseCase;
import school.sptech.acdnbemailservice.infrastructure.dto.EmailContatoDTO;

@Component
public class AlunoContatoConsumer {

    private final SalvarAlunoContatoUseCase salvarAlunoContatoUseCase;

    public AlunoContatoConsumer(SalvarAlunoContatoUseCase salvarAlunoContatoUseCase) {
        this.salvarAlunoContatoUseCase = salvarAlunoContatoUseCase;
    }

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void consumirMensagem(byte[] payload) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        EmailContatoDTO dto = mapper.readValue(payload, EmailContatoDTO.class);
        System.out.println("📨 Mensagem recebida: " + dto.getNome() + " | " + dto.getEmail());
        salvarAlunoContatoUseCase.execute(dto);
    }

}
