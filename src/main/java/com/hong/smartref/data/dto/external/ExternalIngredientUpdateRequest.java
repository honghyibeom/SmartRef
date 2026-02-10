package com.hong.smartref.data.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalIngredientUpdateRequest {
    private Long id;
    private String label;

    @JsonProperty("master_name")
    private String masterName;

    private Map<String, String> names;
}
