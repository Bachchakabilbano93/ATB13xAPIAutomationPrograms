    package com.abir.ATB13xAPITasks.August5thTask2_TestNG;

    import io.restassured.RestAssured;
    import io.restassured.http.ContentType;
    import org.testng.annotations.Test;

    public class Restful_Booker_Get_Post_TestNG {

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

        @Test
        public void postRequest() {

            RestAssured.given()
                    .baseUri("https://restful-booker.herokuapp.com")
                    .basePath("/booking")
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when().log().all().post()
                    .then().log().all().statusCode(200);
        }

        @Test
        public void getRequest() {
            RestAssured.given()
                        .baseUri("https://restful-booker.herokuapp.com")
                        .basePath("/ping")
                        .when().log().all().get()
                        .then().log().all().statusCode(201);
        }
    }
