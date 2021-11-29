package com.mattjohnson.raproject.jsonplaceholder.posts;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class JsonplaceholderPostsGETTest extends BaseTest {

    @Test
    public void readOnePost() {
        Response response = given()
                .pathParam("postId", "1")
                .when()
                .get(baseUrl + postsPath + "/{postId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

    }

    @Test
    public void readAllPosts() {
        Response response = given()
                .when()
                .get(baseUrl + postsPath)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        JsonPath json = response.jsonPath();
        List<String> posts = json.getList("postId");
        System.out.println(posts.size());


    }
}
