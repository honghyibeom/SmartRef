package com.hong.smartref.data.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PrimaryIngredient implements DisplayEnum {
    PROCESSED("processed"),
    DRIED_FISH("dried-fish"),
    GRAINS("grains"),
    FRUITS("fruits"),
    DAIRY_EGG("dairy-egg"),
    CHICKEN("chicken"),
    PORK("pork"),
    FLOUR("flour"),
    MUSHROOM("mushroom"),
    BEEF("beef"),
    RICE("rice"),
    MEAT("meat"),
    VEGETABLE("vegetable"),
    BEAN_NUT("bean-nut"),
    SEAFOOD("seafood");

    private final String value;

    PrimaryIngredient(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static PrimaryIngredient from(String value) {
        for (PrimaryIngredient type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException(value);
    }
}

