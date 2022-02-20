package com.mattjohnson.raproject.trello.boards;

import com.mattjohnson.raproject.dto.trello.Board;
import com.mattjohnson.raproject.dto.trello.TrelloList;
import com.mattjohnson.raproject.rop.trello.board.TrelloCreateBoardEndpoint;
import com.mattjohnson.raproject.rop.trello.board.TrelloDeleteBoardEndpoint;
import com.mattjohnson.raproject.rop.trello.list.TrelloCreateListEndpoint;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MoveCardBetweenListE2ETest extends BaseTest {

    private static String boardId;
    private static String firstListId;
    private static String secondListId;
    private static String cardId;

    @Test
    @Order(1)
    public void createNewBoard() {
        Board board = TrelloCreateBoardEndpoint.builder()
                .name("First Board")
                .defaultLists(false)
                .build()
                .sendRequest()
                .assertRequestSuccess()
                .getResponseModel();

        assertThat(board.getName()).isEqualTo("First Board");
        boardId = board.getId();
    }

    @Test
    @Order(2)
    public void createFirstList() {
        TrelloList trelloList = TrelloCreateListEndpoint.builder()
                .nameList("First List")
                .idBoard(boardId)
                .build()
                .sendRequest()
                .assertRequestSuccess()
                .getResponseModel();

        assertThat(trelloList.getName()).isEqualTo("First List");
        firstListId = trelloList.getId();
    }

    @Test
    @Order(3)
    public void createSecondList() {
        TrelloList trelloList = TrelloCreateListEndpoint.builder()
                .nameList("Second List")
                .idBoard(boardId)
                .build()
                .sendRequest()
                .assertRequestSuccess()
                .getResponseModel();

        assertThat(trelloList.getName()).isEqualTo("Second List");
        secondListId = trelloList.getId();
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
        TrelloDeleteBoardEndpoint.builder()
                .boardId(boardId)
                .build()
                .sendRequest()
                .assertRequestSuccess();
    }

}
