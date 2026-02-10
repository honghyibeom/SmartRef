package com.hong.smartref.data.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExternalIngredientToNicknameResponse {

    private String message;

    private Nickname nickname;

    @JsonProperty("source_id")
    private Long sourceId;

    @JsonProperty("target_ingredient_id")
    private Long targetIngredientId;

    @JsonProperty("deleted_source")
    private Boolean deletedSource;

    @JsonProperty("deleted_source_synonyms")
    private Integer deletedSourceSynonyms;

    @Getter
    @NoArgsConstructor
    public static class Nickname {

        private Long id;

        @JsonProperty("ingredient_id")
        private Long ingredientId;

        private String synonym;

        @JsonProperty("lang_code")
        private String langCode;
    }
}

