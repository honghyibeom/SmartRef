package com.hong.smartref.data.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ExternalAddNicknameResponse {

    private String message;
    private int count;
    private List<Item> data;

    @Getter
    @NoArgsConstructor
    public static class Item {

        private String message;
        private Nickname nickname;
    }

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
