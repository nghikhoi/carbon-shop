package uit.carbon_shop.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uit.carbon_shop.config.BaseIT;


public class StaffResourceTest extends BaseIT {

    @Test
    @Sql("/data/staffData.sql")
    void getAllStaffs_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/staffs")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.equalTo(2))
                    .body("get(0).staffId", Matchers.equalTo(1800));
    }

    @Test
    @Sql("/data/staffData.sql")
    void getStaff_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/staffs/1800")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("email", Matchers.equalTo("Sed ut perspiciatis."));
    }

    @Test
    void getStaff_notFound() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/staffs/2466")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createStaff_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/staffDTORequest.json"))
                .when()
                    .post("/api/staffs")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, staffRepository.count());
    }

    @Test
    @Sql("/data/staffData.sql")
    void updateStaff_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/staffDTORequest.json"))
                .when()
                    .put("/api/staffs/1800")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals("Nulla facilisis.", staffRepository.findById(((long)1800)).orElseThrow().getEmail());
        assertEquals(2, staffRepository.count());
    }

    @Test
    @Sql("/data/staffData.sql")
    void deleteStaff_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/staffs/1800")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, staffRepository.count());
    }

}
