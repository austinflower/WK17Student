package RequesIN;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class requesIN1 {
    RequestSpecification requestSpecification;
    Response response;
    ValidatableResponse validatableResponse;
    int mynewuserid;
    String mytokenid;

    @Test
    public void agetAllUsers() {
        RestAssured.baseURI = "https://reqres.in/api/users";
        requestSpecification = given();
        response = requestSpecification.get();
        System.out.println(response.prettyPrint());
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }
    @Test
    public void bcreateUserPost(){
        String jsonData ="{\n" +
                "    \"name\": \"Preksha\",\n" +
                "    \"job\": \"Tester\"\n" +
                "}";
        RestAssured.baseURI = "https://reqres.in/api/users";
        requestSpecification = given();
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.body(jsonData);
        response = requestSpecification.post();
        System.out.println(response.prettyPrint());
        validatableResponse = response.then();
        validatableResponse.statusCode(201);

    }
    @Test
    public void agetASingleUserByID() {
        RestAssured.baseURI = "https://reqres.in/api/users/7";
        requestSpecification = given();
        response = requestSpecification.get();
        System.out.println(response.prettyPrint());
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }
    @Test
    public void dcreatetoken(){
        String jsonData = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"pistol\"\n" +
                "}";
        validatableResponse = given()
                .contentType(ContentType.JSON)
                .body(jsonData)
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(200);
        mytokenid=validatableResponse.extract().body().path("token");
        System.out.println(mytokenid);
    }
    @Test
    public void eupdateUserPUT(){
        String jsonData ="{\n" +
                "    \"name\": \"morpheus-update\",\n" +
                "    \"job\": \"zion resident\"\n" +
                "}";
        given().log().all()
                .baseUri("https://reqres.in/api/users/2")
                .contentType("application/json")
                .cookie("token",mytokenid)
                .when()
                .body(jsonData)
                .put()
                .then()
                .statusCode(200);
    }
    @Test
    public void fupdateUserPatch(){
        String jsonData ="{\n" +
                "    \"name\": \"morpheus-patch\",\n" +
                "    \"job\": \"zion resident\"\n" +
                "}";
        given().log().all()
                .baseUri("https://reqres.in/api/users/2")
                .contentType("application/json")
                .cookie("token",mytokenid)
                .when()
                .body(jsonData)
                .patch()
                .then()
                .statusCode(200);
    }
    @Test
    public void gdeleteBookingById() {
        given().log().all()
                .baseUri("https://reqres.in/api/users/2")
                .cookie("token",mytokenid)
                .when()
                .delete()
                .then()
                .statusCode(204);
    }
}

