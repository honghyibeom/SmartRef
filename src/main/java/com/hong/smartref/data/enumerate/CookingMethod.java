package com.hong.smartref.data.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CookingMethod implements DisplayEnum {
    GRILL("grill"),
    BOIL("boil"),
    BLANCH("blanch"),
    MIX("mix"),
    STIR_FRY("stir-fry"),
    PAN_FRY("pan-fry"),
    TOSS("toss"),
    SIMMER("simmer"),
    PICKLED("pickled"),
    BRAISE("braise"),
    STEAM("steam"),
    DEEP_FRY("deep-fry"),
    RAW("raw");

    private final String value;

    CookingMethod(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static CookingMethod from(String value) {
        for (CookingMethod type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException(value);
    }
}

