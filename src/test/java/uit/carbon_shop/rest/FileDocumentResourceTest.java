package uit.carbon_shop.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uit.carbon_shop.config.BaseIT;


public class FileDocumentResourceTest extends BaseIT {

    @Test
    @Sql("/data/fileDocumentData.sql")
    void getAllFileDocuments_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, sellerOrBuyerUserToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/fileDocuments")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.equalTo(2))
                    .body("get(0).id", Matchers.equalTo(1400));
    }

    @Test
    @Sql("/data/fileDocumentData.sql")
    void getFileDocument_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, sellerOrBuyerUserToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/fileDocuments/1400")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("name", Matchers.equalTo("Sed diam voluptua."));
    }

    @Test
    void getFileDocument_notFound() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, sellerOrBuyerUserToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/fileDocuments/2066")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createFileDocument_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, sellerOrBuyerUserToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/fileDocumentDTORequest.json"))
                .when()
                    .post("/api/fileDocuments")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, fileDocumentRepository.count());
    }

    @Test
    @Sql("/data/fileDocumentData.sql")
    void updateFileDocument_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, sellerOrBuyerUserToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/fileDocumentDTORequest.json"))
                .when()
                    .put("/api/fileDocuments/1400")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals("Duis autem vel.", fileDocumentRepository.findById(((long)1400)).orElseThrow().getName());
        assertEquals(2, fileDocumentRepository.count());
    }

    @Test
    @Sql("/data/fileDocumentData.sql")
    void deleteFileDocument_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, sellerOrBuyerUserToken())
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/fileDocuments/1400")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, fileDocumentRepository.count());
    }

}
