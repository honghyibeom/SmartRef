package com.hong.smartref.data.enumerate;

public enum CookingTime implements DisplayEnum {
    MIN_5("5min"),
    MIN_10("10min"),
    MIN_20("20min"),
    MIN_30("30min"),
    MIN_45("45min"),
    HOUR_1_PLUS("1h+");

    private final String value;

    CookingTime(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}

