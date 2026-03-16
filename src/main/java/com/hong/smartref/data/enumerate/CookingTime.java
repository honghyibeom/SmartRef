package com.hong.smartref.data.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CookingTime implements DisplayEnum {
    MIN_5("5min"),
    MIN_10("10min"),
    MIN_20("20min"),
    MIN_30("30min"),
    MIN_45("45min"),
    OVER_1H("1h+");

    private final String value;

    CookingTime(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static CookingTime from(String value) {
        for (CookingTime type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException(value);
    }
}

