package uit.carbon_shop.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.UUID;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import uit.carbon_shop.config.BaseIT;


public class UserResourceTest extends BaseIT {

    @Test
    void getAllUsers_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, buyerUserToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/users")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(2))
                    .body("content.get(0).userId", Matchers.equalTo("a9dd4a99-fba6-375a-9494-772b58f95280"));
    }

    @Test
    void getAllUsers_filtered() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, buyerUserToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/users?filter=b801a1c1-65dd-3420-816b-fec5edd6c2b1")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(1))
                    .body("content.get(0).userId", Matchers.equalTo("b801a1c1-65dd-3420-816b-fec5edd6c2b1"));
    }

    @Test
    void getAllUsers_unauthorized() {
        RestAssured
                .given()
                    .redirects().follow(false)
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/users")
                .then()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .body("code", Matchers.equalTo("AUTHORIZATION_DENIED"));
    }

    @Test
    void getUser_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, buyerUserToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/users/a9dd4a99-fba6-375a-9494-772b58f95280")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("passwordSalt", Matchers.equalTo("Quis nostrud exerci."));
    }

    @Test
    void getUser_notFound() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, buyerUserToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/users/234920ea-2540-3ec7-bbee-9efce43ea25e")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createUser_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, buyerUserToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/userDTORequest.json"))
                .when()
                    .post("/api/users")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(3, userRepository.count());
    }

    @Test
    void updateUser_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, buyerUserToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/userDTORequest.json"))
                .when()
                    .put("/api/users/a9dd4a99-fba6-375a-9494-772b58f95280")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals("At vero eos.", userRepository.findById(UUID.fromString("a9dd4a99-fba6-375a-9494-772b58f95280")).orElseThrow().getPasswordSalt());
        assertEquals(2, userRepository.count());
    }

}
