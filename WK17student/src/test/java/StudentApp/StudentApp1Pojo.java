package StudentApp;

import example.model.StudentPojo;
import example.path.EndPoints;
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

public class StudentApp1Pojo {
    RequestSpecification requestSpecification;
    Response response;
    ValidatableResponse validatableResponse;
    int studentId;
    static String firstName = "Preksha" + TestUtils.getRandomValue();
    static String lastName = "Patel" + TestUtils.getRandomValue();
    static String email = TestUtils.getRandomValue() + "xyz@gmail.com";
    static String programme = "rocking";

    @Test(priority = 1)
    public void getAllStudentInfo() {
        response = RestAssured.given().log().all()
                .when()
                .get("http://localhost:8080/student/list");
        response.then().log().all().statusCode(200);

    }

    @Test(priority = 2)
    public void postStudentInfo() {
        List<String> courses = new ArrayList<>();
        courses.add("selenium");
        courses.add("api-api");

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
    public void getAllStudentAndExtractIdOfPostData003() {
        HashMap<String, Object> studentDetails = given().log().all()
                .when()
                .get(EndPoints.GET_ALL_STUDENTS) // refer to endpoints class in path package
                .then()
                .extract()
                .path("findAll{it.firstName=='" + firstName + "'}.get(0)");
        System.out.println(studentDetails);
        studentId = (int) studentDetails.get("id");

    }
    @Test(priority = 4)
    public void getSingleStudentInfo004() {
        given()
                .pathParam("id", studentId)
                .get("http://localhost:8080/student/{id}")
                .then().statusCode(200)
                .body("firstName", equalTo(firstName))
                .body("lastName", equalTo(lastName));
        System.out.println(firstName);
    }
    @Test(priority = 5) // delete above id (priority = 1)
    public void verifyNewStudentInfoDeletedByID005() {
        given()
                .pathParam("id", studentId)
                .when()
                .delete("http://localhost:8080/student/{id}")
                .then()
                .statusCode(204);
    }
    @Test(priority = 6)
    public void verifyStudentInfoDeleted006() {
        given()
                .pathParam("id", studentId)
                .when()
                .get("http://localhost:8080/student/{id}")
                .then().statusCode(404);
    }




}



