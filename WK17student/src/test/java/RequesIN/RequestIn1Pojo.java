package RequesIN;

import example.model.RequestInPojo;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RequestIn1Pojo {
    RequestSpecification requestSpecification;
    Response response;
    ValidatableResponse validatableResponse;
    static String name = "Preksha";
    static String job = "Tester";
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

    }
}
