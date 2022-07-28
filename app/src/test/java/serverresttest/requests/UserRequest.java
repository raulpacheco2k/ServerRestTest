package serverresttest.requests;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRequest {
    private String nome;
    private String email;
    private String password;
    private String administrador;

    public UserRequest(String nome, String email, String password, String administrador) {
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.administrador = administrador;
    }

    public UserRequest(String nome, String email, String administrador) {
        this.nome = nome;
        this.email = email;
        this.administrador = administrador;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAdministrador() {
        return administrador;
    }
}
