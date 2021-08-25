package utils;

import factories.UserDataFactory;
import io.restassured.http.ContentType;
import pojo.CreateUsersPojo;

import static io.restassured.RestAssured.given;
import static utils.BaseUrlUtil.baseUrlUser;

public class UserUtil {

    public String id;

    public UserUtil createUserAdm(){
        CreateUsersPojo user = new UserDataFactory().userAdm();
        user.setNome("Misael Reis");
        user.setEmail("misael@qa.com.br");
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(baseUrlUser)
                .then();
        return null;
    }

    public UserUtil createUser(){
        CreateUsersPojo user = new UserDataFactory().userAdm();
        user.setNome("Misael Usuario");
        user.setEmail("misael@email.com");
        user.setAdministrador("false");
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(baseUrlUser)
                .then();
        return null;
    }

    public String createUserId(){
        CreateUsersPojo user = new UserDataFactory().userAdm();
        id = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(baseUrlUser)
                .then()
                .extract().path("_id");
        return id;
    }

    public UserUtil userCar() {
        CreateUsersPojo user = new UserDataFactory().userAdm();
        user.setNome("Misael carro");
        user.setEmail("misael@carro.com.br");
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(baseUrlUser)
                .then();
        return null;
    }
}
