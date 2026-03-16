package com.hong.smartref.data.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum Occasion implements DisplayEnum {

    SNACK("snack"),
    DIET("diet"),
    LUNCHBOX("lunchbox"),
    HOLIDAY("holiday"),
    GUEST("guest"),
    SIDE_DISH_ALCOHOL("side-dish-alcohol"),
    LATE_NIGHT("late-night"),
    BABY("baby"),
    EVERYDAY("everyday"),
    QUICK("quick"),
    HANGOVER("hangover");

    private final String value;

    Occasion(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static Occasion from(String value) {
        for (Occasion type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException(value);
    }
}

