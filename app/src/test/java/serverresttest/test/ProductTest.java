package serverresttest.test;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import serverresttest.requests.LoginRequest;
import serverresttest.requests.ProductRequest;
import serverresttest.requests.UserRequest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

class ProductTest extends TestBase {

    private final static String PRODUCTS_ENDPOINT = "/produtos";
    private static final String USERS_ENDPOINT = "/usuarios";
    private static final String LOGIN_ENDPOINT = "/login";


    @Test
    void create_product_successfully() {
        UserRequest userRequest = UserRequest.generateValidUserRequest();
        given().body(userRequest).when().post(USERS_ENDPOINT);
        String authorization = given().body(new LoginRequest(userRequest)).post(LOGIN_ENDPOINT).path("authorization");

        ProductRequest product = ProductRequest.generateValidProductRequest();

        given().
                body(product).
                header("Authorization", authorization).
                when().
                post(PRODUCTS_ENDPOINT).
                then().
                assertThat().
                body("message", is("Cadastro realizado com sucesso")).
                statusCode(HttpStatus.SC_CREATED);

    }
}
