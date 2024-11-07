package uit.carbon_shop.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uit.carbon_shop.config.BaseIT;


public class OrderStatusResourceTest extends BaseIT {

    @Test
    @Sql("/data/orderStatusData.sql")
    void getAllOrderStatuses_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/orderStatuses")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.equalTo(2))
                    .body("get(0).orderStatusId", Matchers.equalTo(1300));
    }

    @Test
    @Sql("/data/orderStatusData.sql")
    void getOrderStatus_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/orderStatuses/1300")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("buyerId", Matchers.equalTo("Viverra suspendisse."));
    }

    @Test
    void getOrderStatus_notFound() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/orderStatuses/1966")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createOrderStatus_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/orderStatusDTORequest.json"))
                .when()
                    .post("/api/orderStatuses")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, orderStatusRepository.count());
    }

    @Test
    @Sql("/data/orderStatusData.sql")
    void updateOrderStatus_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/orderStatusDTORequest.json"))
                .when()
                    .put("/api/orderStatuses/1300")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals("Quis nostrud exerci.", orderStatusRepository.findById(((long)1300)).orElseThrow().getBuyerId());
        assertEquals(2, orderStatusRepository.count());
    }

    @Test
    @Sql("/data/orderStatusData.sql")
    void deleteOrderStatus_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/orderStatuses/1300")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, orderStatusRepository.count());
    }

}
