package com.hong.smartref.data.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Servings implements DisplayEnum {
    ONE("1"),
    TWO("2"),
    THREE_FOUR("3-4"),
    FIVE_PLUS("5+");

    private final String value;

    Servings(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static Servings from(String value) {
        for (Servings type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException(value);
    }
}