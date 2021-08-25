package tests.users;

import factories.UserDataFactory;
import io.restassured.http.ContentType;
import org.junit.Test;
import pojo.CreateUsersPojo;
import utils.UserUtil;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static utils.BaseUrlUtil.baseUrlUser;

public class CreateUserTest {

    @Test
    public void createAdmUser(){
        CreateUsersPojo user = new UserDataFactory().userAdm();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(baseUrlUser)
                .then()
                .statusCode(201)
                .body("message", equalTo("Cadastro realizado com sucesso"))
                .body("_id", notNullValue());
    }

    @Test
    public void createUser(){
        CreateUsersPojo user = new UserDataFactory().userAdm();
        user.setAdministrador("false");
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(baseUrlUser)
                .then()
                .statusCode(201)
                .body("message", equalTo("Cadastro realizado com sucesso"))
                .body("_id", notNullValue());
    }

    @Test
    public void createUserAdmWithoutType(){
        CreateUsersPojo user = new UserDataFactory().userWithoutTypeAdm();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(baseUrlUser)
                .then()
                .statusCode(400)
                .body("administrador", equalTo("administrador deve ser 'true' ou 'false'"));
    }

    @Test
    public void createUserTypeAdmNull(){
        CreateUsersPojo user = new UserDataFactory().userAdm();
        user.setAdministrador(null);
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(baseUrlUser)
                .then()
                .statusCode(400)
                .body("administrador", equalTo("administrador deve ser 'true' ou 'false'"));
    }

    @Test
    public void createUserTypeAdmIncorrect(){
        CreateUsersPojo user = new UserDataFactory().userAdm();
        user.setAdministrador("ok");
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(baseUrlUser)
                .then()
                .statusCode(400)
                .body("administrador", equalTo("administrador deve ser 'true' ou 'false'"));
    }

    @Test
    public void createUserWithoutName(){
        CreateUsersPojo user = new UserDataFactory().userWithoutName();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(baseUrlUser)
                .then()
                .statusCode(400)
                .body("nome", equalTo("nome deve ser uma string"));
    }

    @Test
    public void createUserNameNull(){
        CreateUsersPojo user = new UserDataFactory().userAdm();
        user.setNome(null);
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(baseUrlUser)
                .then()
                .statusCode(400)
                .body("nome", equalTo("nome deve ser uma string"));
    }

    @Test
    public void createUserNameEmpty(){
        CreateUsersPojo user = new UserDataFactory().userAdm();
        user.setNome("");
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(baseUrlUser)
                .then()
                .statusCode(400)
                .body("nome", equalTo("nome não pode ficar em branco"));
    }

    @Test
    public void createUserWithoutEmail(){
        CreateUsersPojo user = new UserDataFactory().userWithoutEmail();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(baseUrlUser)
                .then()
                .statusCode(400)
                .body("email", equalTo("email deve ser uma string"));
    }

    @Test
    public void createUserEmailIncorrect(){
        CreateUsersPojo user = new UserDataFactory().userAdm();
        user.setEmail("2");
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(baseUrlUser)
                .then()
                .statusCode(400)
                .body("email", equalTo("email deve ser um email válido"));
    }

    @Test
    public void createUserEmailExistent(){
        UserUtil userAdm = new UserUtil().createUserAdm();
        CreateUsersPojo user = new UserDataFactory().userAdm();
        user.setEmail("misael@qa.com.br");
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(baseUrlUser)
                .then()
                .statusCode(400)
                .body("message", equalTo("Este email já está sendo usado"));
    }

    @Test
    public void createUserEmailEmpty(){
        CreateUsersPojo user = new UserDataFactory().userAdm();
        user.setEmail("");
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(baseUrlUser)
                .then()
                .statusCode(400)
                .body("email", equalTo("email não pode ficar em branco"));
    }

    @Test
    public void createUserWithoutPassword(){
        CreateUsersPojo user = new UserDataFactory().userWithoutPassword();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(baseUrlUser)
                .then()
                .statusCode(400)
                .body("password", equalTo("password deve ser uma string"));
    }

    @Test
    public void createUserPasswordNull(){
        CreateUsersPojo user = new UserDataFactory().userAdm();
        user.setPassword(null);
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(baseUrlUser)
                .then()
                .statusCode(400)
                .body("password", equalTo("password deve ser uma string"));
    }

    @Test
    public void createUserPasswordEmpty(){
        CreateUsersPojo user = new UserDataFactory().userAdm();
        user.setPassword("");
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(baseUrlUser)
                .then()
                .statusCode(400)
                .body("password", equalTo("password não pode ficar em branco"));
    }

    @Test
    public void createUserFieldsRequired(){
        CreateUsersPojo user = new UserDataFactory().userAdm();
        given()
                .body(user)
                .when()
                .post(baseUrlUser)
                .then()
                .statusCode(400)
                .body("nome", equalTo("nome é obrigatório"))
                .body("email", equalTo("email é obrigatório"))
                .body("password", equalTo("password é obrigatório"))
                .body("administrador", equalTo("administrador é obrigatório"));

    }
}
