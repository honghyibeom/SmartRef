package com.hong.smartref.data.enumerate;

public enum Servings implements DisplayEnum {
    ONE("1"),
    TWO("2"),
    THREE_FOUR("3-4"),
    FIVE_PLUS("5+");

    private final String value;

    Servings(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}