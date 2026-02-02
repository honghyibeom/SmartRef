package com.hong.smartref.data.enumerate;

public enum DietaryRestriction implements DisplayEnum {
    NONE("None"),
    VEGAN("Vegan"),
    HALAL("Halal"),
    GLUTEN_FREE("Gluten-Free"),
    LACTOSE_FREE("Lactose-Free");

    private final String value;

    DietaryRestriction(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}

