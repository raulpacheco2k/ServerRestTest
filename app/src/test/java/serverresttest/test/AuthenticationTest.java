package serverresttest.test;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import serverresttest.requests.LoginRequest;
import serverresttest.requests.UserRequest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AuthenticationTest {

    @BeforeAll
    static void beforeAll() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = "https://serverest.dev";
    }

    @Test
    void create_account_successfully() {
        UserRequest userRequest = new UserRequest(
                Faker.instance().name().fullName(),
                Faker.instance().internet().emailAddress(),
                Faker.instance().internet().password(),
                "true"
        );

        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(userRequest).
                when().
                post("/usuarios").
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
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(userRequest).
                when().
                post("/usuarios").
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
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(userRequest).
                when().
                post("/usuarios").
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
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(userRequest).
                when().
                post("/usuarios");

        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(loginRequest).
                when().
                post("/login").
                then().
                assertThat().
                body("message", is("Login realizado com sucesso")).
                statusCode(HttpStatus.SC_OK);

    }
}
