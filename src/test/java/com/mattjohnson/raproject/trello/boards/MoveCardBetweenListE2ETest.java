package com.mattjohnson.raproject.trello.boards;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MoveCardBetweenListE2ETest extends BaseTest {

    private static String boardId;
    private static String firstListId;
    private static String secondListId;
    private static String cardId;

    @Test
    @Order(1)
    public void createNewBoard() {
        Response response = given()
                .spec(reqSpec)
                .queryParam("name", "First Board")
                .queryParam("defaultLists", false)
                .when()
                .post(BASE_URL + BOARDS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo("First Board");

        boardId = json.get("id");

    }

    @Test
    @Order(2)
    public void createFirstList() {
        Response response = given()
                .spec(reqSpec)
                .queryParam("name", "First List")
                .queryParam("idBoard", boardId)
                .when()
                .post(BASE_URL + LISTS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo("First List");

        firstListId = json.getString("id");

    }

    @Test
    @Order(3)
    public void createSecondList() {
        Response response = given()
                .spec(reqSpec)
                .queryParam("name", "Second List")
                .queryParam("idBoard", boardId)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL + LISTS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo("Second List");

        secondListId = json.getString("id");

    }

    @Test
    @Order(4)
    public void addCardToFirstList() {
        Response response = given()
                .spec(reqSpec)
                .queryParam("name", "First Card")
                .queryParam("idList", firstListId)
                .when()
                .post(BASE_URL + CARDS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo("First Card");

        cardId = json.getString("id");

    }

    @Test
    @Order(5)
    public void moveCardToSecondList() {
        Response response = given()
                .spec(reqSpec)
                .queryParam("idList", secondListId)
                .pathParam("id", cardId)
                .when()
                .put(BASE_URL + CARDS + "/{id}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("idList")).isEqualTo(secondListId);

    }

    @Test
    @Order(6)
    public void deleteBoard() {
        given()
                .spec(reqSpec)
                .pathParam("idBoard", boardId)
                .when()
                .delete(BASE_URL + BOARDS + "/{idBoard}")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

}
