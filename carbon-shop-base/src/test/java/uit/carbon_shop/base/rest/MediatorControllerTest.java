package uit.carbon_shop.base.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import uit.carbon_shop.base.config.BaseIT;


public class MediatorControllerTest extends BaseIT {

    @Test
    void updateOrderStatus_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body("\"INIT\"")
                .when()
                    .patch("/mediator/order/test-orderId")
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

}
