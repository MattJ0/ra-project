package com.mattjohnson.raproject.rop.trello;

import com.mattjohnson.raproject.dto.trello.TrelloList;
import com.mattjohnson.raproject.rop.BaseEndpoint;
import lombok.Builder;
import org.apache.http.HttpStatus;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;

@Builder
public class TrelloCreateListEndpoint extends BaseEndpoint<TrelloCreateListEndpoint, TrelloList> {

    private String idBoard;
    private String nameList;

    @Override
    protected Type getModelType() {
        return TrelloList.class;
    }

    @Override
    public TrelloCreateListEndpoint sendRequest() {
         response = given()
                .queryParam("name", nameList)
                .queryParam("idBoard", idBoard)
                .when()
                .post(trelloConfig.getListsUri());

        return this;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }

}
