package uit.carbon_shop.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uit.carbon_shop.config.BaseIT;


public class ReviewCompanyResourceTest extends BaseIT {

    @Test
    @Sql("/data/reviewCompanyData.sql")
    void getAllReviewCompanies_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/reviewCompanies")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.equalTo(2))
                    .body("get(0).reviewCompanyId", Matchers.equalTo(1600));
    }

    @Test
    @Sql("/data/reviewCompanyData.sql")
    void getReviewCompany_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/reviewCompanies/1600")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("text", Matchers.equalTo("Quis nostrud exerci."));
    }

    @Test
    void getReviewCompany_notFound() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/reviewCompanies/2266")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createReviewCompany_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/reviewCompanyDTORequest.json"))
                .when()
                    .post("/api/reviewCompanies")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, reviewCompanyRepository.count());
    }

    @Test
    @Sql("/data/reviewCompanyData.sql")
    void updateReviewCompany_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/reviewCompanyDTORequest.json"))
                .when()
                    .put("/api/reviewCompanies/1600")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals("At vero eos.", reviewCompanyRepository.findById(((long)1600)).orElseThrow().getText());
        assertEquals(2, reviewCompanyRepository.count());
    }

    @Test
    @Sql("/data/reviewCompanyData.sql")
    void deleteReviewCompany_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/reviewCompanies/1600")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, reviewCompanyRepository.count());
    }

}
