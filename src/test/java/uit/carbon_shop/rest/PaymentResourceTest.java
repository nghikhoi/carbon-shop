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


public class PaymentResourceTest extends BaseIT {

    @Test
    @Sql("/data/paymentData.sql")
    void getAllPayments_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/payments")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.equalTo(2))
                    .body("get(0).paymentId", Matchers.equalTo("a9dd4a99-fba6-375a-9494-772b58f95280"));
    }

    @Test
    @Sql("/data/paymentData.sql")
    void getPayment_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/payments/a9dd4a99-fba6-375a-9494-772b58f95280")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("datePayment", Matchers.equalTo("At vero eos."));
    }

    @Test
    void getPayment_notFound() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/payments/234920ea-2540-3ec7-bbee-9efce43ea25e")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createPayment_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/paymentDTORequest.json"))
                .when()
                    .post("/api/payments")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, paymentRepository.count());
    }

    @Test
    @Sql("/data/paymentData.sql")
    void updatePayment_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/paymentDTORequest.json"))
                .when()
                    .put("/api/payments/a9dd4a99-fba6-375a-9494-772b58f95280")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals("Ut wisi enim.", paymentRepository.findById(UUID.fromString("a9dd4a99-fba6-375a-9494-772b58f95280")).orElseThrow().getDatePayment());
        assertEquals(2, paymentRepository.count());
    }

    @Test
    @Sql("/data/paymentData.sql")
    void deletePayment_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/payments/a9dd4a99-fba6-375a-9494-772b58f95280")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, paymentRepository.count());
    }

}
