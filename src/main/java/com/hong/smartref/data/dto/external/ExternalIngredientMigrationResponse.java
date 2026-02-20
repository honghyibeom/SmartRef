package com.hong.smartref.data.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ExternalIngredientMigrationResponse {

    private String message;
    private int count;
    private List<Item> data;

    @Getter
    @NoArgsConstructor
    public static class Item {

        private String message;

        @JsonProperty("source_id")
        private Long sourceId;

        @JsonProperty("new_id")
        private Long newId;

        @JsonProperty("moved_synonyms")
        private Integer movedSynonyms;
    }
}
