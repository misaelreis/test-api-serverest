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

public class ConcludeCarTest {

    public String tokenId;
    public String productId;

    @Before
    public void createUsersAndLogin(){
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
    public void testConcludeCar(){
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
                .statusCode(200)
                .body("message", equalTo("Registro excluído com sucesso"));
    }

    @Test
    public void testConcludeCarWithoutCar(){
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenId)
                .delete(baseUrlCar.concat("concluir-compra"))
                .then()
                .statusCode(200)
                .body("message", equalTo("Não foi encontrado carrinho para esse usuário"));
    }

    @Test
    public void testConcludeCarWithoutToken(){
        given()
                .contentType(ContentType.JSON)
                .delete(baseUrlCar.concat("concluir-compra"))
                .then()
                .statusCode(401)
                .body("message", equalTo("Token de acesso ausente, inválido, expirado ou usuário do token não existe mais"));
    }
}
