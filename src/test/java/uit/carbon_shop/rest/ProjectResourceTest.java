package uit.carbon_shop.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uit.carbon_shop.config.BaseIT;


public class ProjectResourceTest extends BaseIT {

    @Test
    @Sql("/data/projectData.sql")
    void getAllProjects_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/projects")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(2))
                    .body("content.get(0).projectId", Matchers.equalTo(1000));
    }

    @Test
    @Sql("/data/projectData.sql")
    void getAllProjects_filtered() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/projects?filter=1001")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(1))
                    .body("content.get(0).projectId", Matchers.equalTo(1001));
    }

    @Test
    @Sql("/data/projectData.sql")
    void getProject_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/projects/1000")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("name", Matchers.equalTo("Ullamcorper eget nulla facilisi etiam dignissim diam."));
    }

    @Test
    void getProject_notFound() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/projects/1666")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createProject_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/projectDTORequest.json"))
                .when()
                    .post("/api/projects")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, projectRepository.count());
    }

    @Test
    @Sql("/data/projectData.sql")
    void updateProject_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/projectDTORequest.json"))
                .when()
                    .put("/api/projects/1000")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals("Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat.", projectRepository.findById(((long)1000)).orElseThrow().getName());
        assertEquals(2, projectRepository.count());
    }

    @Test
    @Sql("/data/projectData.sql")
    void deleteProject_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/projects/1000")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, projectRepository.count());
    }

}
