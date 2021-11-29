package com.mattjohnson.raproject.jsonplaceholder.users;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;


public class JsonPlaceholderGETTest {

    @Test
    public void jsonPlaceholderReadAllUsers() {
        Response response = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users");

        JsonPath json = response.jsonPath();
        List<String> names = json.getList("name");

        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertEquals(10, names.size());
    }

    @Test
    public void jsonPlaceholderReadOneUser() {
        Response response = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users/1");

        JsonPath json = response.jsonPath();

        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertEquals("Leanne Graham", json.get("name"));
        Assertions.assertEquals("Bret", json.get("username"));
        Assertions.assertEquals("Sincere@april.biz", json.get("email"));
        Assertions.assertEquals("Kulas Light", json.get("address.street"));
    }

    @Test
    public void jsonPlaceholderReadOneUserWithPathVariable() {
        Response response = given()
                .pathParam("userId", 1)
                .when()
                .get("https://jsonplaceholder.typicode.com/users/{userId}");

        JsonPath json = response.jsonPath();

        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertEquals("Leanne Graham", json.get("name"));
        Assertions.assertEquals("Bret", json.get("username"));
        Assertions.assertEquals("Sincere@april.biz", json.get("email"));
        Assertions.assertEquals("Kulas Light", json.get("address.street"));

    }

    @Test
    public void jsonPlaceHolderReadUsersWithQueryParams() {
        Response response = given()
                .queryParam("username", "Bret")
                .when()
                .get("https://jsonplaceholder.typicode.com/users");

        JsonPath json = response.jsonPath();

        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertEquals("Leanne Graham", json.getList("name").get(0));
        Assertions.assertEquals("Bret", json.getList("username").get(0));
        Assertions.assertEquals("Sincere@april.biz", json.getList("email").get(0));
        Assertions.assertEquals("Kulas Light", json.getList("address.street").get(0));

    }


}
