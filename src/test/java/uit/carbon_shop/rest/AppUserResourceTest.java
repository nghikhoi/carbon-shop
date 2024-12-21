package uit.carbon_shop.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.UUID;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import uit.carbon_shop.config.BaseIT;


public class AppUserResourceTest extends BaseIT {

    @Test
    void getAllAppUsers_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/appUsers")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(2))
                    .body("content.get(0).userId", Matchers.equalTo("a93e29a3-5278-371c-bf65-495871231324"));
    }

    @Test
    void getAllAppUsers_filtered() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/appUsers?filter=b8f45244-f093-39e1-aea3-f9117ca45157")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(1))
                    .body("content.get(0).userId", Matchers.equalTo("b8f45244-f093-39e1-aea3-f9117ca45157"));
    }

    @Test
    void getAppUser_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/appUsers/a93e29a3-5278-371c-bf65-495871231324")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("name", Matchers.equalTo("Sed diam voluptua."));
    }

    @Test
    void getAppUser_notFound() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/appUsers/2383af9d-6f6c-36ac-ae72-992f2977f67e")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createAppUser_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/appUserDTORequest.json"))
                .when()
                    .post("/api/appUsers")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(3, appUserRepository.count());
    }

    @Test
    void createAppUser_missingField() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/appUserDTORequest_missingField.json"))
                .when()
                    .post("/api/appUsers")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("password"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    void updateAppUser_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/appUserDTORequest.json"))
                .when()
                    .put("/api/appUsers/a93e29a3-5278-371c-bf65-495871231324")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals("Duis autem vel.", appUserRepository.findById(UUID.fromString("a93e29a3-5278-371c-bf65-495871231324")).orElseThrow().getName());
        assertEquals(2, appUserRepository.count());
    }

    @Test
    void deleteAppUser_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/appUsers/a93e29a3-5278-371c-bf65-495871231324")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, appUserRepository.count());
    }

}
