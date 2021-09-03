package tests.product;

import factories.ProductDataFactory;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import pojo.ProductPojo;
import utils.LoginUtil;
import utils.ProductUtil;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static utils.BaseUrlUtil.baseUrlProduct;

public class UpdateProductTest {

    public String tokenAdm;
    public String tokenUser;
    public String productId;

    @Before
    public void createProductAndToken(){
        tokenAdm = new LoginUtil().loginAdmFix();
        ProductPojo product = new ProductDataFactory().product();
        productId = given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenAdm)
                .body(product)
                .when()
                .post(baseUrlProduct)
                .then()
                .statusCode(201)
                .body("message", equalTo("Cadastro realizado com sucesso"))
                .body("_id", notNullValue())
                .extract().path("_id");
    }

    @Test
    public void testUpdateProduct(){
        ProductPojo product = new ProductDataFactory().product();
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenAdm)
                .body(product)
                .when()
                .put(baseUrlProduct.concat(productId))
                .then()
                .statusCode(200)
                .body("message", equalTo("Registro alterado com sucesso"));
    }

    @Test
    public void testUpdateProductNameExistent(){
        ProductPojo product = new ProductDataFactory().product();
        ProductUtil prod = new ProductUtil().createProduct();
        product.setNome("Nome cadastrado");
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenAdm)
                .body(product)
                .when()
                .put(baseUrlProduct.concat(productId))
                .then()
                .statusCode(400)
                .body("message", equalTo("Já existe produto com esse nome"));
    }

    @Test
    public void testUpdateProductWithoutName(){
        ProductPojo productSn = new ProductDataFactory().productWithoutName();
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenAdm)
                .body(productSn)
                .when()
                .put(baseUrlProduct.concat(productId))
                .then()
                .statusCode(400)
                .body("nome", equalTo("nome deve ser uma string"));
    }

    @Test
    public void testUpdateProductNameNull(){
        ProductPojo productSn = new ProductDataFactory().product();
        productSn.setNome(null);
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenAdm)
                .body(productSn)
                .when()
                .put(baseUrlProduct.concat(productId))
                .then()
                .statusCode(400)
                .body("nome", equalTo("nome deve ser uma string"));
    }

    @Test
    public void testUpdateProductWithoutDescription(){
        ProductPojo productSn = new ProductDataFactory().productWithoutDescription();
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenAdm)
                .body(productSn)
                .when()
                .put(baseUrlProduct.concat(productId))
                .then()
                .statusCode(400)
                .body("descricao", equalTo("descricao deve ser uma string"));
    }

    @Test
    public void testUpdateProductDescriptionNull(){
        ProductPojo productSn = new ProductDataFactory().product();
        productSn.setDescricao(null);
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenAdm)
                .body(productSn)
                .when()
                .put(baseUrlProduct.concat(productId))
                .then()
                .statusCode(400)
                .body("descricao", equalTo("descricao deve ser uma string"));
    }

    @Test
    public void testUpdateProductWithoutPrice(){
        ProductPojo productSn = new ProductDataFactory().productWithoutPrice();
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenAdm)
                .body(productSn)
                .when()
                .put(baseUrlProduct.concat(productId))
                .then()
                .statusCode(400)
                .body("preco", equalTo("preco deve ser um número"));
    }

    @Test
    public void testUpdateProductPriceNull(){
        ProductPojo productSn = new ProductDataFactory().product();
        productSn.setPreco(null);
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenAdm)
                .body(productSn)
                .when()
                .put(baseUrlProduct.concat(productId))
                .then()
                .statusCode(400)
                .body("preco", equalTo("preco deve ser um número"));
    }

    @Test
    public void testUpdateProductWithoutQuantity(){
        ProductPojo productSn = new ProductDataFactory().productWithoutQuantity();
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenAdm)
                .body(productSn)
                .when()
                .put(baseUrlProduct.concat(productId))
                .then()
                .statusCode(400)
                .body("quantidade", equalTo("quantidade deve ser um número"));
    }

    @Test
    public void testUpdateProductQuantityNull(){
        ProductPojo productSn = new ProductDataFactory().product();
        productSn.setQuantidade(null);
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenAdm)
                .body(productSn)
                .when()
                .put(baseUrlProduct.concat(productId))
                .then()
                .statusCode(400)
                .body("quantidade", equalTo("quantidade deve ser um número"));
    }

    @Test
    public void testUpdateProductIdUser(){
        tokenUser = new LoginUtil().loginUserFix();
        ProductPojo product = new ProductDataFactory().product();
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenUser)
                .body(product)
                .when()
                .put(baseUrlProduct.concat(productId))
                .then()
                .statusCode(403)
                .body("message", equalTo("Rota exclusiva para administradores"));
    }

    @Test
    public void testUpdateProductWithoutToken(){
        ProductPojo product = new ProductDataFactory().product();
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", "tokenIdUser")
                .body(product)
                .when()
                .put(baseUrlProduct.concat(productId))
                .then()
                .statusCode(401)
                .body("message", equalTo("Token de acesso ausente, inválido, expirado ou usuário do token não existe mais"));
    }

    @Test
    public void testUpdateProductIdIncorrect(){
        ProductPojo product = new ProductDataFactory().product();
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenAdm)
                .body(product)
                .when()
                .put(baseUrlProduct.concat("productId"))
                .then()
                .statusCode(201)
                .body("message", equalTo("Cadastro realizado com sucesso"));
    }

    @Test
    public void testUpdateProductFieldsRequire(){
        ProductPojo product = new ProductDataFactory().product();
        given()
                .headers("Authorization", tokenAdm)
                .body(product)
                .when()
                .put(baseUrlProduct.concat(productId))
                .then()
                .statusCode(400)
                .body("nome", equalTo("nome é obrigatório"))
                .body("preco", equalTo("preco é obrigatório"))
                .body("descricao", equalTo("descricao é obrigatório"))
                .body("quantidade", equalTo("quantidade é obrigatório"));
    }
}
