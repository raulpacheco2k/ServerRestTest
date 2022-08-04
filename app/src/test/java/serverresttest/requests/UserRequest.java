package serverresttest.requests;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.github.javafaker.Faker;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {
    private String name;
    private String email;
    private String password;
    private String administrator;

    public UserRequest() {
    }

    public UserRequest(String name, String email, String password, String administrator) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.administrator = administrator;
    }

    public UserRequest(String name, String email, String administrator) {
        this.name = name;
        this.email = email;
        this.administrator = administrator;
    }

    public static UserRequest generateValidUserRequest() {
        return new UserRequest(
                Faker.instance().name().fullName(),
                Faker.instance().internet().emailAddress(),
                Faker.instance().internet().password(),
                "true"
        );
    }

    @JsonGetter("nome")
    public String getName() {
        return name;
    }

    @JsonSetter("nome")
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonGetter("administrador")
    public String getAdministrator() {
        return administrator;
    }

    @JsonSetter("administrador")
    public void setAdministrator(String administrator) {
        this.administrator = administrator;
    }
}
