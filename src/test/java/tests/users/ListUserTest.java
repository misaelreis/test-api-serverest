package tests.users;

import io.restassured.http.ContentType;
import org.junit.Test;
import utils.UserUtil;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static utils.BaseUrlUtil.baseUrlUser;

public class ListUserTest {

    public String id;

    @Test
    public void testListUsersAllParams(){
        id = new UserUtil().createUserId();
        given()
                .contentType(ContentType.JSON)
                .queryParam("_id", id)
                .queryParam("password", "teste")
                .queryParam("administrador", "true")
                .when()
                .get(baseUrlUser)
                .then()
                .statusCode(200)
                .body("usuarios.password[0]", equalTo("teste"))
                .body("usuarios.administrador[0]", equalTo("true"))
                .body("usuarios._id[0]", equalTo(id));
    }

    @Test
    public void testListUserWithoutParams(){
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(baseUrlUser)
                .then()
                .statusCode(200)
                .body("usuarios.nome[0]", notNullValue())
                .body("usuarios.nome[1]", notNullValue());
    }

    @Test
    public void testListUserTypeParamsIncorrect(){
        given()
                .contentType(ContentType.JSON)
                .queryParam("_id", 1.0f)
                .queryParam("nome", 1)
                .queryParam("email", 1)
                .queryParam("password", 1)
                .queryParam("administrador", 1)
                .when()
                .get(baseUrlUser)
                .then()
                .statusCode(400)
                .body("email", equalTo("email deve ser uma string"))
                .body("administrador", equalTo("administrador deve ser 'true' ou 'false'"));
    }

    @Test
    public void testListUserParamsIncorrect(){
        given()
                .contentType(ContentType.JSON)
                .queryParam("id", 1.0f)
                .when()
                .get(baseUrlUser)
                .then()
                .statusCode(400)
                .body("id", equalTo("id não é permitido"));
    }

    @Test
    public void testListUserForId(){
        id = new UserUtil().createUserId();
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(baseUrlUser.concat(id))
                .then()
                .statusCode(200)
                .body("_id", equalTo(id));
    }

    @Test
    public void tstListUserIdIncorrect(){
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(baseUrlUser.concat("id"))
                .then()
                .statusCode(400)
                .body("message", equalTo("Usuário não encontrado"));
    }
}
