package BestBuyApp.bestBuy5;

import example.model.BBCategoriesPojo;
import example.utils.TestUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.CoreMatchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BBCateories1Pojo {
    RequestSpecification requestSpecification;
    Response response;
    ValidatableResponse validatableResponse;
    static String name = "Preksha12" + TestUtils.getRandomValue();
    static String id = "abcat2222222" + TestUtils.getRandomValue();
    String catID;
    @Test(priority = 1)
    public void getallCategoriesGET(){
        RestAssured.baseURI ="http://localhost:3030/categories";
        requestSpecification = given();
        response =requestSpecification.get();
        System.out.println(response.prettyPrint());
        validatableResponse =response.then();
        validatableResponse.statusCode(200)
                .body("limit", equalTo(10));
    }
    @Test(priority = 2)
    public void addnewCategoriesPOST(){
        BBCategoriesPojo bbCategoriesPojo = new BBCategoriesPojo();
        bbCategoriesPojo.setId(id);
        bbCategoriesPojo.setName(name);
        validatableResponse = given ()
                .contentType("application/json")
                .body(bbCategoriesPojo)
                .post("http://localhost:3030/categories")
                .then().log().all()
                .statusCode(201);
                //.body("name", CoreMatchers.equalTo("New Cat"));
        catID = validatableResponse.extract().path("id");
        System.out.println(validatableResponse.extract().asPrettyString());
        //System.out.println(catID);
    }
    @Test(priority = 3)
    public void getCategoriesByIDGET(){
        given().log().all()
                .baseUri("http://localhost:3030")
                .pathParam("catID", catID)
                .when()
                .get("/categories/{catID}")
                .then().log().all();
   }
   @Test(priority = 4)
    public void updateCategoriesPATCH(){
       BBCategoriesPojo bbCategoriesPojo = new BBCategoriesPojo();
       bbCategoriesPojo.setId(id);
       bbCategoriesPojo.setName(name + "- Updated");
       validatableResponse = given()
               .log().all()
               .contentType("application/json")
               .baseUri("http://localhost:3030")
               .pathParam("catID", catID)
               .body(bbCategoriesPojo)
               .patch("/categories/{catID}")
               .then()
               .statusCode(200);
    }
    @Test(priority = 5)
    public void deleteCategoriesDELETE(){
        given().log().all()
                .baseUri("http://localhost:3030")
                .pathParam("catID", catID)
                .when()
                .delete("/categories/{catID}")
                .then().log().all()
                .statusCode(200)
                .body("id", CoreMatchers.equalTo(catID));
    }
}
