package uit.carbon_shop.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import uit.carbon_shop.config.BaseIT;


public class UserRegistrationResourceTest extends BaseIT {

    @Test
    void register_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/userregistrationRequest.json"))
                .when()
                    .post("/user/register")
                .then()
                    .statusCode(HttpStatus.OK.value());
            assertEquals(3, userRepository.count());
        }

    }
