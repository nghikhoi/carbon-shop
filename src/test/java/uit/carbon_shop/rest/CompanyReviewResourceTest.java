package uit.carbon_shop.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uit.carbon_shop.config.BaseIT;


public class CompanyReviewResourceTest extends BaseIT {

    @Test
    @Sql("/data/companyReviewData.sql")
    void getAllCompanyReviews_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/companyReviews")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(2))
                    .body("content.get(0).id", Matchers.equalTo(1500));
    }

    @Test
    @Sql("/data/companyReviewData.sql")
    void getAllCompanyReviews_filtered() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/companyReviews?filter=1501")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(1))
                    .body("content.get(0).id", Matchers.equalTo(1501));
    }

    @Test
    @Sql("/data/companyReviewData.sql")
    void getCompanyReview_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/companyReviews/1500")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("message", Matchers.equalTo("Ut wisi enim."));
    }

    @Test
    void getCompanyReview_notFound() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/companyReviews/2166")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createCompanyReview_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/companyReviewDTORequest.json"))
                .when()
                    .post("/api/companyReviews")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, companyReviewRepository.count());
    }

    @Test
    @Sql("/data/companyReviewData.sql")
    void updateCompanyReview_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/companyReviewDTORequest.json"))
                .when()
                    .put("/api/companyReviews/1500")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals("Viverra suspendisse.", companyReviewRepository.findById(((long)1500)).orElseThrow().getMessage());
        assertEquals(2, companyReviewRepository.count());
    }

    @Test
    @Sql("/data/companyReviewData.sql")
    void deleteCompanyReview_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/companyReviews/1500")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, companyReviewRepository.count());
    }

}
