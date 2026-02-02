package com.hong.smartref.data.enumerate;

public enum DietaryGoal implements DisplayEnum {
    STANDARD("Standard"),
    LOW_SODIUM("Low-Sodium"),
    LOW_CARB("Low-Carb"),
    HIGH_PROTEIN("High-Protein");

    private final String value;

    DietaryGoal(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
