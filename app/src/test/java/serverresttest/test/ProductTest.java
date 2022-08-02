package serverresttest.test;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import serverresttest.requests.ProductRequest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

class ProductTest extends TestBase {

    @Test
    void create_product_successfully() {
        String authorization = getAuthorization(generateUser());
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
