package com.hong.smartref.data.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@NoArgsConstructor
public class ExternalIngredientMasterResponse {

    private Long id;
    private String label;

    @JsonProperty("master_name")
    private String masterName;

    private Map<String, String> names;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}

