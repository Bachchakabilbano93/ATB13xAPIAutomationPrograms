package com.abir.ATB13xAPITasks.August8thTasks_TestNG_Priority_Parallel;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class Restful_Booker_Common_HTTP_Methods_E2E_1 {
    RequestSpecification r;
    Response response;
    ValidatableResponse vr;
    String baseURL = "https://restful-booker.herokuapp.com";
    String token;
    int bookingId;
    String firstname;

    @Test(priority = 1)
    public void createBooking() {
        String payload = "{\n" +
                "    \"firstname\" : \"Abir\",\n" +
                "    \"lastname\" : \"Bhattacharya\",\n" +
                "    \"totalprice\" : 9000,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2025-09-24\",\n" +
                "        \"checkout\" : \"2025-10-28\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";

        r = RestAssured.given();
        r.baseUri(baseURL);
        r.basePath("/booking");
        r.contentType(ContentType.JSON);
        r.body(payload).log().all();

        response = r.when().log().all().post();
        bookingId = response.jsonPath().get("bookingid");

        vr = response.then().log().all();
        vr.statusCode(200);

    }

    @Test(priority = 1)
    public void createAuthToken(){
        String payload = "{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}";

        r = RestAssured.given();
        r.baseUri(baseURL);
        r.basePath("/auth");
        r.contentType(ContentType.JSON);
        r.body(payload).log().all();

        response = r.when().log().all().post();
        token = response.jsonPath().get("token");

        vr = response.then().log().all();
        vr.statusCode(200);

    }

    @Test(priority = 2)
    public void updateBookingById(){
        String payload = "{\n" +
                "    \"firstname\" : \"Gutur\",\n" +
                "    \"lastname\" : \"Bhattacharya\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";

        r = RestAssured.given();
        r.baseUri(baseURL);
        r.basePath("/booking/" + bookingId);
        r.contentType(ContentType.JSON);
        r.cookie("token", token);
        r.body(payload).log().all();

        response = r.when().log().all().put();

        vr = response.then().log().all();
        vr.statusCode(200);

    }

    @Test(priority = 3)
    public void getBookingById(){
        r = RestAssured.given();
        r.baseUri(baseURL);
        r.basePath("/booking/" + bookingId);

        response = r.when().log().all().get();
        firstname = response.jsonPath().get("firstname");

        vr = response.then().log().all();
        vr.statusCode(200);
        firstname.equalsIgnoreCase("Gutur");

    }

    @Test(priority = 3)
    public void partialUpdateBooking(){
        String payload = "{\n" +
                "    \"firstname\" : \"James\",\n" +
                "    \"lastname\" : \"Brown\"\n" +
                "}";

        r = RestAssured.given();
        r.baseUri(baseURL);
        r.basePath("/booking/1");
        r.contentType(ContentType.JSON);
        r.cookie("token", token);
        r.body(payload).log().all();

        response = r.when().log().all().patch();
        firstname = response.jsonPath().get("firstname");

        vr = response.then().log().all();
        vr.statusCode(200);
        firstname.equalsIgnoreCase("James");
    }

    @Test(priority = 4)
    public void deleteBookingById(){
        r = RestAssured.given();
        r.baseUri(baseURL);
        r.basePath("/booking/" + bookingId);
        r.contentType(ContentType.JSON);
        r.cookie("token", token).log().all();

        response = r.when().log().all().delete();

        vr = response.then().log().all();
        vr.statusCode(201);

    }
}
