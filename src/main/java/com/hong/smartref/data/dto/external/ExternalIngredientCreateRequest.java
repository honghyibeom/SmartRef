package com.hong.smartref.data.dto.external;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalIngredientCreateRequest {

    private int digitNumber;
    private Food food;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Food {
        private String label;
        private String masterName;
        private Map<String, String> names;
    }
}

