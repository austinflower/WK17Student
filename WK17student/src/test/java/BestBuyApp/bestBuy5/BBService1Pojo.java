package BestBuyApp.bestBuy5;

import example.model.BBServicePojo;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.CoreMatchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BBService1Pojo {
    RequestSpecification requestSpecification;
    Response response;
    ValidatableResponse validatableResponse;
    static String name = "How to use new iPhone";
    int serviceid;

    @Test(priority = 1)
    public void getallServicesGET(){
        RestAssured.baseURI ="http://localhost:3030/services";
        requestSpecification =RestAssured.given();
        response =requestSpecification.get();
        System.out.println(response.prettyPrint());
        validatableResponse =response.then();
        validatableResponse.statusCode(200)
                        .body("limit", equalTo(10));
    }
    @Test(priority = 2)
    public void addNewServicePOST(){
        BBServicePojo bbServicePojo = new BBServicePojo();
        bbServicePojo.setName(name);
        validatableResponse = given()
                .contentType("application/json")
                .body(bbServicePojo)
                .post("http://localhost:3030/services")
                .then()
                .statusCode(201)
                .body("name", CoreMatchers.equalTo("How to use new iPhone"));
        serviceid = validatableResponse.extract().path("id");
        System.out.println(validatableResponse.extract().asPrettyString());
    }
    @Test(priority = 3)
    public void getServiceByIdGET(){
        given().log().all()
                .baseUri("http://localhost:3030")
                .pathParam("serviceid", serviceid)
                .when()
                .get("/services/{serviceid}")
                .then().log().all();
    }
    @Test(priority = 4)
    public void updateServiceByIdPATCH(){
        BBServicePojo bbServicePojo = new BBServicePojo();
        bbServicePojo.setName(name + "- 15 Pro Max");
        validatableResponse = given()
                .log().all()
                .contentType("application/json")
                .baseUri("http://localhost:3030")
                .pathParam("serviceid", serviceid)
                .body(bbServicePojo)
                .patch("/services/{serviceid}")
                .then()
                .statusCode(200)
                .body("name", CoreMatchers.equalTo("How to use new iPhone- 15 Pro Max"));

    }
    @Test(priority = 5)
    public void deleteServiceByIdDELETE(){
        given().log().all()
                .baseUri("http://localhost:3030")
                .pathParam("serviceid", serviceid)
                .when()
                .delete("/services/{serviceid}")
                .then().log().all()
                .statusCode(200)
                .body("id", CoreMatchers.equalTo(serviceid));

    }
}
