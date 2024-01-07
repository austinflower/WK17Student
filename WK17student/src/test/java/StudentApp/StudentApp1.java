package StudentApp;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class StudentApp1 {
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
                "        \"firstName\": \"Preksha\",\n" +
                "        \"lastName\": \"Patel\",\n" +
                "        \"email\": \"prekshapatel@aol.com\",\n" +
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
                .get("http://localhost:8080/student/101");
        response.then().statusCode(200)
                .body("firstName",equalTo("Preksha"))
                .body("lastName",equalTo("Patel"));

    }
    @Test
    public void dupdateStudentPut(){
        String jsonData = "  {\n" +
                "        \"firstName\": \"Preksha Updated\",\n" +
                "        \"lastName\": \"Patel Updated\",\n" +
                "        \"email\": \"prekshapatel@aol.com\",\n" +
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
                .put("http://localhost:8080/student/101");
        response.then().log().all().statusCode(200)
                .body("msg",equalTo("Student Updated"));
    }
    @Test
    public void epatchStudentInfo(){
        String jsonData ="{\n" +
                "    \"firstName\": \"Preksha patch\",\n" +
                "    \"email\": \"prekshapatel@aol.com\"\n" +
                "}";
        response = given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(jsonData)
                .patch("http://localhost:8080/student/101");
        response.then().log().all().statusCode(200)
                .body("msg",equalTo("Updated"));

    }
    @Test
    public void fdeleteStudentInfo101(){
        response = given()
                .log().all()
                .when()
                .delete("http://localhost:8080/student/101");
        response.then().statusCode(204);
    }
    @Test
    public void gverifyStudentDeleted(){
        response = given()
                .log().all()
                .when()
                .get("http://localhost:8080/student/101");
        response.then().statusCode(404);
    }

}
