package RestfulBooker;

import example.model.Bookingdates;
import example.model.CreateTokenPojo;
import example.model.RestfulBookerPojo;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class booking1Pojo {
    RequestSpecification requestSpecification;
    Response response;
    ValidatableResponse validatableResponse;
    int mybookingid;
    String mytokenid;
    static String firstname = "Preksha";
    static String lastname = "Patel";
    static int totalprice = 125;
    static boolean depositpaid = true;
    static String checkin = "2024-01-01";
    static String checkout = "2024-01-05";
    static String additionalneeds = "Breakfast";
    static String username = "admin";
    static String password = "password123";
    @Test(priority = 1)
    public void getAllBooking(){
        response = RestAssured.given().log().all()
                .when()
                .get("https://restful-booker.herokuapp.com/booking");
        response.then().log().all().statusCode(200);
        int bookingId = response.then().extract().body().path("[0].bookingid");
        System.out.println(bookingId);
    }
    @Test(priority = 2)
    public void createBookingPOST(){
        Bookingdates bookingdates = new Bookingdates();
        RestfulBookerPojo restfulBookerPojo = new RestfulBookerPojo();
        restfulBookerPojo.setFirstname(firstname);
        restfulBookerPojo.setLastname(lastname);
        restfulBookerPojo.setTotalprice(totalprice);
        restfulBookerPojo.setDepositpaid(depositpaid);
        bookingdates.setCheckin(checkin);
        bookingdates.setCheckout(checkout);
        restfulBookerPojo.setBookingdates(bookingdates);
        restfulBookerPojo.setAdditionalneeds(additionalneeds);
        response = (Response) RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(restfulBookerPojo)
                .post("https://restful-booker.herokuapp.com/booking");

        response.then().log().all().statusCode(200);
        mybookingid = response.then().extract().path("bookingid");
        System.out.println(mybookingid);
    }
    @Test(priority = 3)
    public void getBookingById(){
        given().log().all()
                .baseUri("https://restful-booker.herokuapp.com")
                .pathParam("bookingid", mybookingid)
                .when()
                .get("/booking/{bookingid}")
                .then();
    }
    @Test(priority = 4)
    public void createToken(){
        CreateTokenPojo createTokenPojo = new CreateTokenPojo();
        createTokenPojo.setUsername(username);
        createTokenPojo.setPassword(password);
        Response response = (Response) RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(createTokenPojo)
                .post("https://restful-booker.herokuapp.com/auth")
                .then().log().all()
                .statusCode(200)
                .extract().response();
        //mytokenid=response.then().extract().path("token");
        mytokenid = response.path("token");
        System.out.println(mytokenid);
    }
    @Test(priority = 5)
    public void updateBookingPUT(){
        Bookingdates bookingdates = new Bookingdates();
        RestfulBookerPojo restfulBookerPojo = new RestfulBookerPojo();
        restfulBookerPojo.setFirstname(firstname + "- Updated");
        restfulBookerPojo.setLastname(lastname + "- Updated");
        restfulBookerPojo.setTotalprice(199);
        restfulBookerPojo.setDepositpaid(depositpaid);
        bookingdates.setCheckin("2024-02-01");
        bookingdates.setCheckout("2024-03-01");
        restfulBookerPojo.setBookingdates(bookingdates);
        restfulBookerPojo.setAdditionalneeds(additionalneeds);
        given().log().all()
                .baseUri("https://restful-booker.herokuapp.com")
                .contentType("application/json")
                .cookie("token",mytokenid)
                .pathParam("bookingid", mybookingid)
                .when()
                .body(restfulBookerPojo)
                .put("/booking/{bookingid}")
                .then()
                .statusCode(200)
                .body("firstname", equalTo("Preksha- Updated"));
    }
    @Test(priority = 6)
    public void partialUpdateBookingPATCH(){
        Bookingdates bookingdates = new Bookingdates();
        RestfulBookerPojo restfulBookerPojo = new RestfulBookerPojo();
        restfulBookerPojo.setFirstname(firstname + "- Updated-Patch");
        restfulBookerPojo.setLastname(lastname);
        restfulBookerPojo.setTotalprice(totalprice);
        restfulBookerPojo.setDepositpaid(depositpaid);
        bookingdates.setCheckin(checkin);
        bookingdates.setCheckout(checkout);
        restfulBookerPojo.setBookingdates(bookingdates);
        restfulBookerPojo.setAdditionalneeds(additionalneeds);
        given().log().all()
                .baseUri("https://restful-booker.herokuapp.com")
                .contentType("application/json")
                .accept("application/json")
                .cookie("token",mytokenid)
                .pathParam("bookingid", mybookingid)
                .when()
                .body(restfulBookerPojo)
                .patch("/booking/{bookingid}")
                .then()
                .statusCode(200)
                .body("firstname", equalTo("Preksha- Updated-Patch"));

    }
    @Test(priority = 7)
    public void deleteBookingbyID(){
        given().log().all()
                .baseUri("https://restful-booker.herokuapp.com")
                .cookie("token",mytokenid)
                .pathParam("bookingid", mybookingid)
                .when()
                .delete("/booking/{bookingid}")
                .then()
                .statusCode(201);
    }
    @Test(priority = 8)
    public void getBookingByIDVerifyDeleted(){
        given().log().all()
                .baseUri("https://restful-booker.herokuapp.com")
                .pathParam("bookingid", mybookingid)
                .when()
                .get("/booking/{bookingid}")
                .then()
                .statusCode(404);
    }
}


