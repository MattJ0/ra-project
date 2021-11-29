package com.mattjohnson.raproject.jsonplaceholder.posts;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BaseTest {
    protected static Faker faker;
    protected static String baseUrl;
    protected static String postsPath;

    @BeforeAll
    public static void beforeAll() {
        faker = new Faker();
        baseUrl = "https://jsonplaceholder.typicode.com";
        postsPath = "/posts";
    }

}
