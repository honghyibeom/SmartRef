package com.hong.smartref.data.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class ExternalCreateIngredientResponse {

    private String message;
    private int count;
    private List<Item> data;

    @Getter
    @NoArgsConstructor
    public static class Item {

        private String message;
        private Ingredient ingredient;
    }

    @Getter
    @NoArgsConstructor
    public static class Ingredient {

        private Long id;
        private String label;

        @JsonProperty("master_name")
        private String masterName;

        private Map<String, String> names;

        @JsonProperty("created_at")
        private LocalDateTime createdAt;
    }
}
