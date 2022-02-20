package com.mattjohnson.raproject.rop.trello.board;

import com.mattjohnson.raproject.rop.BaseEndpoint;
import io.restassured.response.Response;
import lombok.Builder;
import org.apache.http.HttpStatus;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;

@Builder
public class TrelloDeleteBoardEndpoint extends BaseEndpoint<TrelloDeleteBoardEndpoint, Response> {

    private String boardId;

    @Override
    protected Type getModelType() {
        return Response.class;
    }

    @Override
    public TrelloDeleteBoardEndpoint sendRequest() {
        response = given()
                .queryParam("idBoard", boardId)
                .when()
                .delete(trelloConfig.getBoardsUri());
        return this;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }
}
