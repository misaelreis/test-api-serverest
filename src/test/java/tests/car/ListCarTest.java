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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static utils.BaseUrlUtil.baseUrlCar;
import static utils.BaseUrlUtil.baseUrlProduct;

public class ListCarTest {

    public String tokenId;
    public String productId;
    public String carId;

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
    public void testListCarForId(){
        CarPojo car = new CarDataFactory().car();
        car.addProducts((new ProductCarPojo(productId,1)));
        carId = given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenId)
                .body(car)
                .when()
                .post(baseUrlCar)
                .then()
                .statusCode(201)
                .extract().path("_id");

        given()
                .contentType(ContentType.JSON)
                .when()
                .get(baseUrlCar.concat(carId))
                .then()
                .statusCode(200)
                .body("_id", equalTo(carId));

        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenId)
                .delete(baseUrlCar.concat("concluir-compra"))
                .then()
                .statusCode(200)
                .body("message", equalTo("Registro excluído com sucesso"));
    }

    @Test
    public void testListCars(){
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(baseUrlCar)
                .then()
                .statusCode(200);
    }

    @Test
    public void testListCarIncorrectParams(){
        given()
                .contentType(ContentType.JSON)
                .queryParam("id", 1)
                .when()
                .get(baseUrlCar)
                .then()
                .statusCode(400)
                .body("id", equalTo("id não é permitido"));
    }

    @Test
    public void testListCarTypeParamsIncorrect(){
        given()
                .contentType(ContentType.JSON)
                .queryParam("precoTotal", "1")
                .queryParam("_id", 1)
                .queryParam("quantidadeTotal", "1")
                .queryParam("idUsuario",1)
                .when()
                .get(baseUrlCar)
                .then()
                .statusCode(200)
                .body("quantidade", equalTo(0));
    }
}
