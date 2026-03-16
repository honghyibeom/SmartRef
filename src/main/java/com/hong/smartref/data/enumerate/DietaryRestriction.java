package com.hong.smartref.data.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DietaryRestriction implements DisplayEnum {
    VEGAN("vegan"),
    HALAL("halal"),
    GLUTEN_FREE("gluten-free"),
    LACTOSE_FREE("lactose-free"),
    NONE("none");

    private final String value;

    DietaryRestriction(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static DietaryRestriction from(String value) {
        for (DietaryRestriction type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException(value);
    }
}

