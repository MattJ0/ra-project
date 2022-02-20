package com.mattjohnson.raproject.trello.boards;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Disabled
public class TrelloBoardTest extends BaseTest {

    @Test
    public void createNewBoard() {
        Response response = given()
                .queryParam("name", "SecondBoard")
                .when()
                .post("https://api.trello.com/1/boards")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo("SecondBoard");

        String boardId = json.get("id");

        given()
                .when()
                .delete("https://api.trello.com/1/boards/" + boardId)
                .then()
                .statusCode(HttpStatus.SC_OK);

    }

    @Test
    public void createBoardWithEmptyName() {
        Response response = given()
                .queryParam("name", "")
                .when()
                .post("https://api.trello.com/1/boards")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .response();

    }

    @Test
    public void createBoardWithoutDefaultLists() {
        Response response = given()
                .queryParam("name", "BoardWithoutDefaultLists")
                .queryParam("defaultLists", false)
                .contentType(ContentType.JSON)
                .when()
                .post("https://api.trello.com/1/boards")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo("BoardWithoutDefaultLists");

        String boardId = json.get("id");


        Response responseGet = given()
                .pathParam("id", boardId)
                .when()
                .get("https://api.trello.com/1/boards/{id}/lists")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath jsonGet = responseGet.jsonPath();
        List<String> idList = jsonGet.getList("id");
        assertTrue(idList.isEmpty());

        given()
                .when()
                .delete("https://api.trello.com/1/boards/" + boardId)
                .then()
                .statusCode(HttpStatus.SC_OK);

    }

    @Test
    public void createBoardWithDefaultLists() {
        Response response = given()
                .queryParam("name", "BoardWithDefaultLists")
                .queryParam("defaultLists", true)
                .contentType(ContentType.JSON)
                .when()
                .post("https://api.trello.com/1/boards")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo("BoardWithDefaultLists");

        String boardId = json.get("id");

        Response responseGet = given()
                .pathParam("id", boardId)
                .when()
                .get("https://api.trello.com/1/boards/{id}/lists")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath jsonGet = responseGet.jsonPath();
        List<String> idList = jsonGet.getList("id");
        assertThat(idList).hasSize(3);

        List<String> nameList = jsonGet.getList("name");
        assertThat(nameList).hasSize(3).contains("Do zrobienia", "W trakcie", "Zrobione");

        given()
                .when()
                .delete("https://api.trello.com/1/boards/" + boardId)
                .then()
                .statusCode(HttpStatus.SC_OK);

    }


}
