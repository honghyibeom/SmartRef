package com.hong.smartref.data.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DietaryGoal implements DisplayEnum {
    LOW_SODIUM("low-sodium"),
    LOW_CARB("low-carb"),
    HIGH_PROTEIN("high-protein"),
    STANDARD("standard");

    private final String value;

    DietaryGoal(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static DietaryGoal from(String value) {
        for (DietaryGoal type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException(value);
    }
}
