package com.mattjohnson.raproject.trello.boards;

import com.mattjohnson.raproject.dto.trello.TrelloBoard;
import com.mattjohnson.raproject.dto.trello.TrelloCard;
import com.mattjohnson.raproject.dto.trello.TrelloList;
import com.mattjohnson.raproject.rop.trello.board.TrelloCreateBoardEndpoint;
import com.mattjohnson.raproject.rop.trello.board.TrelloDeleteBoardEndpoint;
import com.mattjohnson.raproject.rop.trello.card.TrelloAddCardToListEndpoint;
import com.mattjohnson.raproject.rop.trello.card.TrelloUpdateCardEndpoint;
import com.mattjohnson.raproject.rop.trello.list.TrelloCreateListEndpoint;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MoveTrelloCardBetweenListE2ETest extends BaseTest {

    private static String boardId;
    private static String firstListId;
    private static String secondListId;
    private static String cardId;

    @Test
    @Order(1)
    public void createNewBoard() {
        TrelloBoard trelloBoard = TrelloCreateBoardEndpoint.builder()
                .name("First Board")
                .defaultLists(false)
                .build()
                .sendRequest()
                .assertRequestSuccess()
                .getResponseModel();

        assertThat(trelloBoard.getName()).isEqualTo("First Board");
        boardId = trelloBoard.getId();
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
        TrelloCard trelloCard = TrelloAddCardToListEndpoint.builder()
                .name("First Card")
                .idList(firstListId)
                .build()
                .sendRequest()
                .assertRequestSuccess()
                .getResponseModel();

        assertThat(trelloCard.getName()).isEqualTo("First Card");
        assertThat(trelloCard.getIdList()).isEqualTo(firstListId);
        cardId = trelloCard.getId();
    }

    @Test
    @Order(5)
    public void moveCardToSecondList() {
        TrelloCard trelloCard = TrelloUpdateCardEndpoint.builder()
                .listId(secondListId)
                .cardId(cardId)
                .build()
                .sendRequest()
                .assertRequestSuccess()
                .getResponseModel();

        assertThat(trelloCard.getIdList()).isEqualTo(secondListId);
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
