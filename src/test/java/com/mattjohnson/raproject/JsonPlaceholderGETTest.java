package com.mattjohnson.raproject;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;


public class JsonPlaceholderGETTest {

    @Test
    public void jsonPlaceholderReadAllUsers() {
        Response response = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users");

        System.out.println(response.asString());

        Assertions.assertEquals(200, response.getStatusCode());
    }

    //TODO request specification + allure filter + jenkins allure plugin
}
