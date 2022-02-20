package com.mattjohnson.raproject.rop.trello.card;

import com.mattjohnson.raproject.dto.trello.TrelloCard;
import com.mattjohnson.raproject.rop.BaseEndpoint;
import lombok.Builder;
import org.apache.http.HttpStatus;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;

@Builder
public class TrelloUpdateCardEndpoint extends BaseEndpoint<TrelloUpdateCardEndpoint, TrelloCard> {

    private String cardId;
    private String listId;

    @Override
    protected Type getModelType() {
        return TrelloCard.class;
    }

    @Override
    public TrelloUpdateCardEndpoint sendRequest() {
        response = given()
                .queryParam("idList", listId)
                .pathParam("id", cardId)
                .when()
                .put(trelloConfig.getCardsUri() + "/{id}");

        return this;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }
}
