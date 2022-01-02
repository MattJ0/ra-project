package com.mattjohnson.raproject;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "trello")
@Setter
@Getter
public class TrelloConfig {

    private String key;
    private String token;

}
