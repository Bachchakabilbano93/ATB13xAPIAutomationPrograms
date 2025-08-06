package com.abir.ATB13xAPITasks.August5thTask1_Main_Maven;

import io.restassured.RestAssured;

public class Restful_Booker_Get {
    public static void main(String[] args) {

        RestAssured.given()
                .baseUri("https://restful-booker.herokuapp.com")
                .basePath("/ping")
                .when().log().all().get()
                .then().log().all().statusCode(201);

    }
}
