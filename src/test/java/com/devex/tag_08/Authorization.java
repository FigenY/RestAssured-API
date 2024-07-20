package com.devex.tag_08;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class Authorization {

    @BeforeMethod
    public void setUp() {
        baseURI = "http://www.eurotech.study";
    }


    public static String getToken() {

        String body = """
                {
                  "email": "forrestgump8@gmail.com",
                  "password": "Test123"
                }""";


        JsonPath jsonData = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .body(body)
                .when().post("/api/auth").jsonPath();

        String token = jsonData.getString("token");

        return token;
    }


    @Test
    public void addExperience() {

        String token = getToken();

        String postBody = """
                {
                  "title": "Jr. Full Stack SDET",
                  "company": "Ali  Baba",
                  "location": "Pekin",
                  "from": "2018-01-30",
                  "to": "2020-01-30",
                  "current": false,
                  "description": "worked as a junior SDET"
                }""";

        given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().header("x-auth-token", token)
                .body(postBody)
                .when().post("/api/profile/experience")
                .then().assertThat().statusCode(201);



    }
}
