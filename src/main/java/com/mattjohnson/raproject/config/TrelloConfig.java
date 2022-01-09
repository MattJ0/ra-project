package com.mattjohnson.raproject.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "trello")
@Setter
@Getter
public class TrelloConfig {

    private String key;
    private String token;

    @Value("${trello.path.base}")
    private String baseUri;

    @Value("${trello.path.lists}")
    private String listsUri;

}
