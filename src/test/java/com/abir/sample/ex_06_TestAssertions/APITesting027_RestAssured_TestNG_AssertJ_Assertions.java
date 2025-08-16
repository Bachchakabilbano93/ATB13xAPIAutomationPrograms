package com.abir.sample.ex_06_TestAssertions;

import static org.assertj.core.api.Assertions.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

public class APITesting027_RestAssured_TestNG_AssertJ_Assertions {

    RequestSpecification requestSpecification;
    ValidatableResponse validatableResponse;
    Response response;
    String token;
    Integer bookingId;

    @Test
    public void test_POST() {

        String payload_POST = "{\n" +
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

        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com");
        requestSpecification.basePath("/booking");
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.body(payload_POST).log().all();

        Response response = requestSpecification.when().post();

        // Get Validate response to perform validation
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        // Rest Assured -> import org.hamcrest.Matchers; %4-5%
        // Matchers.equalTo()

        validatableResponse.body("bookingid", Matchers.notNullValue());
        validatableResponse.body("booking.firstname", Matchers.equalTo("Abir"));
        validatableResponse.body("booking.lastname", Matchers.equalTo("Bhattacharya"));
        validatableResponse.body("booking.depositpaid", Matchers.equalTo(true));


        // Extraction
        // Concept #1 - Normal(TestNG or AssertJ) IS IMP
        bookingId = response.then().extract().path("bookingid");
        String firstname = response.then().extract().path("booking.firstname");
        String lastname = response.then().extract().path("booking.lastname");

        // Concept #2 - (Jsonpath class) Another mechanism to extract the Keys, value by JSON Path
        JsonPath jp = new JsonPath(response.asString());
        String bookingID1 = jp.getString("bookingid");

        assertThat(jp.getInt("bookingid")).isEqualTo(bookingId);
        assertThat(jp.getString("booking.firstname")).isEqualTo("Abir");
        assertThat(jp.getString("booking.lastname")).isEqualTo("Bhattacharya");
        assertThat(jp.getInt("booking.totalprice")).isEqualTo(9000);
        assertThat(jp.getBoolean("booking.depositpaid")).isTrue();


        // TestNG - Extract the details of the firstname, bookingId, lastname from Response.
        // TestNG Assertions - 75%
        // SoftAssert vs HardAssert (90%)
        // This means that if any assertion fails,
        // the remaining statements in that test method will not be executed.
        Assert.assertEquals(firstname,"Abir");
        Assert.assertEquals(lastname,"Bhattacharya");
        Assert.assertNotNull(bookingId);

        if(!firstname.contains("Abir")){
            Assert.fail("Failed kar diya Test");
        }

        // AssertJ( 3rd- Lib to Assertions) - 20%
        assertThat(bookingId).isNotZero().isNotNull().isPositive();
        assertThat(firstname).isNotBlank().isNotEmpty().isNotNull().isEqualTo("Abir");

//        assertThat(deposit).isTrue();

    }
}
