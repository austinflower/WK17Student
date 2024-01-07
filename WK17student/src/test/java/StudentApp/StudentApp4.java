package StudentApp;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class StudentApp4 {
    RequestSpecification requestSpecification;
    Response response;
    ValidatableResponse validatableResponse;
    @Test
    public void agetAllStudents(){

        response = given().log().all()
                .when()
                .get("http://localhost:8080/student/");
        response.then().log().all().statusCode(200);
    }
    @Test
    public void bpostNewStudent(){
        String jsonData = "  {\n" +
                "        \"firstName\": \"Pooja\",\n" +
                "        \"lastName\": \"Bhatt\",\n" +
                "        \"email\": \"poojabhatt@aol.com\",\n" +
                "        \"programme\": \"Financial Analysis\",\n" +
                "        \"courses\": [\n" +
                "            \"Accounting\",\n" +
                "            \"Statistics\"\n" +
                "        ]\n" +
                "    }";
        response = given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(jsonData)
                .post("http://localhost:8080/student");
        response.then().log().all().statusCode(201)
                .body("msg",equalTo("Student added"));
    }
    @Test
    public void cgetStudentById(){
        response = given()
                .log().all()
                .when()
                .get("http://localhost:8080/student/104");
        response.then().statusCode(200)
                .body("firstName",equalTo("Pooja"))
                .body("lastName",equalTo("Bhatt"));

    }
    @Test
    public void dupdateStudentPut(){
        String jsonData = "  {\n" +
                "        \"firstName\": \"Pooja Updated\",\n" +
                "        \"lastName\": \"Bhatt Updated\",\n" +
                "        \"email\": \"poojabhatt@aol.com\",\n" +
                "        \"programme\": \"Financial Analysis\",\n" +
                "        \"courses\": [\n" +
                "            \"Accounting\",\n" +
                "            \"Calculus & Linear Algebra I\"\n" +
                "        ]\n" +
                "    }";
        response = given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(jsonData)
                .put("http://localhost:8080/student/104");
        response.then().log().all().statusCode(200)
                .body("msg",equalTo("Student Updated"));
    }
    @Test
    public void epatchStudentInfo(){
        String jsonData ="{\n" +
                "    \"firstName\": \"Pooja patch\",\n" +
                "    \"email\": \"poojabhatt@aol.com\"\n" +
                "}";
        response = given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(jsonData)
                .patch("http://localhost:8080/student/104");
        response.then().log().all().statusCode(200)
                .body("msg",equalTo("Updated"));

    }
    @Test
    public void fdeleteStudentInfo104(){
        response = given()
                .log().all()
                .when()
                .delete("http://localhost:8080/student/104");
        response.then().statusCode(204);
    }
    @Test
    public void gverifyStudentDeleted(){
        response = given()
                .log().all()
                .when()
                .get("http://localhost:8080/student/104");
        response.then().statusCode(404);
    }

}
