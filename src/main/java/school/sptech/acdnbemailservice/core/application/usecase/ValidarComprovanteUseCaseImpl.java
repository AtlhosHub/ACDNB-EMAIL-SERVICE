package school.sptech.acdnbemailservice.core.application.usecase;

import school.sptech.acdnbemailservice.infrastructure.dto.ComprovanteDto;

public class ValidarComprovanteUseCaseImpl implements ValidarComprovanteUseCase {

    private final String destinatarioEsperado;
    private final String bancoDestinoEsperado;

    public ValidarComprovanteUseCaseImpl(String destinatarioEsperado, String bancoDestinoEsperado) {
        this.destinatarioEsperado = destinatarioEsperado;
        this.bancoDestinoEsperado = bancoDestinoEsperado;
    }

    @Override
    public ComprovanteDto execute(ComprovanteDto comprovante) throws Exception {
        if (!destinatarioEsperado.equalsIgnoreCase(comprovante.getNomeDestinatario())) {
            throw new IllegalArgumentException("Nome do destinatário inválido: " + comprovante.getNomeDestinatario());
        }

        if (!bancoDestinoEsperado.equalsIgnoreCase(comprovante.getBancoDestino())) {
            throw new IllegalArgumentException("Banco de destino inválido: " + comprovante.getBancoDestino());
        }

        return comprovante;
    }
}