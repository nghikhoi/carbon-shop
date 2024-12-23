package uit.carbon_shop.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import uit.carbon_shop.config.BaseIT;


public class BuyerControllerTest extends BaseIT {

    @Test
    void viewProject_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, sellerOrBuyerUserToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/buyer/project/test-projectId")
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    void reviewProject_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, sellerOrBuyerUserToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/buyerReviewProjectRequest.json"))
                .when()
                    .post("/api/buyer/project/test-projectId/review")
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    void viewAllProject_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, sellerOrBuyerUserToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/buyer/projects")
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    void viewOrder_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, sellerOrBuyerUserToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/buyer/order/test-orderId")
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    void newOrder_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, sellerOrBuyerUserToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/buyerCreateOrderRequest.json"))
                .when()
                    .post("/api/buyer/order")
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    void viewAllOrders_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, sellerOrBuyerUserToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/buyer/orders")
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    void viewCompany_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, sellerOrBuyerUserToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/buyer/company/test-companyId")
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    void reviewCompany_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, sellerOrBuyerUserToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/buyerReviewCompanyRequest.json"))
                .when()
                    .post("/api/buyer/company/test-companyId/review")
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

}
