package BestBuyApp.bestBuy3;

import example.model.BBStorePojo;
import example.model.Services;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.CoreMatchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BBStore1Pojo {
    RequestSpecification requestSpecification;
    Response response;
    ValidatableResponse validatableResponse;
    int storeid;
    static String name = "Dallas Store";
    static String type = "BigBox";
    static String address = "1234 Main dr";
    static String address2 = "";
    static String city = "Dallas";
    static String state ="TX";
    static String zip = "12345";
    static Integer lat = 0;
    static Integer lng = 0;
    static String hours = "10-9";
    static String Services = "";
    static Services services;
    @Test(priority = 1)
    public void getStoreGET(){
        given().log().all()
                .when()
                .get("http://localhost:3030/stores")
                .then().log().all()
                .statusCode(200)
                .body("limit", equalTo(10));
    }
    @Test(priority = 2)
    public void createStorePost(){
        BBStorePojo bbStorePojo = new BBStorePojo();
        Services services = new Services();
        services.setDelivery(true);
        services.setCarryout(true);
        bbStorePojo.setName(name);
        bbStorePojo.setType(type);
        bbStorePojo.setAddress(address);
        bbStorePojo.setAddress2(address2);
        bbStorePojo.setCity(city);
        bbStorePojo.setState(state);
        bbStorePojo.setZip(zip);
        bbStorePojo.setLat(lat);
        bbStorePojo.setLng(lng);
        bbStorePojo.setHours(hours);
        bbStorePojo.setServices(services);
        validatableResponse =given()
                .contentType("application/json")
                .body(bbStorePojo)
                .post("http://localhost:3030/stores")
                .then().log().all()
                .statusCode(201)
                .body("name", CoreMatchers.equalTo("Dallas Store"));
        storeid = validatableResponse.extract().path("id");
        System.out.println(validatableResponse.extract().asPrettyString());
        System.out.println(storeid);
    }
    @Test(priority = 3)
    public void getStoreByIdGET(){
        given().log().all()
                .baseUri("http://localhost:3030")
                .pathParam("storeid", storeid)
                .when()
                .get("/stores/{storeid}")
                .then().log().all();

    }
    @Test(priority = 4)
    public void updateStorePATCH(){
        BBStorePojo bbStorePojo = new BBStorePojo();
        Services services = new Services();
        services.setDelivery(true);
        services.setCarryout(true);
        bbStorePojo.setName(name + "- Updated");
        bbStorePojo.setType(type);
        bbStorePojo.setAddress(address);
        bbStorePojo.setAddress2(address2);
        bbStorePojo.setCity(city);
        bbStorePojo.setState(state);
        bbStorePojo.setZip(zip);
        bbStorePojo.setLat(lat);
        bbStorePojo.setLng(lng);
        bbStorePojo.setHours(hours);
        bbStorePojo.setServices(services);
        validatableResponse =given()
                .baseUri("http://localhost:3030")
                .contentType("application/json")
                .pathParam("storeid", storeid)
                .body(bbStorePojo)
                .patch("/stores/{storeid}")
                .then().log().all()
                .statusCode(200)
                .body("name", CoreMatchers.equalTo("Dallas Store- Updated"));
        //storeid = validatableResponse.extract().path("id");
        System.out.println(validatableResponse.extract().asPrettyString());
        System.out.println(storeid);

    }
    @Test(priority = 5)
    public void deleteStorebyIDDELETE(){
        given().log().all()
                .baseUri("http://localhost:3030")
                .pathParam("storeid", storeid)
                .when()
                .delete("/stores/{storeid}")
                .then().log().all()
                .statusCode(200)
                .body("id", CoreMatchers.equalTo(storeid));

    }






}
