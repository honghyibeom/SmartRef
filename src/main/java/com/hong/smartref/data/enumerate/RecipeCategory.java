package com.hong.smartref.data.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RecipeCategory implements DisplayEnum {
    SNACK_COOKIE("snack-cookie"),
    SOUP_STEW("soup-stew"),
    ETC("etc"),
    KIMCHI_PASTE("kimchi-paste"),
    DESSERT("dessert"),
    MAIN_DISH("main-dish"),
    NOODLE_DUMPLING("noodle-dumpling"),
    SIDE_DISH("side-dish"),
    RICE_PORRIDGE("rice-porridge"),
    BREAD("bread"),
    SALAD("salad"),
    SOUP("soup"),
    SAUCE_JAM("sauce-jam"),
    WESTERN("western"),
    STEW("stew"),
    DRINK_ALCOHOL("drink-alcohol"),
    FUSION("fusion");

    private final String value;

    RecipeCategory(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static RecipeCategory from(String value) {
        for (RecipeCategory type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException(value);
    }
}

