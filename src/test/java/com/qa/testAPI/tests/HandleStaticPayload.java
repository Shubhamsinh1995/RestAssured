package com.qa.testAPI.tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.hamcrest.Matchers.equalTo;

public class HandleStaticPayload {
    @Test
    public void verifyStaticPayload() throws IOException {
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(new String(Files.readAllBytes(Paths.get("C:\\my java\\API DATA\\staticPayload1.json"))))
                .when().post("/maps/api/place/add/json")
                .then().assertThat().log().all().statusCode(200).body("scope", equalTo("APP"))
                .extract().response().asString();

        System.out.println(response);

        JsonPath js = new JsonPath(response);
        String actualAddress = js.getString("status");
        Assert.assertEquals(actualAddress,"OK","status does not match");
    }
}

