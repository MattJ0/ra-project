package com.mattjohnson.raproject.trello.boards;

import com.mattjohnson.raproject.config.TrelloConfig;
import com.mattjohnson.raproject.configuration.RequestConfigurationBuilder;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {
    protected static final String BASE_URL = "https://api.trello.com/1";
    protected static final String BOARDS = "/boards";
    protected static final String LISTS = "/lists";
    protected static final String CARDS = "/cards";

    @Autowired private TrelloConfig config;

    protected static RequestSpecBuilder reqBuilder;
    protected static RequestSpecification reqSpec;


    @BeforeAll
    public void beforeAll() {
        String key = config.getKey();
        String token = config.getToken();

        reqBuilder = new RequestSpecBuilder();
        reqBuilder.addQueryParam("key", key);
        reqBuilder.addQueryParam("token", token);
        reqBuilder.setContentType(ContentType.JSON);
        reqBuilder.addFilter(new RequestLoggingFilter());
        reqBuilder.addFilter(new ResponseLoggingFilter());
        reqBuilder.addFilter(new AllureRestAssured());

        reqSpec = reqBuilder.build();
    }

    @BeforeAll
    public void beforeAll2() {
        RestAssured.baseURI = config.getBaseUri();
        RestAssured.requestSpecification = RequestConfigurationBuilder.getDefaultRequestSpecification();
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.requestSpecification.queryParam("key", config.getKey());
        RestAssured.requestSpecification.queryParam("token", config.getToken());

    }
}
