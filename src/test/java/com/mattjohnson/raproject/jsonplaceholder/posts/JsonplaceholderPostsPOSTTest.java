package com.mattjohnson.raproject.jsonplaceholder.posts;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonplaceholderPostsPOSTTest extends BaseTest {

    @Test
    public void createNewPost() throws JSONException {
        String userId = "1";
        String title = faker.lorem().sentence(10);
        String body = faker.lorem().sentence(30);

        JSONObject post = new JSONObject();
        post.put("userId", userId);
        post.put("title", title);
        post.put("body", body);

        Response response = given()
                .contentType("application/json")
                .body(post.toString())
                .when()
                .post(baseUrl + postsPath)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().response();

        JsonPath json = response.jsonPath();

        assertEquals(json.get("userId"), userId);
        assertEquals(json.get("title"), title);
        assertEquals(json.get("body"), body);
    }
}
