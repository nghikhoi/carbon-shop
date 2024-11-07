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


public class UserResourceTest extends BaseIT {

    @Test
    @Sql("/data/userData.sql")
    void getAllUsers_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/users")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.equalTo(2))
                    .body("get(0).userId", Matchers.equalTo("a9b7ba70-783b-317e-9998-dc4dd82eb3c5"));
    }

    @Test
    @Sql("/data/userData.sql")
    void getUser_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/users/a9b7ba70-783b-317e-9998-dc4dd82eb3c5")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("companyName", Matchers.equalTo("Nam liber tempor."));
    }

    @Test
    void getUser_notFound() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/users/23d7c8a0-8b4a-3a1b-87c5-99473f5dddda")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createUser_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/userDTORequest.json"))
                .when()
                    .post("/api/users")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, userRepository.count());
    }

    @Test
    @Sql("/data/userData.sql")
    void updateUser_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/userDTORequest.json"))
                .when()
                    .put("/api/users/a9b7ba70-783b-317e-9998-dc4dd82eb3c5")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals("Nec ullamcorper.", userRepository.findById(UUID.fromString("a9b7ba70-783b-317e-9998-dc4dd82eb3c5")).orElseThrow().getCompanyName());
        assertEquals(2, userRepository.count());
    }

    @Test
    @Sql("/data/userData.sql")
    void deleteUser_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/users/a9b7ba70-783b-317e-9998-dc4dd82eb3c5")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, userRepository.count());
    }

}
