package serverresttest.test;

import com.github.javafaker.Faker;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import serverresttest.requests.LoginRequest;
import serverresttest.requests.UserRequest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

class AuthenticationTest extends TestBase {

    @Test
    void create_account_successfully() {
        UserRequest userRequest = new UserRequest(
                Faker.instance().name().fullName(),
                Faker.instance().internet().emailAddress(),
                Faker.instance().internet().password(),
                "true"
        );

        given().
                body(userRequest).
                when().
                post(USERS_ENDPOINT).
                then().
                assertThat().
                body("message", is("Cadastro realizado com sucesso")).
                statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    void create_account_with_blank_password() {
        UserRequest userRequest = new UserRequest(
                Faker.instance().name().fullName(),
                Faker.instance().internet().emailAddress(),
                "",
                "true"
        );

        given().
                body(userRequest).
                when().
                post(USERS_ENDPOINT).
                then().
                assertThat().
                body("password", is("password não pode ficar em branco"));
    }

    @Test
    void create_account_without_password() {
        UserRequest userRequest = new UserRequest(
                Faker.instance().name().fullName(),
                Faker.instance().internet().emailAddress(),
                "true"
        );

        System.out.println(userRequest.toString());

        given().
                body(userRequest).
                when().
                post(USERS_ENDPOINT).
                then().
                assertThat().
                body("password", is("password é obrigatório"));
    }

    @Test
    void login_successfully() {
        UserRequest userRequest = new UserRequest(
                Faker.instance().name().fullName(),
                Faker.instance().internet().emailAddress(),
                Faker.instance().internet().password(),
                "true"
        );

        LoginRequest loginRequest = new LoginRequest(userRequest);

        given().
                body(userRequest).
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
