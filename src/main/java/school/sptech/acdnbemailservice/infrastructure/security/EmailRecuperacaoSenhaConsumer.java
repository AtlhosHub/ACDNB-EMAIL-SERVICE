package school.sptech.acdnbemailservice.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import school.sptech.acdnbemailservice.core.application.usecase.EnviarEmailRecuperacaoSenhaUseCase;
import school.sptech.acdnbemailservice.infrastructure.dto.EmailRecuperacaoSenhaDTO;

@Component
public class EmailRecuperacaoSenhaConsumer {

    private final EnviarEmailRecuperacaoSenhaUseCase useCase;
    private final ObjectMapper mapper;

    public EmailRecuperacaoSenhaConsumer(EnviarEmailRecuperacaoSenhaUseCase useCase, ObjectMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @RabbitListener(queues = "${app.rabbitmq.queue.recuperacao-senha}")
    public void consumirMensagem(byte[] payload) {
        try {
            String mensagem = new String(payload);
            System.out.println("📨 Mensagem recebida na fila de recuperação de senha");

            EmailRecuperacaoSenhaDTO dto = mapper.readValue(payload, EmailRecuperacaoSenhaDTO.class);
            System.out.println("✅ Processando email de recuperação para: " + dto.getEmail());

            useCase.execute(dto);

        } catch (Exception e) {
            System.err.println("❌ Erro ao processar mensagem de recuperação: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
