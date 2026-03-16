package com.hong.smartref.data.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RecipeVisibility implements DisplayEnum {
    PRIVATE("private"),
    SHARED("shared"),
    PUBLIC("public");

    private final String value;

    private RecipeVisibility(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static RecipeVisibility from(String value) {
        for (RecipeVisibility type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown RecipeVisibility: " + value);
    }
}
