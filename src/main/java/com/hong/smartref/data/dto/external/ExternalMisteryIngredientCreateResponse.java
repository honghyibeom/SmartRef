package com.hong.smartref.data.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class ExternalMisteryIngredientCreateResponse {

    private String message;
    private Integer count;
    private List<DataItem> data;

    @Getter
    @NoArgsConstructor
    public static class DataItem {

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
