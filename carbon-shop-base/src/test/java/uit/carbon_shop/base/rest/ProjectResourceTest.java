package uit.carbon_shop.base.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.UUID;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uit.carbon_shop.base.config.BaseIT;


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
                    .body("content.get(0).projectId", Matchers.equalTo("a96e0a04-d20f-3096-bc64-dac2d639a577"));
    }

    @Test
    @Sql("/data/projectData.sql")
    void getAllProjects_filtered() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/projects?filter=b8bff625-bdb0-3939-92c9-d4db0c6bbe45")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(1))
                    .body("content.get(0).projectId", Matchers.equalTo("b8bff625-bdb0-3939-92c9-d4db0c6bbe45"));
    }

    @Test
    @Sql("/data/projectData.sql")
    void getProject_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/projects/a96e0a04-d20f-3096-bc64-dac2d639a577")
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
                    .get("/api/projects/23a93ba8-9a5b-3c6c-a26e-49b88973f46e")
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
                    .put("/api/projects/a96e0a04-d20f-3096-bc64-dac2d639a577")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals("Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat.", projectRepository.findById(UUID.fromString("a96e0a04-d20f-3096-bc64-dac2d639a577")).orElseThrow().getName());
        assertEquals(2, projectRepository.count());
    }

    @Test
    @Sql("/data/projectData.sql")
    void deleteProject_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/projects/a96e0a04-d20f-3096-bc64-dac2d639a577")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, projectRepository.count());
    }

}
