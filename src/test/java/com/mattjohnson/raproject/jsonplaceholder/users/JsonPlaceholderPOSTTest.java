package com.mattjohnson.raproject.jsonplaceholder.users;

import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class JsonPlaceholderPOSTTest {

    @Test
    public void jsonPlaceholderCreateNewUser() throws JSONException {
        JSONObject user = new JSONObject();
        JSONObject address = new JSONObject();
        JSONObject geo = new JSONObject();
        JSONObject company = new JSONObject();

        user.put("name", "name");
        user.put("username", "username");
        user.put("email", "email@mail.pl");
        user.put("phone", "48000000000");
        user.put("website", "website.pl");

        address.put("street", "street");
        address.put("suite", "suite");
        address.put("city", "city");
        address.put("zipcode", "zipcode");

        geo.put("lat", "lat");
        geo.put("lng", "lng");

        company.put("name", "company");
        company.put("catchphrase", "catchphrase");
        company.put("bs", "bs");

        address.put("geo", geo);
        user.put("address", address);
        user.put("company", company);

        given()
                .contentType("application/json")
                .body(user.toString())
                .when()
                .post("https://jsonplaceholder.typicode.com/users")
                .then()
                .statusCode(201);

    }
}
