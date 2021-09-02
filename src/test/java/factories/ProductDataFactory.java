package factories;

import com.github.javafaker.Faker;
import pojo.ProductPojo;

public class ProductDataFactory {
    Faker faker = new Faker();

    public ProductPojo product(){
        ProductPojo product = new ProductPojo();
        product.setNome(faker.name().name());
        product.setDescricao(faker.app().version());
        product.setPreco(100);
        product.setQuantidade(3);
        return  product;
    }

    public ProductPojo productWithoutName(){
        ProductPojo product = new ProductPojo();
        product.setDescricao(faker.app().version());
        product.setPreco(100);
        product.setQuantidade(3);
        return  product;
    }

    public ProductPojo productWithoutDescription(){
        ProductPojo product = new ProductPojo();
        product.setNome(faker.app().name());
        product.setPreco(100);
        product.setQuantidade(3);
        return  product;
    }

    public ProductPojo productWithoutPrice(){
        ProductPojo product = new ProductPojo();
        product.setNome(faker.app().name());
        product.setDescricao(faker.app().version());
        product.setQuantidade(3);
        return  product;
    }

    public ProductPojo productWithoutQuantity(){
        ProductPojo product = new ProductPojo();
        product.setNome(faker.app().name());
        product.setDescricao(faker.app().version());
        product.setPreco(100);
        return  product;
    }
}
