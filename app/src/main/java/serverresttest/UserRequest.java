package serverresttest;

public class UserRequest {
    String nome;
    String email;
    String password;
    String administrador;

    public UserRequest(String nome, String email, String password, String administrador) {
        this.nome = nome;
        this.email = email;
        this.password = password;
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
