package serverresttest.test;

import com.github.javafaker.Faker;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class UserTest extends TestBase {

    @Test
    void get_users() {
        get("/usuarios").then().assertThat().
                body("quantidade", notNullValue()).
                body("usuarios.size()", is(numberOfRegisteredUsers())).
                statusCode(HttpStatus.SC_OK).
                body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/user/getUsers.json"));
    }

    @Test
    void get_valid_user() {
        String id = given().body(generateUser()).when().post(USERS_ENDPOINT).then().extract().path("_id");

        given().
                pathParam("_id", id).
                when().
                get(USERS_ENDPOINT + "/{_id}")
                .then().assertThat().
                statusCode(HttpStatus.SC_OK)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/user/getUser.json"));
    }

    @Test
    void get_invalid_user() {
        given().
                pathParam("_id", Faker.instance().funnyName().name()).
                when().
                get("/usuarios/{_id}").
                then().assertThat().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/user/getInvalidUser.json"));
    }

    private int numberOfRegisteredUsers() {
        return when().get("/usuarios").then().extract().path("quantidade");
    }

}
