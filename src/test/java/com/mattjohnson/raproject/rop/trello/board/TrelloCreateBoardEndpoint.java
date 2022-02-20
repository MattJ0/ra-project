package com.mattjohnson.raproject.rop.trello.board;

import com.mattjohnson.raproject.dto.trello.TrelloBoard;
import com.mattjohnson.raproject.rop.BaseEndpoint;
import lombok.Builder;
import org.apache.http.HttpStatus;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;

@Builder
public class TrelloCreateBoardEndpoint extends BaseEndpoint<TrelloCreateBoardEndpoint, TrelloBoard> {

    private String name;
    private Boolean defaultLists;

    @Override
    protected Type getModelType() {
        return TrelloBoard.class;
    }

    @Override
    public TrelloCreateBoardEndpoint sendRequest() {
        response = given()
                .queryParam("name", name)
                .queryParam("defaultsLists", defaultLists)
                .when()
                .post(trelloConfig.getBoardsUri());
        return this;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }
}
