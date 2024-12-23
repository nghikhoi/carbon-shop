package uit.carbon_shop.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import uit.carbon_shop.config.BaseIT;


public class SellerControllerTest extends BaseIT {

    @Test
    void registerProject_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, sellerOrBuyerUserToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/sellerRegisterProjectDTORequest.json"))
                .when()
                    .post("/api/seller/project")
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    void viewProject_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, sellerOrBuyerUserToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/seller/project/test-projectId")
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    void viewProject_unauthorized() {
        RestAssured
                .given()
                    .redirects().follow(false)
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/seller/project/test-projectId")
                .then()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .body("code", Matchers.equalTo("AUTHORIZATION_DENIED"));
    }

    @Test
    void viewAllProject_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, sellerOrBuyerUserToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/seller/projects")
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
                    .get("/api/seller/order/test-orderId")
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
                    .get("/api/seller/orders")
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
                    .get("/api/seller/company/test-companyId")
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
                    .body(readResource("/requests/sellerReviewCompanyRequest.json"))
                .when()
                    .post("/api/seller/company/test-companyId/review")
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

}
