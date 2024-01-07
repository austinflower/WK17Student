package StudentApp;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class StudentApp5 {
    RequestSpecification requestSpecification;
    Response response;
    ValidatableResponse validatableResponse;

    @Test
    public void getAllStudentsInfo() {

        response = given().log().all()
                .when()
                .get("http://localhost:8080/student/list");
        response.then().log().all().statusCode(200);
    }
    @Test
    public void postStudentsInfo() {

        String data = "{\n" +
                "    \"firstName\": \"Dipti\",\n" +
                "    \"lastName\": \"Tank\",\n" +
                "    \"email\": \"dipti@gmail.com\",\n" +
                "    \"programme\": \"Automation\",\n" +
                "    \"courses\": [\n" +
                "        \"Selenium\",\n" +
                "        \"API\"\n" +
                "    ]\n" +
                "}";
        response = given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(data)
                .post("http://localhost:8080/student");
        response.then().log().all().statusCode(201)
                .body("msg", equalTo("Student added"));
    }
    @Test
    public void getSingleStudentInfo() {
        response=given()
                .log().all()
                .pathParam("id","105")
                .when()
                .get("/student/{id}");
        response.then().statusCode(200);
        response.prettyPrint();
    }
    @Test
    public void putStudentsInfo() {

        String data = "{\n" +
                "    \"firstName\": \"Dipti Update\",\n" +
                "    \"lastName\": \"Tank Update\",\n" +
                "    \"email\": \"dipti@gmail.com\",\n" +
                "    \"programme\": \"Automation\",\n" +
                "    \"courses\": [\n" +
                "        \"Selenium\",\n" +
                "        \"java\"\n" +
                "    ]\n" +
                "}";
        response = given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(data)
                .put("http://localhost:8080/student/105");
        response.then().log().all().statusCode(200)
                .body("msg", equalTo("Student Updated"));
    }
    @Test
    public void epatchStudentInfo() {
        String jsonData ="{\n" +
                "    \"firstName\": \"Dipti patch\",\n" +
                "    \"email\": \"dipti@gmail.com\"\n" +
                "}";
        response = given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(jsonData)
                .patch("http://localhost:8080/student/105");
        response.then().log().all().statusCode(200)
                .body("msg", equalTo("Updated"));
    }
    @Test
    public void verifyNewStudentInfoDeletedByID() {
        given()
                .pathParam("id", 105)
                .when()
                .delete("http://localhost:8080/student/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void verifyStudentInfoDeleted() {
        given()
                .pathParam("id", 105)
                .when()
                .get("http://localhost:8080/student/{id}")
                .then().statusCode(404);
    }


}
