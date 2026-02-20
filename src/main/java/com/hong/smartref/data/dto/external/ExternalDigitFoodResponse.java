package com.hong.smartref.data.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class ExternalDigitFoodResponse {

    private int count;

    private Integer digitNumber;

    @JsonProperty("start_id")
    private Long startId;

    @JsonProperty("end_id")
    private Long endId;

    private List<Item> data;

    @Getter
    @NoArgsConstructor
    public static class Item {

        private Long id;
        private String label;

        @JsonProperty("master_name")
        private String masterName;

        private Map<String, String> names;

        @JsonProperty("created_at")
        private LocalDateTime createdAt;
    }
}
