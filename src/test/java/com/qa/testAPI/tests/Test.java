package com.qa.testAPI.tests;

import com.qa.testAPI.Files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class Test {
    public static void main(String[] args ){

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(Payload.addPlace())
                .when().post("/maps/api/place/add/json")
                .then().assertThat().log().all().statusCode(200).body("scope", equalTo("APP"))
                .extract().response().asString();

        System.out.println("Response: "+response);

        JsonPath js = new JsonPath(response);  // for parsing json
        String placeID = js.getString("place_id");

        System.out.println("place id: "+placeID);


        // update place
        String address = "70 winter walk, USA";
        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body("{\n" +
                        "\"place_id\":\""+placeID+"\",\n" +
                        "\"address\":\""+address+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}")
                .when().put("/maps/api/place/update/json")
                .then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));


        // get place
        String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeID)
                .when().get("/maps/api/place/get/json")
                .then().assertThat().log().all().statusCode(200).extract().response().asString();

        JsonPath js1 = new JsonPath(getPlaceResponse);
        String actualAddress = js1.getString("address");
        Assert.assertEquals(actualAddress,address,"Address does not match");

    }
}

