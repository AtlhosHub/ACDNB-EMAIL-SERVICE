package school.sptech.acdnbemailservice.infrastructure.dto;

public class EmailContatoDTO {
    private Long id;
    private String nome;
    private String email;
    private String operacao;
    private String emailAntigo;

    public EmailContatoDTO() {

    }

    public EmailContatoDTO(Long id, String nome, String email, String operacao, String emailAntigo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.operacao = operacao;
        this.emailAntigo = emailAntigo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getEmailAntigo() {
        return emailAntigo;
    }

    public void setEmailAntigo(String emailAntigo) {
        this.emailAntigo = emailAntigo;
    }
}
