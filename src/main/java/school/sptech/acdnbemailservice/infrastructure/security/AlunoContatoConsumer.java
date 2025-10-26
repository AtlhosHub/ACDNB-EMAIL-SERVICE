package school.sptech.acdnbemailservice.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import school.sptech.acdnbemailservice.core.application.usecase.SalvarAlunoContatoUseCase;
import school.sptech.acdnbemailservice.infrastructure.dto.EmailContatoDTO;

@Component
public class AlunoContatoConsumer {

    private final SalvarAlunoContatoUseCase salvarAlunoContatoUseCase;
    private final ObjectMapper mapper;

    public AlunoContatoConsumer(SalvarAlunoContatoUseCase salvarAlunoContatoUseCase, ObjectMapper mapper) {
        this.salvarAlunoContatoUseCase = salvarAlunoContatoUseCase;
        this.mapper = mapper;
    }

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void consumirMensagem(byte[] payload) {
        if (payload == null || payload.length == 0) {
            System.out.println("⚠️ Mensagem vazia recebida, ignorando...");
            return;
        }

        try {
            String mensagemString = new String(payload);
            System.out.println("📨 Mensagem recebida (raw): " + mensagemString);

            EmailContatoDTO dto = mapper.readValue(payload, EmailContatoDTO.class);
            System.out.println("✅ Mensagem processada: " + dto.getNome() + " | " + dto.getEmail());

            salvarAlunoContatoUseCase.execute(dto);

        } catch (Exception e) {
            System.out.println("❌ Erro ao processar mensagem: " + e.getMessage());
        }
    }
}