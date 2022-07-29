package serverresttest.requests;

import com.github.javafaker.Faker;

public class ProductRequest {
    private String nome;
    private Integer preco;
    private String descricao;
    private Integer quantidade;

    public ProductRequest(String bome, Integer preco, String descricao, Integer quantidade) {
        this.nome = bome;
        this.preco = preco;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    public static ProductRequest generateValidProductRequest() {
        return new ProductRequest(
                Faker.instance().name().title(),
                Faker.instance().number().randomDigitNotZero(),
                Faker.instance().name().title(),
                Faker.instance().number().randomDigit());
    }

    public String getNome() {
        return nome;
    }

    public Integer getPreco() {
        return preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }
}
