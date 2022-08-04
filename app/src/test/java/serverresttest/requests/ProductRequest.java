package serverresttest.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    @JsonProperty("nome")
    private String name;
    @JsonProperty("preco")
    private Integer price;
    @JsonProperty("descricao")
    private String description;
    @JsonProperty("quantidade")
    private Integer quantity;

    public static ProductRequest generateValidProductRequest() {
        return new ProductRequest(
                Faker.instance().name().title(),
                Faker.instance().number().randomDigitNotZero(),
                Faker.instance().name().title(),
                Faker.instance().number().randomDigit());
    }
}
