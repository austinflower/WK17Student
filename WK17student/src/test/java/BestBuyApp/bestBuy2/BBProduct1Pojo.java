package BestBuyApp.bestBuy2;

import example.model.BBProductPojo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.CoreMatchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BBProduct1Pojo {
    RequestSpecification requestSpecification;
    Response response;
    ValidatableResponse validatableResponse;
    int myid;
    static String name = "Red Pen";
    static String name2 = "Red Pen - Updated";
    static String type = "Pen";
    static int price= 3;
    static int shipping =5;
    static  String upc = "1234567890";
    static String discription ="Something to write with";
    static String manufacturer = "Bic";
    static  String model = "series2";
    static String url = "Bestbuy.com";
    static String image ="image1";

    @Test(priority = 1)
    public void getallproducts() {
        given().log().all()
                .when()
                .get("http://localhost:3030/products")
                .then().log().all()
                .body("limit", equalTo(10));
    }
    @Test(priority = 2)
    public void createProductPost(){
        BBProductPojo bbProductPojo =new BBProductPojo();
        bbProductPojo.setName(name);
        bbProductPojo.setType(type);
        bbProductPojo.setPrice(price);
        bbProductPojo.setShipping(shipping);
        bbProductPojo.setUpc(upc);
        bbProductPojo.setDescription(discription);
        bbProductPojo.setManufacturer(manufacturer);
        bbProductPojo.setModel(model);
        bbProductPojo.setUrl(url);
        bbProductPojo.setImage(image);
        validatableResponse = given()
                .baseUri("http://localhost:3030")
                .contentType(ContentType.JSON)
                .body(bbProductPojo)
                .when()
                .post("/products")
                .then()
                .statusCode(201).log().all()
                .body("name", CoreMatchers.equalTo("Red Pen"));
            myid = validatableResponse.extract().path("id");
        System.out.println(myid);

    }
    @Test(priority = 3)
    public void getProductById(){

        given().log().all()
                .baseUri("http://localhost:3030")
                .pathParam("productid", myid)
                .when()
                .get("/products/{productid}")
                .then().log().all();
    }
    @Test(priority = 4)
    public void updateProductPatch(){
        BBProductPojo bbProductPojo =new BBProductPojo();
        bbProductPojo.setName(name2);
        bbProductPojo.setType(type);
        bbProductPojo.setPrice(price);
        bbProductPojo.setShipping(shipping);
        bbProductPojo.setUpc(upc);
        bbProductPojo.setDescription(discription);
        bbProductPojo.setManufacturer(manufacturer);
        bbProductPojo.setModel(model);
        bbProductPojo.setUrl(url);
        bbProductPojo.setImage(image);
        given().log().all()
                .baseUri("http://localhost:3030")
                .contentType(ContentType.JSON)
                .accept("application/json")
                .pathParam("productid", myid)
                .when()
                .patch("/products/{productid}")
                .then().log().all()
                .statusCode(200);
                //.body("name",equalTo("Red Pen - Updated"));
    }
    @Test(priority = 5)
    public void deleteProductById(){
        given().log().all()
                .baseUri("http://localhost:3030")
                .pathParam("productid", myid)
                .when()
                .delete("/products/{productid}")
                .then().log().all()
                .statusCode(200)
                .body("id", CoreMatchers.equalTo(myid));

    }








}
