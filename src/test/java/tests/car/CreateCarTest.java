package tests.car;

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
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static utils.BaseUrlUtil.baseUrlCar;
import static utils.BaseUrlUtil.baseUrlProduct;

public class CreateCarTest {

    public String tokenId;
    public String productId;

    @Before
    public void createProductAndLogin(){
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
    }

    @Test
    public void testCarContract(){
        CarPojo car = new CarDataFactory().car();
        car.addProducts((new ProductCarPojo(productId,1)));
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenId)
                .body(car)
                .when()
                .post(baseUrlCar)
                .then()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("schema/car-schema.json"));

        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenId)
                .delete(baseUrlCar.concat("concluir-compra"))
                .then()
                .body("message", equalTo("Registro excluído com sucesso"));
    }

    @Test
    public void testCreateCar(){
        CarPojo car = new CarDataFactory().car();
        car.addProducts((new ProductCarPojo(productId,1)));
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenId)
                .body(car)
                .when()
                .post(baseUrlCar)
                .then()
                .statusCode(201)
                .body("message", equalTo("Cadastro realizado com sucesso"))
                .body("_id", notNullValue());

        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenId)
                .delete(baseUrlCar.concat("concluir-compra"))
                .then()
                .body("message", equalTo("Registro excluído com sucesso"));
    }

    @Test
    public void testCreateTwoCars(){
        CarPojo car = new CarDataFactory().car();
        car.addProducts((new ProductCarPojo(productId,1)));
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenId)
                .body(car)
                .when()
                .post(baseUrlCar)
                .then()
                .statusCode(201)
                .body("message", equalTo("Cadastro realizado com sucesso"))
                .body("_id", notNullValue());

        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenId)
                .body(car)
                .when()
                .post(baseUrlCar)
                .then()
                .statusCode(400)
                .body("message", equalTo("Não é permitido ter mais de 1 carrinho"));

        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenId)
                .delete(baseUrlCar.concat("concluir-compra"))
                .then()
                .body("message", equalTo("Registro excluído com sucesso"));
    }

    @Test
    public void testCreateCarQuantityProductInsufficient(){
        CarPojo car = new CarDataFactory().car();
        car.addProducts((new ProductCarPojo(productId,100)));
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenId)
                .body(car)
                .when()
                .post(baseUrlCar)
                .then()
                .statusCode(400)
                .body("message", equalTo("Produto não possui quantidade suficiente"));
    }

    @Test
    public void testCreateCarWithoutToken(){
        CarPojo car = new CarDataFactory().car();
        car.addProducts((new ProductCarPojo(productId,1)));
        given()
                .contentType(ContentType.JSON)
                .body(car)
                .when()
                .post(baseUrlCar)
                .then()
                .statusCode(401)
                .body("message", equalTo("Token de acesso ausente, inválido, expirado ou usuário do token não existe mais"));
    }

    @Test
    public void testCreateCarFieldRequired(){
        CarPojo car = new CarDataFactory().car();
        car.addProducts((new ProductCarPojo(productId,1)));
        given()
                .headers("Authorization", tokenId)
                .body(car)
                .when()
                .post(baseUrlCar)
                .then()
                .statusCode(400)
                .body("produtos", equalTo("produtos é obrigatório"));
    }

    @Test
    public void testCreateCarWithoutProducts(){
        CarPojo car = new CarDataFactory().car();
        car.addProducts((new ProductCarPojo("1",1)));
        given()
                .headers("Authorization", tokenId)
                .body(car)
                .when()
                .post(baseUrlCar)
                .then()
                .statusCode(400)
                .body("produtos", equalTo("produtos é obrigatório"));
    }
}
