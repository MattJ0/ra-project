package com.mattjohnson.raproject.jsonplaceholder.posts;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class JsonplaceholderPostsDELETETest extends BaseTest {

    @Test
    public void deletePost() {
        given()
                .when()
                .delete(baseUrl + postsPath + "/1")
                .then()
                .statusCode(HttpStatus.SC_OK);

    }
}
