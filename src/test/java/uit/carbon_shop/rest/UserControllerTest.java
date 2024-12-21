package uit.carbon_shop.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import uit.carbon_shop.config.BaseIT;


public class UserControllerTest extends BaseIT {

    @Test
    void newQuestion_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, buyerUserToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/userAskDTORequest.json"))
                .when()
                    .post("/api/user/question")
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    void viewQuestions_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, buyerUserToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/user/questions")
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    void viewAllProject_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, buyerUserToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/user/projects")
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

}
