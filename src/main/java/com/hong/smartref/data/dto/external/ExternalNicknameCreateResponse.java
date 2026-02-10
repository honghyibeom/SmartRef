package com.hong.smartref.data.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExternalNicknameCreateResponse {

    private String message;
    private Nickname nickname;

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

