package serverresttest.test;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class UserTest extends TestBase {

    @Test
    void get_user() {
        get("/usuarios").then().assertThat().
                body("quantidade", notNullValue()).
                body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/user/getAll.json")).
                body("usuarios.size()", is(numberOfRegisteredUsers())).
                statusCode(HttpStatus.SC_OK);
    }

    private int numberOfRegisteredUsers() {
        return when().get("/usuarios").then().extract().path("quantidade");
    }

}
