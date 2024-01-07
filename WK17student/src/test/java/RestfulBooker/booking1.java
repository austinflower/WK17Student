package RestfulBooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class booking1 {
    RequestSpecification requestSpecification;
    Response response;
    ValidatableResponse validatableResponse;
    int mybookingid;
    String mytokenid;

    @Test
    public void agetAllBooking() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking";
        requestSpecification = given();
        response = requestSpecification.get();
        System.out.println(response.prettyPrint());
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
        int bookingId = response.then().extract().body().path("[0].bookingid");
        System.out.println(bookingId);

    }
    @Test
    public void bcreateBookingPost(){
        String jsonData ="{\n" +
                "    \"firstname\": \"Preksha\",\n" +
                "    \"lastname\": \"Patel\",\n" +
                "    \"totalprice\": 111,\n" +
                "    \"depositpaid\": true,\n" +
                "    \"bookingdates\": {\n" +
                "        \"checkin\": \"2018-01-01\",\n" +
                "        \"checkout\": \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\": \"Breakfast\"\n" +
                "}";
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking";
        requestSpecification = given();
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.body(jsonData);
        response = requestSpecification.post();
        System.out.println(response.prettyPrint());
        validatableResponse = response.then();
        validatableResponse.statusCode(200);
        mybookingid = validatableResponse.extract().body().path("bookingid");
        System.out.println(mybookingid);
    }
    @Test
    public void cgetBookingById() {
        given().log().all()
                .baseUri("https://restful-booker.herokuapp.com")
                .pathParam("bookingid", mybookingid)
                .when()
                .get("/booking/{bookingid}")
                .then();

    }
    @Test
    public void dcreatetoken(){
        String jsonData = "{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}";
        validatableResponse = given()
                .contentType(ContentType.JSON)
                .body(jsonData)
                .post("https://restful-booker.herokuapp.com/auth")
                .then()
                .statusCode(200);
        mytokenid=validatableResponse.extract().body().path("token");
        System.out.println(mytokenid);
    }
    @Test
    public void eupdatebookingPUT(){
        String jsonData ="{\n" +
                "    \"firstname\": \"Preksha-Updated\",\n" +
                "    \"lastname\": \"Patel-Updated\",\n" +
                "    \"totalprice\": 111,\n" +
                "    \"depositpaid\": true,\n" +
                "    \"bookingdates\": {\n" +
                "        \"checkin\": \"2018-01-01\",\n" +
                "        \"checkout\": \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\": \"Breakfast\"\n" +
                "}";
        given().log().all()
                .baseUri("https://restful-booker.herokuapp.com")
                .contentType("application/json")
                .cookie("token",mytokenid)
                .pathParam("bookingid", mybookingid)
                .when()
                .body(jsonData)
                .put("/booking/{bookingid}")
                .then()
                .statusCode(200)
                .body("firstname", equalTo("Preksha-Updated"));
    }
    @Test
    public void fpartialupdatebookingPATCH(){
        String jsonData ="{\n" +
                "    \"firstname\" : \"James\",\n" +
                "    \"lastname\" : \"Brown\"\n" +
                "}";
        given().log().all()
                .baseUri("https://restful-booker.herokuapp.com")
                .contentType("application/json")
                .accept("application/json")
                .cookie("token",mytokenid)
                .pathParam("bookingid", mybookingid)
                .when()
                .body(jsonData)
                .patch("/booking/{bookingid}")
                .then()
                .statusCode(200)
                .body("firstname", equalTo("James"));
    }
    @Test
    public void gdeleteBookingById() {
        given().log().all()
                .baseUri("https://restful-booker.herokuapp.com")
                .cookie("token",mytokenid)
                .pathParam("bookingid", mybookingid)
                .when()
                .delete("/booking/{bookingid}")
                .then()
                .statusCode(201);
    }
    @Test
    public void hgetBookingByIdVerifyDeleted() {
        given().log().all()
                .baseUri("https://restful-booker.herokuapp.com")
                .pathParam("bookingid", mybookingid)
                .when()
                .get("/booking/{bookingid}")
                .then()
                .statusCode(404);
    }
}
