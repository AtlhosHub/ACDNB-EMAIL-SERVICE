package school.sptech.acdnbemailservice.core.application.usecase;

interface GerarMensagemRetornoUseCase {
    void execute(String destinatario, String mensagem) throws Exception;
}
