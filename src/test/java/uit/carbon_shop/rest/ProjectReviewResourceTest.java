package uit.carbon_shop.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uit.carbon_shop.config.BaseIT;


public class ProjectReviewResourceTest extends BaseIT {

    @Test
    @Sql("/data/projectReviewData.sql")
    void getAllProjectReviews_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/projectReviews")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(2))
                    .body("content.get(0).id", Matchers.equalTo(1600));
    }

    @Test
    @Sql("/data/projectReviewData.sql")
    void getAllProjectReviews_filtered() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/projectReviews?filter=1601")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(1))
                    .body("content.get(0).id", Matchers.equalTo(1601));
    }

    @Test
    @Sql("/data/projectReviewData.sql")
    void getProjectReview_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/projectReviews/1600")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("message", Matchers.equalTo("Ut wisi enim."));
    }

    @Test
    void getProjectReview_notFound() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/projectReviews/2266")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createProjectReview_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/projectReviewDTORequest.json"))
                .when()
                    .post("/api/projectReviews")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, projectReviewRepository.count());
    }

    @Test
    @Sql("/data/projectReviewData.sql")
    void updateProjectReview_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/projectReviewDTORequest.json"))
                .when()
                    .put("/api/projectReviews/1600")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals("Viverra suspendisse.", projectReviewRepository.findById(((long)1600)).orElseThrow().getMessage());
        assertEquals(2, projectReviewRepository.count());
    }

    @Test
    @Sql("/data/projectReviewData.sql")
    void deleteProjectReview_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/projectReviews/1600")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, projectReviewRepository.count());
    }

}
