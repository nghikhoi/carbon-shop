package uit.carbon_shop.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
                    .body("content.get(0).id", Matchers.equalTo(1200));
    }

    @Test
    @Sql("/data/companyData.sql")
    void getAllCompanies_filtered() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/companies?filter=1201")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(1))
                    .body("content.get(0).id", Matchers.equalTo(1201));
    }

    @Test
    @Sql("/data/companyData.sql")
    void getCompany_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/companies/1200")
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
                    .get("/api/companies/1866")
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
                    .put("/api/companies/1200")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals("Duis autem vel.", companyRepository.findById(((long)1200)).orElseThrow().getName());
        assertEquals(2, companyRepository.count());
    }

    @Test
    @Sql("/data/companyData.sql")
    void deleteCompany_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/companies/1200")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, companyRepository.count());
    }

}
