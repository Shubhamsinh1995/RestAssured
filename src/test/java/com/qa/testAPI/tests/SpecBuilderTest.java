package com.qa.testAPI.tests;

import com.qa.testAPI.Files.Payload;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class SpecBuilderTest {
    @Test
    public void testSpecBuilder(){

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        RequestSpecification req =new RequestSpecBuilder().setBaseUri(RestAssured.baseURI).addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON).build();

        ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        Response response = given().spec(req)
                .body(Payload.addPlace())
                .when().post("/maps/api/place/add/json")
                .then().assertThat().log().all().spec(res)
                .extract().response();

        String respnseString = response.asString();
        System.out.println(respnseString);
    }

}
