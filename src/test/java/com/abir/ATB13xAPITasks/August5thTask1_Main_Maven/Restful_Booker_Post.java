package com.abir.ATB13xAPITasks.August5thTask1_Main_Maven;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class Restful_Booker_Post {
    public static void main(String[] args) {

        String payload = "{\n" +
                "    \"firstname\" : \"abir\",\n" +
                "    \"lastname\" : \"Bhattacharya\",\n" +
                "    \"totalprice\" : 9000,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2025-09-24\",\n" +
                "        \"checkout\" : \"2025-10-28\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";

        RestAssured.given()
                .baseUri("https://restful-booker.herokuapp.com")
                .basePath("/booking")
                .contentType(ContentType.JSON)
                .body(payload)
                .when().log().all().post()
                .then().log().all().statusCode(200);

    }
}
