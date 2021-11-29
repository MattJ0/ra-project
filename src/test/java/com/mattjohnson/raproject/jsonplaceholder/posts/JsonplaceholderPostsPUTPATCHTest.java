package com.mattjohnson.raproject.jsonplaceholder.posts;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonplaceholderPostsPUTPATCHTest extends BaseTest {

    @Test
    public void updatePostPUT() throws JSONException {
        String postId = "1";
        String userId = "1";
        String updatedTitle = faker.lorem().sentence(10);
        String updatedBody = faker.lorem().sentence(30);

        JSONObject post = new JSONObject();
        post.put("userId", userId);
        post.put("title", updatedTitle);
        post.put("body", updatedBody);

        Response response = given()
                .contentType("application/json")
                .pathParam("postId", postId)
                .body(post.toString())
                .when()
                .put(baseUrl + postsPath + "/{postId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        JsonPath json = response.jsonPath();

        assertEquals(json.get("userId"), userId);
        assertEquals(json.get("title"), updatedTitle);
        assertEquals(json.get("body"), updatedBody);

    }

    @Test
    public void updatePostPATCH() throws JSONException {
        String postId = "1";
        String userId = "1";
        String updatedTitle = faker.lorem().sentence(10);

        JSONObject post = new JSONObject();
        post.put("userId", userId);
        post.put("title", updatedTitle);

        Response response = given()
                .contentType("application/json")
                .pathParam("postId", postId)
                .body(post.toString())
                .when()
                .patch(baseUrl + postsPath + "/{postId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        JsonPath json = response.jsonPath();

        assertEquals(json.get("userId"), userId);
        assertEquals(json.get("title"), updatedTitle);
    }


}
