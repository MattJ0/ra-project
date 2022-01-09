package com.mattjohnson.raproject.rop;

import com.mattjohnson.raproject.config.TrelloConfig;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Type;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 *Base class for Request Object Pattern
 *
 * @param <E> endpoint class e.g. CreateListEndpoint
 * @param <M> model(dto) class for response
 */
public abstract class BaseEndpoint<E, M> {

    public TrelloConfig trelloConfig;

    protected Response response;

    protected abstract Type getModelType();

    public abstract E sendRequest();

    protected abstract int getSuccessStatusCode();

    public M getResponseModel() { return response.as(getModelType()); }

    public E assertRequestSuccess() { return assertStatusCode(getSuccessStatusCode()); }

    @SuppressWarnings("unchecked")
    public E assertStatusCode(int statusCode) {
        assertThat(response.getStatusCode(), is(equalTo(statusCode)));
        return (E) this;
    }

    @Autowired
    public final void setTrelloConfig(TrelloConfig trelloConfig) {
        this.trelloConfig = trelloConfig;
    }
}
