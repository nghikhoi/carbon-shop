package uit.carbon_shop.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uit.carbon_shop.config.BaseIT;


public class ContractResourceTest extends BaseIT {

    @Test
    @Sql("/data/contractData.sql")
    void getAllContracts_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/contracts")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.equalTo(2))
                    .body("get(0).constractId", Matchers.equalTo(1500));
    }

    @Test
    @Sql("/data/contractData.sql")
    void getContract_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/contracts/1500")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("constractFile", Matchers.equalTo("Vel illum dolore."));
    }

    @Test
    void getContract_notFound() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/contracts/2166")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createContract_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/contractDTORequest.json"))
                .when()
                    .post("/api/contracts")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, contractRepository.count());
    }

    @Test
    @Sql("/data/contractData.sql")
    void updateContract_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/contractDTORequest.json"))
                .when()
                    .put("/api/contracts/1500")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals("Sed diam nonumy.", contractRepository.findById(((long)1500)).orElseThrow().getConstractFile());
        assertEquals(2, contractRepository.count());
    }

    @Test
    @Sql("/data/contractData.sql")
    void deleteContract_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/contracts/1500")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, contractRepository.count());
    }

}
