package uit.carbon_shop.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uit.carbon_shop.config.BaseIT;


public class ReviewProjectResourceTest extends BaseIT {

    @Test
    @Sql("/data/reviewProjectData.sql")
    void getAllReviewProjects_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/reviewProjects")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.equalTo(2))
                    .body("get(0).reviewProjectId", Matchers.equalTo(1700));
    }

    @Test
    @Sql("/data/reviewProjectData.sql")
    void getReviewProject_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/reviewProjects/1700")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("text", Matchers.equalTo("Quis nostrud exerci."));
    }

    @Test
    void getReviewProject_notFound() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/reviewProjects/2366")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createReviewProject_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/reviewProjectDTORequest.json"))
                .when()
                    .post("/api/reviewProjects")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, reviewProjectRepository.count());
    }

    @Test
    @Sql("/data/reviewProjectData.sql")
    void updateReviewProject_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/reviewProjectDTORequest.json"))
                .when()
                    .put("/api/reviewProjects/1700")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals("At vero eos.", reviewProjectRepository.findById(((long)1700)).orElseThrow().getText());
        assertEquals(2, reviewProjectRepository.count());
    }

    @Test
    @Sql("/data/reviewProjectData.sql")
    void deleteReviewProject_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/reviewProjects/1700")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, reviewProjectRepository.count());
    }

}
