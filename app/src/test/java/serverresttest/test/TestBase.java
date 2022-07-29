package serverresttest.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import serverresttest.requests.LoginRequest;
import serverresttest.requests.UserRequest;

import static io.restassured.RestAssured.given;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class TestBase {
    protected final static String PRODUCTS_ENDPOINT = "/produtos";
    protected static final String USERS_ENDPOINT = "/usuarios";
    protected static final String LOGIN_ENDPOINT = "/login";

    @BeforeAll
    static void beforeAll() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = "https://serverest.dev";
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    protected String getAuthorization() {
        UserRequest userRequest = UserRequest.generateValidUserRequest();
        given().body(userRequest).when().post(USERS_ENDPOINT);

        return given().body(new LoginRequest(userRequest)).
                when().post(LOGIN_ENDPOINT).
                then().assertThat().statusCode(HttpStatus.SC_OK).
                extract().path("authorization");
    }
}