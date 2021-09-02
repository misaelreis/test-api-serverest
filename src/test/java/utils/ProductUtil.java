package utils;

import factories.ProductDataFactory;
import io.restassured.http.ContentType;
import pojo.ProductPojo;

import static io.restassured.RestAssured.given;
import static utils.BaseUrlUtil.baseUrlProduct;

public class ProductUtil {
    public String tokenAdm;

    public ProductUtil createProduct(){
        tokenAdm = new LoginUtil().loginAdmFix();
        ProductPojo product = new ProductDataFactory().product();
        product.setNome("Nome cadastrado");
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", tokenAdm)
                .body(product)
                .when()
                .post(baseUrlProduct)
                .then();
        return null;
    }
}
