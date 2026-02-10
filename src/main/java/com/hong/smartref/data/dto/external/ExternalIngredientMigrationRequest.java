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
public class ExternalIngredientMigrationRequest {

    @JsonProperty("source_id")
    private Long sourceId;

    @JsonProperty("target_digit_number")
    private Integer targetDigitNumber;
}

