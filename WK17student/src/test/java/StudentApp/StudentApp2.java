package StudentApp;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class StudentApp2 {
    RequestSpecification requestSpecification;
    Response response;
    ValidatableResponse validatableResponse;
    @Test
    public void agetallstudents(){
        response = given().log().all()
                .when()
                .get("http://localhost:8080/student/list");
        response.then().log().all().statusCode(200);
    }
    @Test
    public void bpostStudentInfo(){
        String jsonData =" {\n" +
                "        \"firstName\": \"Raj\",\n" +
                "        \"lastName\": \"saxena\",\n" +
                "        \"email\": \"rajsaxena@aol.com\",\n" +
                "        \"programme\": \"Financial Analysis\",\n" +
                "        \"courses\": [\n" +
                "            \"Business\",\n" +
                "            \"Calculus\"\n" +
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
    public void cstudentInfoById(){
        response =given()
                .log().all()
                .when()
                .get("http://localhost:8080/student/102");
        response.then().statusCode(200)
                .body("firstName",equalTo("Raj"))
                .body("lastName",equalTo("saxena"));
    }
    @Test
    public void dputStudentUpdate(){
        String jsonData = "  {\n" +
                "        \"firstName\": \"Raj Updated\",\n" +
                "        \"lastName\": \"saxena Updated\",\n" +
                "        \"email\": \"rajsaxena@aol.com\",\n" +
                "        \"programme\": \"Financial Analysis\",\n" +
                "        \"courses\": [\n" +
                "            \"Business\",\n" +
                "            \"Statistics\"\n" +
                "        ]\n" +
                "    }";
        response = given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(jsonData)
                .put("http://localhost:8080/student/102");
        response.then().log().all().statusCode(200)
                .body("msg",equalTo("Student Updated"));
    }  @Test
    public void epatchStudentInfo(){
        String jsonData ="{\n" +
                "    \"firstName\": \"Raj patch\",\n" +
                "    \"email\": \"rajsaxena@aol.com\"\n" +
                "}";
        response = given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(jsonData)
                .patch("http://localhost:8080/student/102");
        response.then().log().all().statusCode(200)
                .body("msg",equalTo("Updated"));
    }
    @Test
    public void fdeleteStudentInfo102(){
        response = given()
                .log().all()
                .when()
                .delete("http://localhost:8080/student/102");
        response.then().statusCode(204);
    }
    @Test
    public void gverifyStudentDeleted(){
        response = given()
                .log().all()
                .when()
                .get("http://localhost:8080/student/102");
        response.then().statusCode(404);
    }

    }
