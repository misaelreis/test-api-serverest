package utils;

import factories.LoginDataFactory;
import io.restassured.http.ContentType;
import pojo.LoginPojo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static utils.BaseUrlUtil.baseUrlLogin;

public class LoginUtil {

    String tokenIdAdm;
    String tokenIdUser;

    public String loginAdmFix(){
        UserUtil user = new UserUtil().createUserAdm();
        LoginPojo login = LoginDataFactory.loginSuccess();
        tokenIdAdm = given()
                .contentType(ContentType.JSON)
                .body(login)
                .when()
                .post(baseUrlLogin)
                .then()
                .statusCode(200)
                .body("authorization", notNullValue())
                .body("message", equalTo("Login realizado com sucesso"))
                .extract().path("authorization");
        return tokenIdAdm;
    }

    public String loginUserFix(){
        UserUtil user = new UserUtil().createUser();
        LoginPojo login = LoginDataFactory.loginSuccess();
        login.setEmail("misael@email.com");
        tokenIdUser = given()
                .contentType(ContentType.JSON)
                .body(login)
                .when()
                .post(baseUrlLogin)
                .then()
                .statusCode(200)
                .body("authorization", notNullValue())
                .body("message", equalTo("Login realizado com sucesso"))
                .extract().path("authorization");
        return tokenIdUser;
    }

    public String loginUserCar(){
        UserUtil user = new UserUtil().userCar();
        LoginPojo login = LoginDataFactory.loginSuccess();
        login.setEmail("misael@carro.com.br");
        tokenIdUser = given()
                .contentType(ContentType.JSON)
                .body(login)
                .when()
                .post(baseUrlLogin)
                .then()
                .statusCode(200)
                .body("authorization", notNullValue())
                .body("message", equalTo("Login realizado com sucesso"))
                .extract().path("authorization");
        return tokenIdUser;
    }
}
