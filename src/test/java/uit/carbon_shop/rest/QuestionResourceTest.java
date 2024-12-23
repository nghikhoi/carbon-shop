package uit.carbon_shop.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uit.carbon_shop.config.BaseIT;


public class QuestionResourceTest extends BaseIT {

    @Test
    @Sql("/data/questionData.sql")
    void getAllQuestions_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/questions")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(2))
                    .body("content.get(0).id", Matchers.equalTo(1700));
    }

    @Test
    @Sql("/data/questionData.sql")
    void getAllQuestions_filtered() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/questions?filter=1701")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(1))
                    .body("content.get(0).id", Matchers.equalTo(1701));
    }

    @Test
    @Sql("/data/questionData.sql")
    void getQuestion_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/questions/1700")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("question", Matchers.equalTo("Sed diam nonumy."));
    }

    @Test
    void getQuestion_notFound() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/questions/2366")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createQuestion_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/questionDTORequest.json"))
                .when()
                    .post("/api/questions")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, questionRepository.count());
    }

    @Test
    @Sql("/data/questionData.sql")
    void updateQuestion_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/questionDTORequest.json"))
                .when()
                    .put("/api/questions/1700")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals("Lorem ipsum dolor.", questionRepository.findById(((long)1700)).orElseThrow().getQuestion());
        assertEquals(2, questionRepository.count());
    }

    @Test
    @Sql("/data/questionData.sql")
    void deleteQuestion_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/questions/1700")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, questionRepository.count());
    }

}
