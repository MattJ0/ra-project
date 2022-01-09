package com.mattjohnson.raproject.dto.trello;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrelloList {

    private String id;
    private String name;
    private Boolean closed;
    private Integer pos;
    private String idBoard;

}
