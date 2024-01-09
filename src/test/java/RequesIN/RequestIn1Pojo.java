package RequesIN;

import example.model.RequestInPojo;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RequestIn1Pojo {
    RequestSpecification requestSpecification;
    Response response;
    ValidatableResponse validatableResponse;
    RequestInPojo requestInPojo = new RequestInPojo();
    static String name = "Preksha";
    static String job = "Tester";
    static String myid;
    @Test(priority = 1)
    public void getAllUsersGET(){
        RestAssured.baseURI ="https://reqres.in/api/users";
        requestSpecification = given();
        response =requestSpecification.get();
        System.out.println(response.prettyPrint());
        validatableResponse =response.then();
        validatableResponse.statusCode(200);
    }
    @Test(priority = 2)
    public void createUserPOST(){
        RequestInPojo requestInPojo = new RequestInPojo();
        requestInPojo.setName(name);
        requestInPojo.setJob(job);
        response = RestAssured.given().log().all()
                .contentType("application/json")
                .when()
                .body(requestInPojo)
                .post("https://reqres.in/api/users");
        response.then().log().all().statusCode(201);
        myid = response.then().extract().path("id");
        System.out.println(myid);
    }
    @Test(priority = 3)
    public void getRequestByIdGET(){
        given().log().all()
                .baseUri("https://reqres.in/api")
                .pathParam("myid",myid)
                .when()
                .get("/users/{myid}")
                .then().statusCode(404);
    }
    @Test(priority = 4)
    public void updateRequestByPut(){
        RequestInPojo requestInPojo = new RequestInPojo();
        requestInPojo.setName("Preksha Updated");
        requestInPojo.setJob("Tester Updated");
        response = (Response) RestAssured.given().log().all()
                .baseUri("https://reqres.in/api")
                .pathParam("myid",myid)
                .contentType("application/json")
                .when()
                .body(requestInPojo)
                .put("/users/{myid}");
        response.then().statusCode(200).log().all()
                .body("name",equalTo("Preksha Updated"));

    }
    @Test(priority = 5)
    public void updatedRequestByPatch() {
        RequestInPojo requestInPojo = new RequestInPojo();
        requestInPojo.setName("Preksha Patch");
        requestInPojo.setJob("Tester Patch");
        response = RestAssured.given().log().all()
                .baseUri("https://reqres.in/api")
                .pathParam("myid", myid)
                .contentType("application/json")
                .when()
                .body(requestInPojo)
                .patch("/users/{myid}");
        response.then().statusCode(200).log().all()
                .body("job", equalTo("Tester Patch"));
    }
    @Test(priority = 6)
    public void deleteRequestId(){
        given()
                .pathParam("myid", myid)
                .when()
                .delete("https://reqres.in/api/users/{myid}")
                .then()
                .statusCode(204);

    }




}
