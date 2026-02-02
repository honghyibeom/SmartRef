package com.hong.smartref.data.enumerate;

import lombok.Getter;

public enum Occasion implements DisplayEnum {
    EVERYDAY("Everyday"),
    QUICK("Quick"),
    SNACK("Snack"),
    DIET("Diet"),
    LUNCHBOX("Lunchbox"),
    HOLIDAY("Holiday"),
    GUEST("Guest"),
    SIDE_DISH_ALCOHOL("Side Dish (Alcohol)"),
    LATE_NIGHT("Late-night"),
    BABY("Baby"),
    HANGOVER("Hangover");

    private final String value;

    Occasion(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}

