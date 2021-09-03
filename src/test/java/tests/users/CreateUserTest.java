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
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class CreateUserTest {

    @Test
    public void testUserContract(){
        CreateUsersPojo user = new UserDataFactory().userAdm();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(baseUrlUser)
                .then()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("schema/user-schema.json"));
    }

    @Test
    public void testCreateAdmUser(){
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
    public void testCreateUser(){
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
    public void testCreateUserAdmWithoutType(){
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
    public void testCreateUserTypeAdmNull(){
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
    public void testCreateUserTypeAdmIncorrect(){
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
    public void testCreateUserWithoutName(){
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
    public void testCreateUserNameNull(){
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
    public void testCreateUserNameEmpty(){
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
    public void testCreateUserWithoutEmail(){
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
    public void testCreateUserEmailIncorrect(){
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
    public void testCreateUserEmailExistent(){
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
    public void testCreateUserEmailEmpty(){
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
    public void testCreateUserWithoutPassword(){
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
    public void testCreateUserPasswordNull(){
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
    public void testCreateUserPasswordEmpty(){
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
    public void testCreateUserFieldsRequired(){
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
