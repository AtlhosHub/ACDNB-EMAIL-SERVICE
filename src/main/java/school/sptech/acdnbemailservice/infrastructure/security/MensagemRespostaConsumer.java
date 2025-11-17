package school.sptech.acdnbemailservice.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import school.sptech.acdnbemailservice.core.application.usecase.GerarMensagemRetornoUseCaseImpl;
import school.sptech.acdnbemailservice.infrastructure.dto.RepostaPagamentoDTO;

@Component
public class MensagemRespostaConsumer {

    private final ObjectMapper mapper;
    private final GerarMensagemRetornoUseCaseImpl gerarMensagemRetornoUseCase;

    public MensagemRespostaConsumer(ObjectMapper mapper,
                                    GerarMensagemRetornoUseCaseImpl gerarMensagemRetornoUseCase) {
        this.mapper = mapper;
        this.gerarMensagemRetornoUseCase = gerarMensagemRetornoUseCase;
    }

    @RabbitListener(queues = "${app.rabbitmq.filapagamento}")
    public void consumirMensagem(byte[] payload) {

        if (payload == null || payload.length == 0) {
            System.out.println("⚠️ Mensagem vazia recebida, ignorando...");
            return;
        }

        try {
            String mensagemRaw = new String(payload);
            System.out.println("📨 Resposta recebida (raw): " + mensagemRaw);

            RepostaPagamentoDTO dto = mapper.readValue(payload, RepostaPagamentoDTO.class);

            System.out.println("✅ Resposta de pagamento processada:");
            System.out.println("Email: " + dto.getEmailDestinatario());
            System.out.println("Tipo Retorno: " + dto.getTipoRetorno());
            System.out.println("Mensagem: " + dto.getMensagem());
            System.out.println("Valor Recebido: " + dto.getValorRecebido());
            System.out.println("Valor Faltante: " + dto.getValorFaltante());
            System.out.println("Valor Excedente: " + dto.getValorExcedente());
            System.out.println("IDs processados: " + dto.getIdsProcessados());
            System.out.println("IDs com desconto: " + dto.getIdsComDesconto());

            String destinatario = dto.getEmailDestinatario();
            String mensagem = dto.getMensagem();

            gerarMensagemRetornoUseCase.execute(destinatario, mensagem);

            System.out.println("📤 Email de retorno enviado ao usuário: " + destinatario);

        } catch (Exception e) {
            System.out.println("❌ Erro ao processar resposta do pagamento: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
