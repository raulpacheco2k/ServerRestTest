package serverresttest.test;

import com.github.javafaker.Faker;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import serverresttest.requests.UserRequest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest extends TestBase {

    @Test
    void get_users() {
        get("/usuarios").then().assertThat().
                statusCode(HttpStatus.SC_OK).
                body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/user/getUsers.json"));
    }

    @Test
    void get_user() {
        UserRequest userRequestSerialized = UserRequest.generateValidUserRequest();
        String id = given().body(userRequestSerialized).when().post(USERS_ENDPOINT)
                .then().extract().path("_id");

        UserRequest userRequestDeserialized = given().
                pathParam("_id", id).
                when().
                get(USERS_ENDPOINT + "/{_id}").
                then().
                assertThat().
                statusCode(HttpStatus.SC_OK).
                body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/user/getUser.json")).
                extract().body().jsonPath().getObject("", UserRequest.class);

        assertEquals(userRequestDeserialized.getName(), userRequestSerialized.getName());
        assertEquals(userRequestDeserialized.getEmail(), userRequestSerialized.getEmail());
        assertEquals(userRequestDeserialized.getEmail(), userRequestSerialized.getEmail());
        assertEquals(userRequestDeserialized.getPassword(), userRequestSerialized.getPassword());
    }

    @Test
    void get_invalid_user() {
        given().
                pathParam("_id", Faker.instance().internet().uuid()).
                when().
                get("/usuarios/{_id}").
                then().
                assertThat().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("message", is("Usuário não encontrado")).
                body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/user/getInvalidUser.json"));
    }

    private int numberOfRegisteredUsers() {
        return when().get("/usuarios").then().extract().path("quantidade");
    }

}
