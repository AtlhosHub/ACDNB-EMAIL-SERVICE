package school.sptech.acdnbemailservice.core.application.usecase;

import jakarta.mail.Address;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import school.sptech.acdnbemailservice.infrastructure.dto.ComprovanteDto;

public class AtribuirEmailRemetenteUseCaseImpl implements AtribuirEmailRemetenteUseCase {

    @Override
    public ComprovanteDto execute(ComprovanteDto dto, Message email) {
        try {
            Address[] remetentes = email.getFrom();

            if (remetentes != null && remetentes.length > 0) {
                String remetente = ((InternetAddress) remetentes[0]).getAddress();
                dto.setEmailDestinatario(remetente);
                System.out.println("📧 Email do remetente atribuído ao DTO: " + remetente);
            } else {
                System.out.println("⚠️ Nenhum remetente encontrado no email.");
            }

        } catch (Exception e) {
            System.err.println("❌ Erro ao atribuir o email do remetente ao DTO: " + e.getMessage());
        }

        return dto;
    }
}
