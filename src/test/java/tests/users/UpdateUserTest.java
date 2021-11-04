package tests.users;

import factories.UserDataFactory;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import pojo.CreateUsersPojo;
import utils.UserUtil;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static utils.BaseUrlUtil.baseUrlUser;

public class UpdateUserTest {
    public String id;

    @Before
    public void createUser() {
        id = new UserUtil().createUserId();
    }

    @Test
    public void testUpdateUserAdm(){
        CreateUsersPojo user = new UserDataFactory().userAdm();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put(baseUrlUser.concat(id))
                .then()
                .statusCode(200)
                .body("message", equalTo("Registro alterado com sucesso"));
    }

    @Test
    public void testUpdateUser(){
        CreateUsersPojo user = new UserDataFactory().userAdm();
        user.setAdministrador("false");
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put(baseUrlUser.concat(id))
                .then()
                .statusCode(200)
                .body("message", equalTo("Registro alterado com sucesso"));
    }

    @Test
    public void testUpdateUserIncorrectId(){
        CreateUsersPojo user = new UserDataFactory().userAdm();
        user.setAdministrador("false");
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put(baseUrlUser.concat("id"))
                .then()
                .statusCode(201)
                .body("_id", notNullValue())
                .body("message", equalTo("Cadastro realizado com sucesso"));
    }

    @Test
    public void testUpdateUserWithoutTypeAdm(){
        CreateUsersPojo user = new UserDataFactory().userWithoutTypeAdm();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put(baseUrlUser.concat(id))
                .then()
                .statusCode(400)
                .body("administrador", equalTo("administrador deve ser 'true' ou 'false'"));
    }

    @Test
    public void testUpdateUserTypeAdmNull(){
        CreateUsersPojo user = new UserDataFactory().userAdm();
        user.setAdministrador(null);
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put(baseUrlUser.concat(id))
                .then()
                .statusCode(400)
                .body("administrador", equalTo("administrador deve ser 'true' ou 'false'"));
    }

    @Test
    public void testUpdateUserTypeAdmIncorrect(){
        CreateUsersPojo user = new UserDataFactory().userAdm();
        user.setAdministrador("ok");
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put(baseUrlUser.concat(id))
                .then()
                .statusCode(400)
                .body("administrador", equalTo("administrador deve ser 'true' ou 'false'"));
    }

    @Test
    public void testUpdateUserWithoutName(){
        CreateUsersPojo user = new UserDataFactory().userWithoutName();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put(baseUrlUser.concat(id))
                .then()
                .statusCode(400)
                .body("nome", equalTo("nome deve ser uma string"));
    }

    @Test
    public void testUpdateUserNameNull(){
        CreateUsersPojo user = new UserDataFactory().userAdm();
        user.setNome(null);
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put(baseUrlUser.concat(id))
                .then()
                .statusCode(400)
                .body("nome", equalTo("nome deve ser uma string"));
    }

    @Test
    public void testUpdateUserNameEmpty(){
        CreateUsersPojo user = new UserDataFactory().userAdm();
        user.setNome("");
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put(baseUrlUser.concat(id))
                .then()
                .statusCode(400)
                .body("nome", equalTo("nome não pode ficar em branco"));
    }

    @Test
    public void testUpdateUserWithoutEmail(){
        CreateUsersPojo user = new UserDataFactory().userWithoutEmail();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put(baseUrlUser.concat(id))
                .then()
                .statusCode(400)
                .body("email", equalTo("email deve ser uma string"));
    }

    @Test
    public void testUpdateUserEmailIncorrect(){
        CreateUsersPojo user = new UserDataFactory().userAdm();
        user.setEmail("2");
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put(baseUrlUser.concat(id))
                .then()
                .statusCode(400)
                .body("email", equalTo("email deve ser um email válido"));
    }

    @Test
    public void testUpdateUserEmailExistent(){
        CreateUsersPojo user = new UserDataFactory().userAdm();
        user.setEmail("batatinhafrita@123.com.br");
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(baseUrlUser);
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put(baseUrlUser.concat(id))
                .then()
                .statusCode(400)
                .body("message", equalTo("Este email já está sendo usado"));
    }

    @Test
    public void testUpdateUserEmptyEmail(){
        CreateUsersPojo user = new UserDataFactory().userAdm();
        user.setEmail("");
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put(baseUrlUser.concat(id))
                .then()
                .statusCode(400)
                .body("email", equalTo("email não pode ficar em branco"));
    }

    @Test
    public void testUpdateUserWithoutPassword(){
        CreateUsersPojo user = new UserDataFactory().userWithoutPassword();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put(baseUrlUser.concat(id))
                .then()
                .statusCode(400)
                .body("password", equalTo("password deve ser uma string"));
    }

    @Test
    public void testUpdateUserPasswordNull(){
        CreateUsersPojo user = new UserDataFactory().userAdm();
        user.setPassword(null);
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put(baseUrlUser.concat(id))
                .then()
                .statusCode(400)
                .body("password", equalTo("password deve ser uma string"));
    }

    @Test
    public void testUpdateUserPasswordEmpty(){
        CreateUsersPojo user = new UserDataFactory().userAdm();
        user.setPassword("");
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put(baseUrlUser.concat(id))
                .then()
                .statusCode(400)
                .body("password", equalTo("password não pode ficar em branco"));
    }

    @Test
    public void testUpdateUserFieldsRequired(){
        CreateUsersPojo user = new UserDataFactory().userAdm();
        given()
                .body(user)
                .when()
                .put(baseUrlUser.concat(id))
                .then()
                .statusCode(400)
                .body("nome", equalTo("nome é obrigatório"))
                .body("email", equalTo("email é obrigatório"))
                .body("password", equalTo("password é obrigatório"))
                .body("administrador", equalTo("administrador é obrigatório"));
    }
}
