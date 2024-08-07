package serverresttest.test;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import serverresttest.requests.LoginRequest;
import serverresttest.requests.UserRequest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

class AuthenticationTest extends TestBase {

    private UserRequest userRequest;

    @BeforeEach
    void beforeEach() {
        this.userRequest = UserRequest.generateValidUserRequest();
    }

    @Test
    void create_account_successfully() {
        given().
                body(this.userRequest).
                when().
                post(USERS_ENDPOINT).
                then().
                assertThat().
                body("message", is("Cadastro realizado com sucesso")).
                body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/user/successfullyCreated.json")).
                statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    void create_account_with_blank_password() {
        this.userRequest.setPassword("");

        given().
                body(this.userRequest).
                when().
                post(USERS_ENDPOINT).
                then().
                assertThat().
                body("password", is("password não pode ficar em branco")).
                statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    void create_account_without_password() {
        this.userRequest.setPassword(null);

        given().
                body(this.userRequest).
                when().
                post(USERS_ENDPOINT).
                then().
                assertThat().
                body("password", is("password é obrigatório")).
                statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    void login_successfully() {
        LoginRequest loginRequest = new LoginRequest(this.userRequest);

        given().
                body(this.userRequest).
                when().
                post(USERS_ENDPOINT);

        given().
                body(loginRequest).
                when().
                post(LOGIN_ENDPOINT).
                then().
                assertThat().
                body("message", is("Login realizado com sucesso")).
                statusCode(HttpStatus.SC_OK);
    }
}
