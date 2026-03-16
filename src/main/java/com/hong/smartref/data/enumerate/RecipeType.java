package com.hong.smartref.data.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RecipeType implements DisplayEnum {

    EVERYDAY("everyday"),
    QUICK("quick"),
    HEALTHY("healthy"),
    LEFTOVER("leftover"),
    PREMIUM("premium"),
    SNACK("snack"),
    BABY("baby"),
    DRINK("drink");

    private final String value;

    RecipeType(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static RecipeType from(String value) {
        for (RecipeType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown RecipeType: " + value);
    }
}

