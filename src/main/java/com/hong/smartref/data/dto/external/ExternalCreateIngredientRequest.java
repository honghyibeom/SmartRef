package com.hong.smartref.data.dto.external;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class ExternalCreateIngredientRequest {

    private Integer digitNumber;
    private Food food;

    @Getter
    @NoArgsConstructor
    public static class Food {

        private String label;
        private String masterName;
        private Map<String, String> names;
    }
}
