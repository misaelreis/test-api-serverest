package tests.product;

import factories.ProductDataFactory;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import pojo.ProductPojo;
import utils.LoginUtil;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static utils.BaseUrlUtil.baseUrlProduct;

public class CreateProductTest {

    public String tokenIdAdm;
    public String tokenIdUser;

    @Before
    public void setToken(){
        tokenIdAdm = new LoginUtil().loginAdmFix();
        tokenIdUser = new LoginUtil().loginUserFix();
    }
    @Test
    public void createProduct(){
        ProductPojo product = new ProductDataFactory().product();
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenIdAdm)
                .body(product)
                .when()
                .post(baseUrlProduct)
                .then()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("product-schema.json"))
                .body("message", equalTo("Cadastro realizado com sucesso"))
                .body("_id", notNullValue());
    }

    @Test
    public void createProductIdUser(){
        ProductPojo product = new ProductDataFactory().product();
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenIdUser)
                .body(product)
                .when()
                .post(baseUrlProduct)
                .then()
                .statusCode(403)
                .body("message", equalTo("Rota exclusiva para administradores"));
    }

    @Test
    public void createProductNameExistent(){
        ProductPojo product = new ProductDataFactory().product();
        product.setNome("produto existente");
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenIdAdm)
                .body(product)
                .when()
                .post(baseUrlProduct);

        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenIdAdm)
                .body(product)
                .when()
                .post(baseUrlProduct)
                .then()
                .statusCode(400)
                .body("message", equalTo("Já existe produto com esse nome"));
    }

    @Test
    public void createProductWithoutToken(){
        ProductPojo product = new ProductDataFactory().product();
        given()
                .contentType(ContentType.JSON)
                .body(product)
                .when()
                .post(baseUrlProduct)
                .then()
                .statusCode(401)
                .body("message", equalTo("Token de acesso ausente, inválido, expirado ou usuário do token não existe mais"));
    }

    @Test
    public void createProductWithoutName(){
        ProductPojo product = new ProductDataFactory().productWithoutName();
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenIdAdm)
                .body(product)
                .when()
                .post(baseUrlProduct)
                .then()
                .statusCode(400)
                .body("nome", equalTo("nome deve ser uma string"));
    }

    @Test
    public void createProductNameNull(){
        ProductPojo product = new ProductDataFactory().product();
        product.setNome(null);
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenIdAdm)
                .body(product)
                .when()
                .post(baseUrlProduct)
                .then()
                .statusCode(400)
                .body("nome", equalTo("nome deve ser uma string"));
    }

    @Test
    public void createProductWithoutPrice(){
        ProductPojo product = new ProductDataFactory().productWithoutPrice();
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenIdAdm)
                .body(product)
                .when()
                .post(baseUrlProduct)
                .then()
                .statusCode(400)
                .body("preco", equalTo("preco deve ser um número"));
    }

    @Test
    public void createProductPriceNull(){
        ProductPojo product = new ProductDataFactory().product();
        product.setPreco(null);
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenIdAdm)
                .body(product)
                .when()
                .post(baseUrlProduct)
                .then()
                .statusCode(400)
                .body("preco", equalTo("preco deve ser um número"));
    }

    @Test
    public void createProductWithoutQuantity(){
        ProductPojo product = new ProductDataFactory().productWithoutQuantity();
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenIdAdm)
                .body(product)
                .when()
                .post(baseUrlProduct)
                .then()
                .statusCode(400)
                .body("quantidade", equalTo("quantidade deve ser um número"));
    }

    @Test
    public void createProductQuantityNull(){
        ProductPojo product = new ProductDataFactory().product();
        product.setQuantidade(null);
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenIdAdm)
                .body(product)
                .when()
                .post(baseUrlProduct)
                .then()
                .statusCode(400)
                .body("quantidade", equalTo("quantidade deve ser um número"));
    }

    @Test
    public void createProductWithoutDescription(){
        ProductPojo product = new ProductDataFactory().productWithoutDescription();
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenIdAdm)
                .body(product)
                .when()
                .post(baseUrlProduct)
                .then()
                .statusCode(400)
                .body("descricao", equalTo("descricao deve ser uma string"));
    }

    @Test
    public void createProductDescriptionNull(){
        ProductPojo product = new ProductDataFactory().product();
        product.setDescricao(null);
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenIdAdm)
                .body(product)
                .when()
                .post(baseUrlProduct)
                .then()
                .statusCode(400)
                .body("descricao", equalTo("descricao deve ser uma string"));
    }

    @Test
    public void createProductFieldRequire(){
        ProductPojo product = new ProductDataFactory().product();
        given()
                .headers("Authorization", tokenIdAdm)
                .body(product)
                .when()
                .post(baseUrlProduct)
                .then()
                .statusCode(400)
                .body("nome", equalTo("nome é obrigatório"))
                .body("preco", equalTo("preco é obrigatório"))
                .body("descricao", equalTo("descricao é obrigatório"))
                .body("quantidade", equalTo("quantidade é obrigatório"));
    }
}
