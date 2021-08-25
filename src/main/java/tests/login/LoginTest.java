package tests.login;

import factories.LoginDataFactory;
import io.restassured.http.ContentType;
import org.junit.Test;
import pojo.LoginPojo;
import utils.UserUtil;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static utils.BaseUrlUtil.baseUrlLogin;

public class LoginTest {

    @Test
    public void loginSuccess(){
        UserUtil user = new UserUtil().createUserAdm();
        LoginPojo login = LoginDataFactory.loginSuccess();
        given()
                .contentType(ContentType.JSON)
                .body(login)
                .when()
                .post(baseUrlLogin)
                .then()
                .statusCode(200)
                .body("authorization", notNullValue())
                .body("message", equalTo("Login realizado com sucesso"));
    }

    @Test
    public void loginWithoutEmail(){
        LoginPojo login = LoginDataFactory.loginWithoutEmail();
        given()
                .contentType(ContentType.JSON)
                .body(login)
                .when()
                .post(baseUrlLogin)
                .then()
                .statusCode(400)
                .body("email", equalTo("email deve ser uma string"));
    }

    @Test
    public void loginWithoutPassword(){
        LoginPojo login = LoginDataFactory.loginWithoutPassword();
        given()
                .contentType(ContentType.JSON)
                .body(login)
                .when()
                .post(baseUrlLogin)
                .then()
                .statusCode(400)
                .body("password", equalTo("password deve ser uma string"));
    }

    @Test
    public void loginEmailEmpty(){
        LoginPojo login = LoginDataFactory.loginSuccess();
        login.setEmail("");
        given()
                .contentType(ContentType.JSON)
                .body(login)
                .when()
                .post(baseUrlLogin)
                .then()
                .statusCode(400)
                .body("email", equalTo("email não pode ficar em branco"));
    }

    @Test
    public void loginPasswordEmpty(){
        LoginPojo login = LoginDataFactory.loginSuccess();
        login.setPassword("");
        given()
                .contentType(ContentType.JSON)
                .body(login)
                .when()
                .post(baseUrlLogin)
                .then()
                .statusCode(400)
                .body("password", equalTo("password não pode ficar em branco"));
    }

    @Test
    public void loginEmailNull(){
        LoginPojo login = LoginDataFactory.loginSuccess();
        login.setEmail(null);
        given()
                .contentType(ContentType.JSON)
                .body(login)
                .when()
                .post(baseUrlLogin)
                .then()
                .statusCode(400)
                .body("email", equalTo("email deve ser uma string"));
    }

    @Test
    public void loginPasswordNull(){
        LoginPojo login = LoginDataFactory.loginSuccess();
        login.setPassword(null);
        given()
                .contentType(ContentType.JSON)
                .body(login)
                .when()
                .post(baseUrlLogin)
                .then()
                .statusCode(400)
                .body("password", equalTo("password deve ser uma string"));
    }

    @Test
    public void loginEmailIncorrect(){
        LoginPojo login = LoginDataFactory.loginSuccess();
        login.setEmail("teste.com.br");
        given()
                .contentType(ContentType.JSON)
                .body(login)
                .when()
                .post(baseUrlLogin)
                .then()
                .statusCode(400)
                .body("email", equalTo("email deve ser um email válido"));
    }

    @Test
    public void loginEmailNoRegistered(){
        LoginPojo login = LoginDataFactory.loginSuccess();
        login.setEmail("misael@email.com.br");
        given()
                .contentType(ContentType.JSON)
                .body(login)
                .when()
                .post(baseUrlLogin)
                .then()
                .statusCode(401)
                .body("message", equalTo("Email e/ou senha inválidos"));
    }

    @Test
    public void loginPasswordIncorrect(){
        LoginPojo login = LoginDataFactory.loginSuccess();
        login.setPassword("123");
        given()
                .contentType(ContentType.JSON)
                .body(login)
                .when()
                .post(baseUrlLogin)
                .then()
                .statusCode(401)
                .body("message", equalTo("Email e/ou senha inválidos"));
    }

    @Test
    public void loginFieldsRequired(){
        LoginPojo login = LoginDataFactory.loginSuccess();
        given()
                .body(login)
                .when()
                .post(baseUrlLogin)
                .then()
                .statusCode(400)
                .body("password", equalTo("password é obrigatório"))
                .body("email", equalTo("email é obrigatório"));
    }
}
