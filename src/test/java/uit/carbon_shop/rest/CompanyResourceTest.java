package uit.carbon_shop.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.UUID;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uit.carbon_shop.config.BaseIT;


public class CompanyResourceTest extends BaseIT {

    @Test
    @Sql("/data/companyData.sql")
    void getAllCompanies_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/companies")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(2))
                    .body("content.get(0).id", Matchers.equalTo("a92d0103-08a6-3379-9a3d-9c728ee74244"));
    }

    @Test
    @Sql("/data/companyData.sql")
    void getAllCompanies_filtered() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/companies?filter=b801e5d4-da87-3c39-9782-741cd794002d")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(1))
                    .body("content.get(0).id", Matchers.equalTo("b801e5d4-da87-3c39-9782-741cd794002d"));
    }

    @Test
    @Sql("/data/companyData.sql")
    void getCompany_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/companies/a92d0103-08a6-3379-9a3d-9c728ee74244")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("name", Matchers.equalTo("Sed diam voluptua."));
    }

    @Test
    void getCompany_notFound() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/companies/23de10ad-baa1-32ee-93f7-7f679fa1483a")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createCompany_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/companyDTORequest.json"))
                .when()
                    .post("/api/companies")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, companyRepository.count());
    }

    @Test
    @Sql("/data/companyData.sql")
    void updateCompany_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/companyDTORequest.json"))
                .when()
                    .put("/api/companies/a92d0103-08a6-3379-9a3d-9c728ee74244")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals("Duis autem vel.", companyRepository.findById(UUID.fromString("a92d0103-08a6-3379-9a3d-9c728ee74244")).orElseThrow().getName());
        assertEquals(2, companyRepository.count());
    }

    @Test
    @Sql("/data/companyData.sql")
    void deleteCompany_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/companies/a92d0103-08a6-3379-9a3d-9c728ee74244")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, companyRepository.count());
    }

}
