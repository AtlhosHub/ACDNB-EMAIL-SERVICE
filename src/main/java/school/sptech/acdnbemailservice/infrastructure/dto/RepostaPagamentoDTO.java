package school.sptech.acdnbemailservice.infrastructure.dto;

import java.math.BigDecimal;
import java.util.List;

public class RepostaPagamentoDTO {
    private String emailDestinatario;
    private String tipoRetorno;
    private String mensagem;
    private BigDecimal valorRecebido;

    public String getEmailDestinatario() {
        return emailDestinatario;
    }

    public void setEmailDestinatario(String emailDestinatario) {
        this.emailDestinatario = emailDestinatario;
    }

    public String getTipoRetorno() {
        return tipoRetorno;
    }

    public void setTipoRetorno(String tipoRetorno) {
        this.tipoRetorno = tipoRetorno;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public BigDecimal getValorRecebido() {
        return valorRecebido;
    }

    public void setValorRecebido(BigDecimal valorRecebido) {
        this.valorRecebido = valorRecebido;
    }

    public BigDecimal getValorFaltante() {
        return valorFaltante;
    }

    public void setValorFaltante(BigDecimal valorFaltante) {
        this.valorFaltante = valorFaltante;
    }

    public BigDecimal getValorExcedente() {
        return valorExcedente;
    }

    public void setValorExcedente(BigDecimal valorExcedente) {
        this.valorExcedente = valorExcedente;
    }

    public List<Integer> getIdsProcessados() {
        return idsProcessados;
    }

    public void setIdsProcessados(List<Integer> idsProcessados) {
        this.idsProcessados = idsProcessados;
    }

    public List<Integer> getIdsComDesconto() {
        return idsComDesconto;
    }

    public void setIdsComDesconto(List<Integer> idsComDesconto) {
        this.idsComDesconto = idsComDesconto;
    }

    private BigDecimal valorFaltante;
    private BigDecimal valorExcedente;
    private List<Integer> idsProcessados;
    private List<Integer> idsComDesconto;
}
