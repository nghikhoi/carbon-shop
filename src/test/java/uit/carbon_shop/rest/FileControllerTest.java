package uit.carbon_shop.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import uit.carbon_shop.config.BaseIT;


public class FileControllerTest extends BaseIT {

    @Test
    void upload_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, sellerOrBuyerUserToken())
                    .accept(ContentType.JSON)
                .when()
                    .post("/api/file/upload")
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    void get_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, sellerOrBuyerUserToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/file/test-fileId")
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

}
