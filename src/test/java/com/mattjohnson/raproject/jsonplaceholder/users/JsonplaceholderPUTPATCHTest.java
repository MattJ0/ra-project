package com.mattjohnson.raproject.jsonplaceholder.users;

import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class JsonplaceholderPUTPATCHTest {

    private static Faker faker;
    private String fakeEmail;
    private String fakeName;
    private String fakeUsername;
    private String fakePhone;
    private String fakeWWW;

    @BeforeAll
    public static void beforeAll() {
        faker = new Faker();
    }

    @BeforeEach
    public void beforeEach() {
        fakeEmail = faker.internet().emailAddress();
        fakeName = faker.name().fullName();
        fakeUsername = faker.name().username();
        fakePhone = faker.phoneNumber().phoneNumber();
        fakeWWW = faker.internet().url();
    }

    @Test
    public void jsonPlaceholderUpdateUserPUT() throws JSONException {
        JSONObject user = new JSONObject();
        user.put("name", fakeName);
        user.put("username", fakeUsername);
        user.put("email", fakeEmail);
        user.put("phone", fakePhone);
        user.put("website", fakeWWW);

        JSONObject address = new JSONObject();
        address.put("street", "street");
        address.put("suite", "suite");
        address.put("city", "city");
        address.put("zipcode", "zipcode");

        JSONObject geo = new JSONObject();
        geo.put("lat", "lat");
        geo.put("lng", "lng");

        JSONObject company = new JSONObject();
        company.put("name", "company");
        company.put("catchphrase", "catchphrase");
        company.put("bs", "bs");

        address.put("geo", geo);
        user.put("address", address);
        user.put("company", company);

        Response response = given()
                .contentType("application/json")
                .body(user.toString())
                .when()
                .put("https://jsonplaceholder.typicode.com/users/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals(fakeName, json.get("name"));
        assertEquals(fakeEmail, json.get("email"));
        assertEquals(fakeUsername, json.get("username"));
        assertEquals("company", json.get("company.name"));
        assertEquals("city", json.get("address.city"));

    }

    @Test
    public void jsonPlaceholderUpdateUserPATCH() throws JSONException {
        JSONObject geo = new JSONObject();
        geo.put("lat", "UPDATED_LAT_PATCH");
        geo.put("lng", "UPDATED_LNG_PATCH");
        JSONObject address = new JSONObject();
        address.put("geo", geo);
        JSONObject user = new JSONObject();
        user.put("name", "UPDATED_NAME_PATCH");
        user.put("address", address);

        Response response = given()
                .contentType("application/json")
                .body(user.toString())
                .when()
                .patch("https://jsonplaceholder.typicode.com/users/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals("UPDATED_NAME_PATCH", json.get("name"));
    }
}
