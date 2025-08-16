package com.abir.ATB13xAPITasks.August8thTasks_TestNG_Priority_Parallel;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;

public class Restful_Booker_Common_HTTP_Methods_E2E_ParallelAllTests {

    String baseURL = "https://restful-booker.herokuapp.com";

    @Test(priority = 1)
    public void createBookingTest() {
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

        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath("/booking")
                .contentType(ContentType.JSON)
                .body(payload)
                .post();

        ValidatableResponse vr = response.then();
        vr.statusCode(200);
        int bookingId = response.jsonPath().get("bookingid");
    }

    @Test(priority = 1)
    public void createAuthTokenTest() {
        String payload = "{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}";

        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath("/auth")
                .contentType(ContentType.JSON)
                .body(payload)
                .post();

        ValidatableResponse vr = response.then();
        vr.statusCode(200);
        String token = response.jsonPath().get("token");
    }

    @Test(priority = 2)
    public void updateBookingByIdTest() {
        String token = RestAssured.given()
                .baseUri(baseURL)
                .basePath("/auth")
                .contentType(ContentType.JSON)
                .body("{ \"username\": \"admin\", \"password\": \"password123\" }")
                .post()
                .jsonPath().get("token");

        String bookingPayload = "{\n" +
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

        int bookingId = RestAssured.given()
                .baseUri(baseURL)
                .basePath("/booking")
                .contentType(ContentType.JSON)
                .body(bookingPayload)
                .post()
                .jsonPath().get("bookingid");

        String updatePayload = "{\n" +
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

        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath("/booking/" + bookingId)
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(updatePayload)
                .put();

        ValidatableResponse vr = response.then();
        vr.statusCode(200);
    }

    @Test(priority = 3)
    public void getBookingByIdTest() {
        String token = RestAssured.given()
                .baseUri(baseURL)
                .basePath("/auth")
                .contentType(ContentType.JSON)
                .body("{ \"username\": \"admin\", \"password\": \"password123\" }")
                .post()
                .jsonPath().get("token");

        String bookingPayload = "{\n" +
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

        int bookingId = RestAssured.given()
                .baseUri(baseURL)
                .basePath("/booking")
                .contentType(ContentType.JSON)
                .body(bookingPayload)
                .post()
                .jsonPath().get("bookingid");

        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath("/booking/" + bookingId)
                .get();

        ValidatableResponse vr = response.then();
        vr.statusCode(200);
        String firstname = response.jsonPath().get("firstname");
        firstname.equalsIgnoreCase("Gutur");
    }

    @Test(priority = 3)
    public void partialUpdateBookingTest() {
        String token = RestAssured.given()
                .baseUri(baseURL)
                .basePath("/auth")
                .contentType(ContentType.JSON)
                .body("{ \"username\": \"admin\", \"password\": \"password123\" }")
                .post()
                .jsonPath().get("token");

        String bookingPayload = "{\n" +
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

        int bookingId = RestAssured.given()
                .baseUri(baseURL)
                .basePath("/booking")
                .contentType(ContentType.JSON)
                .body(bookingPayload)
                .post()
                .jsonPath().get("bookingid");

        String partialPayload = "{\n" +
                "    \"firstname\" : \"James\",\n" +
                "    \"lastname\" : \"Brown\"\n" +
                "}";

        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath("/booking/" + bookingId)
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(partialPayload)
                .patch();

        ValidatableResponse vr = response.then();
        vr.statusCode(200);
        String firstname = response.jsonPath().get("firstname");
        firstname.equalsIgnoreCase("James");
    }

    @Test(priority = 4)
    public void deleteBookingByIdTest() {
        String token = RestAssured.given()
                .baseUri(baseURL)
                .basePath("/auth")
                .contentType(ContentType.JSON)
                .body("{ \"username\": \"admin\", \"password\": \"password123\" }")
                .post()
                .jsonPath().get("token");

        String bookingPayload = "{\n" +
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

        int bookingId = RestAssured.given()
                .baseUri(baseURL)
                .basePath("/booking")
                .contentType(ContentType.JSON)
                .body(bookingPayload)
                .post()
                .jsonPath().get("bookingid");

        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath("/booking/" + bookingId)
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .delete();

        ValidatableResponse vr = response.then();
        vr.statusCode(201);
    }
}

