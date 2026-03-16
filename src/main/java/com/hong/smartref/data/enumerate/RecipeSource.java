package com.hong.smartref.data.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RecipeSource implements DisplayEnum {
    AI("ai"),
    USER("user");

    private final String value;

    RecipeSource(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static RecipeSource from(String value) {
        for (RecipeSource type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown RecipeSource: " + value);
    }
}
