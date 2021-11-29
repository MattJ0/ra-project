package com.mattjohnson.raproject.jsonplaceholder.users;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class JsonPlaceholderGETTwoTest {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private final String USERS = "/users";

    @Test
    public void jsonPlaceholderReadAllUsers() {
        Response response = given()
                .when()
                .get(BASE_URL + USERS )
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        List<String> names = json.getList("name");
        assertEquals(10, names.size());
    }

    @Test
    public void jsonPlaceholderReadAllUsersWithoutPlEmail() {
        Response response = given()
                .when()
                .get(BASE_URL + USERS )
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        List<String> emails = json.getList("email");
        System.out.println(emails);
        assertTrue(emails.stream().noneMatch(email -> email.endsWith(".pl")), "Nie ma żadnego użytkownika, którego adres email kończy się na .pl");

    }

    @Test
    public void jsonPlaceholderReadOneUser() {
        given()
                .when()
                .get(BASE_URL + USERS + "/1")
                .then()
                .statusCode(200)
                .body("name", equalTo("Leanne Graham"))
                .body("username", equalTo("Bret"))
                .body("email", equalTo("Sincere@april.biz"))
                .body("address.street", equalTo("Kulas Light"));

//        JsonPath json = response.jsonPath();
//
//        Assertions.assertEquals(200, response.getStatusCode());
//        Assertions.assertEquals("Leanne Graham", json.get("name"));
//        Assertions.assertEquals("Bret", json.get("username"));
//        Assertions.assertEquals("Sincere@april.biz", json.get("email"));
//        Assertions.assertEquals("Kulas Light", json.get("address.street"));
    }

    @Test
    public void jsonPlaceholderReadOneUserWithPathVariable() {
        Response response = given()
                .pathParam("userId", 1)
                .when()
                .get(BASE_URL + USERS + "/{userId}")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals("Leanne Graham", json.get("name"));
        assertEquals("Bret", json.get("username"));
        assertEquals("Sincere@april.biz", json.get("email"));
        assertEquals("Kulas Light", json.get("address.street"));

    }

    @Test
    public void jsonPlaceHolderReadUsersWithQueryParams() {
        Response response = given()
                .queryParam("username", "Bret")
                .when()
                .get(BASE_URL + USERS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals("Leanne Graham", json.getList("name").get(0));
        assertEquals("Bret", json.getList("username").get(0));
        assertEquals("Sincere@april.biz", json.getList("email").get(0));
        assertEquals("Kulas Light", json.getList("address.street").get(0));

    }


}
