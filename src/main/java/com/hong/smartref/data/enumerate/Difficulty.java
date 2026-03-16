package com.hong.smartref.data.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Difficulty implements DisplayEnum {
    EASY("easy"),
    BEGINNER("beginner"),
    INTERMEDIATE("intermediate"),
    ADVANCED("advanced"),
    MASTER("master");

    private final String value;

    Difficulty(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static Difficulty from(String value) {
        for (Difficulty type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException(value);
    }
}

