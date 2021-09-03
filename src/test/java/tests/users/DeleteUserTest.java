package tests.users;

import factories.CarDataFactory;
import factories.ProductDataFactory;
import factories.UserDataFactory;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import pojo.CarPojo;
import pojo.CreateUsersPojo;
import pojo.ProductCarPojo;
import pojo.ProductPojo;
import utils.LoginUtil;
import utils.UserUtil;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static utils.BaseUrlUtil.*;

public class DeleteUserTest {

    public String ID;
    public String tokenId;
    public String idUser;
    public String productId;

    @Before
    public void createUsersAndLogin(){
        CreateUsersPojo user = new UserDataFactory().userAdm();
        UserUtil us = new UserUtil().userCar();

        idUser = given()
                .contentType(ContentType.JSON)
                .queryParam("email","misael@carro.com.br")
                .when()
                .get(baseUrlUser)
                .then()
                .statusCode(200)
                .extract().path("usuarios[0]._id");

        tokenId = new LoginUtil().loginUserCar();
    }


    @Test
    public void testDeleteUser(){
        CreateUsersPojo user = new UserDataFactory().userAdm();
        ID = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(baseUrlUser)
                .then()
                .statusCode(201)
                .extract().path("_id");

        given()
                .contentType(ContentType.JSON)
                .when()
                .delete(baseUrlUser.concat(ID))
                .then()
                .statusCode(200)
                .body("message", equalTo("Registro excluído com sucesso"));
    }

    @Test
    public void testDeleteUserIdIncorrect(){
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete(baseUrlUser.concat("ID"))
                .then()
                .statusCode(200)
                .body("message", equalTo("Nenhum registro excluído"));
    }

    @Test
    public void testDeleteUserWithCar(){
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

        CarPojo car = new CarDataFactory().car();
        car.addProducts((new ProductCarPojo(productId,1)));
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenId)
                .body(car)
                .when()
                .post(baseUrlCar)
                .then();

        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenId)
                .body(product)
                .when()
                .delete(baseUrlUser.concat(idUser))
                .then()
                .statusCode(400)
                .body("message", equalTo("Não é permitido excluir usuário com carrinho cadastrado"));

        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenId)
                .delete(baseUrlCar.concat("concluir-compra"))
                .then()
                .statusCode(200);
    }
}
