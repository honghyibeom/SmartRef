package com.hong.smartref.data.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CookingTechnique implements DisplayEnum {

    NO_COOK("no-cook"),
    ONE_PAN("one-pan"),
    KNIFE_ONLY("knife-only"),
    FERMENTATION("fermentation"),
    BAKING("baking"),
    SOUS_VIDE("sous-vide"),
    PRESSURE_COOK("pressure-cook");

    private final String value;

    CookingTechnique(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static CookingTechnique from(String value) {
        for (CookingTechnique type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException(value);
    }
}

