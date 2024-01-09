package BestBuyApp.StudentAPP;

import example.model.StudentPojo;
import example.utils.TestUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Student1Pojo {
    RequestSpecification requestSpecification;
    Response response;
    ValidatableResponse validatableResponse;
    static String firstName = "Preksha" + TestUtils.getRandomValue();
    static String lastName = "Patel" + TestUtils.getRandomValue();
    static String email = TestUtils.getRandomValue() + "test@gmail.com";
    static String programme = "testing";
    int studentId;

    @Test(priority = 1)
    public void getAllStudentGET(){
        RestAssured.baseURI ="http://localhost:8080/student/list";
        requestSpecification = given();
        response =requestSpecification.get();
        System.out.println(response.prettyPrint());
        validatableResponse =response.then();
        validatableResponse.statusCode(200);

    }
    @Test(priority = 2)
    public void addnewStudentPOST(){
        List<String> courses = new ArrayList<>();
        courses.add("postman");
        courses.add("java");
        StudentPojo studentPojo = new StudentPojo();
        studentPojo.setFirstName(firstName);
        studentPojo.setLastName(lastName);
        studentPojo.setEmail(email);
        studentPojo.setProgramme(programme);
        studentPojo.setcourses(courses);
        response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(studentPojo)
                .post("http://localhost:8080/student");
        response.then().log().all().statusCode(201)
                .body("msg", equalTo("Student added"));

    }
    @Test(priority = 3)
    public void getStudentIDafterPostGET(){
        HashMap<String, Object> studentDetails = given().log().all()
                .when()
                .get("http://localhost:8080/student/list") // refer to endpoints class in path package
                .then()
                .extract()
                .path("findAll{it.firstName=='" + firstName + "'}.get(0)");
        System.out.println(studentDetails);
        studentId = (int) studentDetails.get("id");
    }
    @Test(priority = 3)
    public void getStudentInfoByIDGET(){
        given()
                .pathParam("id", studentId)
                .get("http://localhost:8080/student/{id}")
                .then().statusCode(200)
                .body("firstName", equalTo(firstName))
                .body("lastName", equalTo(lastName)).log().all();

    }
    @Test(priority = 4)
    public void deleteStudentByIdDELETE(){
        given()
                .pathParam("id", studentId)
                .when()
                .delete("http://localhost:8080/student/{id}")
                .then()
                .statusCode(204);
    }
    @Test(priority = 5)
    public void verifyStudendDeletedGET(){
        given()
                .pathParam("id", studentId)
                .when()
                .get("http://localhost:8080/student/{id}")
                .then().statusCode(404);

    }

}
