package com.hong.smartref.data.enumerate;

public enum RecipeType implements DisplayEnum {
    EVERYDAY("Everyday"),
    QUICK("Quick"),
    HEALTHY("Healthy"),
    LEFTOVER("Leftover"),
    PREMIUM("Premium"),
    SNACK("Snack"),
    BABY("Baby"),
    DRINK("Drink");

    private final String value;

    RecipeType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}

