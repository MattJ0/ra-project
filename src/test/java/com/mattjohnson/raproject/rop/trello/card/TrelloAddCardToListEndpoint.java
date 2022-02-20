package com.mattjohnson.raproject.rop.trello.card;

import com.mattjohnson.raproject.dto.trello.TrelloCard;
import com.mattjohnson.raproject.rop.BaseEndpoint;
import lombok.Builder;
import org.apache.http.HttpStatus;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;

@Builder
public class TrelloAddCardToListEndpoint extends BaseEndpoint<TrelloAddCardToListEndpoint, TrelloCard> {

    private String id;
    private String name;
    private String idList;

    @Override
    protected Type getModelType() {
        return TrelloCard.class;
    }

    @Override
    public TrelloAddCardToListEndpoint sendRequest() {
        response = given()
                .queryParam("name", name)
                .queryParam("idList", idList)
                .when()
                .post(trelloConfig.getCardsUri());

        return this;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }
}
