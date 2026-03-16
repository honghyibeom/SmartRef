package com.hong.smartref.data.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CuisineRegion implements DisplayEnum {
    KOREAN("korean"),
    JAPANESE("japanese"),
    CHINESE("chinese"),
    THAI("thai"),
    VIETNAMESE("vietnamese"),
    FRENCH("french"),
    ITALIAN("italian"),
    MEXICAN("mexican"),
    INDIAN("indian"),
    MIDDLE_EASTERN("middle-eastern"),
    WESTERN("western"),
    FUSION("fusion"),
    GLOBAL("global");

    private final String value;

    CuisineRegion(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static CuisineRegion from(String value) {
        for (CuisineRegion type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException(value);
    }
}

