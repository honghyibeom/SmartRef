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
public class ExternalNicknameCreateRequest {

    @JsonProperty("ingredient_id")
    private Long ingredientId;

    private String synonym;

    @JsonProperty("lang_code")
    private String langCode;
}

