package tests.product;

import factories.CarDataFactory;
import factories.ProductDataFactory;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import pojo.CarPojo;
import pojo.ProductCarPojo;
import pojo.ProductPojo;
import utils.LoginUtil;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static utils.BaseUrlUtil.baseUrlCar;
import static utils.BaseUrlUtil.baseUrlProduct;

public class DeleteProductTest {


    public String tokenId;
    public String tokenIdUser;
    public String productId;
    public String carTokenId;

    @Before
    public void createUsersAndLogin(){
        tokenId = new LoginUtil().loginAdmFix();
        tokenIdUser = new LoginUtil().loginUserFix();
    }

    @Test
    public void testDeleteProduct(){
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
                .headers("Authorization", tokenId)
                .body(product)
                .when()
                .delete(baseUrlProduct.concat(productId))
                .then()
                .statusCode(200)
                .body("message", equalTo("Registro excluído com sucesso"));
    }

    @Test
    public void testDeleteProductAbsent(){
        ProductPojo product = new ProductDataFactory().product();
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenId)
                .body(product)
                .when()
                .delete(baseUrlProduct.concat("productId"))
                .then()
                .statusCode(200)
                .body("message", equalTo("Nenhum registro excluído"));
    }

    @Test
    public void testDeleteProductTokenUser(){
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
                .headers("Authorization", tokenIdUser)
                .body(product)
                .when()
                .delete(baseUrlProduct.concat(productId))
                .then()
                .statusCode(403)
                .body("message", equalTo("Rota exclusiva para administradores"));
    }

    @Test
    public void testDeleteProductWithoutToken(){
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
                .headers("Authorization", "tokenIdUser")
                .body(product)
                .when()
                .delete(baseUrlProduct.concat(productId))
                .then()
                .statusCode(401)
                .body("message", equalTo("Token de acesso ausente, inválido, expirado ou usuário do token não existe mais"));
    }

    @Test
    public void testDeleteProductWithCar(){
        carTokenId = new LoginUtil().loginUserCar();
        ProductPojo product = new ProductDataFactory().product();
        productId = given()
                .contentType(ContentType.JSON)
                .headers("Authorization", carTokenId)
                .body(product)
                .when()
                .post(baseUrlProduct)
                .then()
                .statusCode(201)
                .body("message", equalTo("Cadastro realizado com sucesso"))
                .body("_id", notNullValue())
                .extract().path("_id");

        CarPojo car = new CarDataFactory().car();
        car.addProducts((new ProductCarPojo(productId,1)));
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", carTokenId)
                .body(car)
                .when()
                .post(baseUrlCar)
                .then();

        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", carTokenId)
                .body(product)
                .when()
                .delete(baseUrlProduct.concat(productId))
                .then()
                .statusCode(400)
                .body("message", equalTo("Não é permitido excluir produto que faz parte de carrinho"));

        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", carTokenId)
                .delete(baseUrlCar.concat("concluir-compra"))
                .then()
                .statusCode(200);
    }
}
