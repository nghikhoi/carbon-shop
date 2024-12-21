package uit.carbon_shop.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import uit.carbon_shop.config.BaseIT;


public class MediatorAuditControllerTest extends BaseIT {

    @Test
    void startProcessOrder_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, mediatorMediatorToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/mediatorProcessOrderDTORequest.json"))
                .when()
                    .patch("/api/mediator/audit/order/test-orderId/process")
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    void cancelProcessOrder_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, mediatorMediatorToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/mediatorCancelOrderDTORequest.json"))
                .when()
                    .patch("/api/mediator/audit/order/test-orderId/cancel")
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    void doneProcessOrder_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, mediatorMediatorToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/mediatorDoneOrderDTORequest.json"))
                .when()
                    .patch("/api/mediator/audit/order/test-orderId/done")
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    void approveUserRegistration_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, mediatorMediatorToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/mediatorApproveUserDTORequest.json"))
                .when()
                    .patch("/api/mediator/audit/user/test-userId/approve")
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    void rejectUserRegistration_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, mediatorMediatorToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/mediatorRejectUserDTORequest.json"))
                .when()
                    .patch("/api/mediator/audit/user/test-userId/reject")
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    void approveProject_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, mediatorMediatorToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/mediatorApproveProjectDTORequest.json"))
                .when()
                    .patch("/api/mediator/audit/project/test-projectId/approve")
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    void rejectProject_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, mediatorMediatorToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/mediatorRejectProjectDTORequest.json"))
                .when()
                    .patch("/api/mediator/audit/project/test-projectId/reject")
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    void answerQuestion_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, mediatorMediatorToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/mediatorAnswerDTORequest.json"))
                .when()
                    .patch("/api/mediator/audit/question/test-questionId")
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deleteQuestionAnswer_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, mediatorMediatorToken())
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/mediator/audit/question/test-questionId/answer")
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

}
