package com.qa.testAPI.tests;

import com.qa.testAPI.pojo.GetCourses;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class OAuthTest {

    @Test
    public void verifyOAuth(){

// deserialization : getting JSON response and converting that into Java object
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        String response = given().log().all()
                .formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .formParam("grant_type", "client_credentials")
                .formParam("scope", "trust")
                .when().log().all()
                .post("/oauthapi/oauth2/resourceOwner/token").asString();

        System.out.println(response);

        JsonPath js = new JsonPath(response);
        String accessToken = js.getString("access_token");
        System.out.println("accessToken: "+accessToken);

        GetCourses gc = given().log().all()
                .queryParam("access_token", accessToken)
                .when().log().all()
                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourses.class);

        System.out.println(gc.getUrl());
        System.out.println(gc.getInstructor());
        System.out.println(gc.getLinkedIn());
        System.out.println(gc.getCourses().getWebAutomation().get(0).getCourseTitle());

    }
}

