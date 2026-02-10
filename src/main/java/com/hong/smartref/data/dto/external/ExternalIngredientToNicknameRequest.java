package com.hong.smartref.data.dto.external;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalIngredientToNicknameRequest {

    @JsonProperty("source_id")
    private Long sourceId;

    @JsonProperty("ingredient_id")
    private Long ingredientId;

    @JsonProperty("lang_code")
    private String langCode;

    @JsonProperty("delete_source")
    private Boolean deleteSource;
}

