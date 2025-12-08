package school.sptech.acdnbemailservice.infrastructure.dto;

public class EmailRecuperacaoSenhaDTO {
    private String email;
    private String nome;
    private String token;
    private String linkRecuperacao;

    public EmailRecuperacaoSenhaDTO() {}

    public EmailRecuperacaoSenhaDTO(String email, String nome, String token, String linkRecuperacao) {
        this.email = email;
        this.nome = nome;
        this.token = token;
        this.linkRecuperacao = linkRecuperacao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLinkRecuperacao() {
        return linkRecuperacao;
    }

    public void setLinkRecuperacao(String linkRecuperacao) {
        this.linkRecuperacao = linkRecuperacao;
    }
}