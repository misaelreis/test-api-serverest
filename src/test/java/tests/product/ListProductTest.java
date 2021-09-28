package tests.product;

import factories.ProductDataFactory;
import io.restassured.http.ContentType;
import org.junit.Test;
import pojo.ProductPojo;
import utils.LoginUtil;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static utils.BaseUrlUtil.baseUrlProduct;

public class ListProductTest {

    public String tokenId;
    public String productId;

    @Test
    public void testListProducts(){
        ProductPojo product = new ProductDataFactory().product();
        given()
                .contentType(ContentType.JSON)
                .body(product)
                .when()
                .get(baseUrlProduct)
                .then()
                .statusCode(200)
                .body("produtos._id[0]", notNullValue());
    }

    @Test
    public void testListProductsForId(){
        tokenId = new LoginUtil().loginAdmFix();
        ProductPojo product = new ProductDataFactory().product();
        productId = given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenId)
                .body(product)
                .when()
                .post(baseUrlProduct)
                .then()
                .statusCode(201)
                .body("message", equalTo("Cadastro realizado com sucesso"))
                .body("_id", notNullValue())
                .extract().path("_id");
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(baseUrlProduct.concat(productId))
                .then()
                .statusCode(200)
                .body("_id[0]", notNullValue());
    }

    @Test
    public void testListProductsParams(){
        ProductPojo product = new ProductDataFactory().product();
        given()
                .contentType(ContentType.JSON)
                .queryParam("preco", 100)
                .queryParam("quantidade", 3)
                .body(product)
                .when()
                .get(baseUrlProduct)
                .then()
                .statusCode(200);
    }

    @Test
    public void testListProductsParamsIncorrect(){
        ProductPojo product = new ProductDataFactory().product();
        given()
                .contentType(ContentType.JSON)
                .queryParam("Preco", 100)
                .queryParam("Quantidade", 3)
                .body(product)
                .when()
                .get(baseUrlProduct)
                .then()
                .statusCode(400)
                .body("Quantidade", equalTo("Quantidade não é permitido"))
                .body("Preco", equalTo("Preco não é permitido"));
    }
}
