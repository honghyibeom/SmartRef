package com.hong.smartref.data.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AmountType implements DisplayEnum {
    QUANTITY("quantity"), // 사과 몇 개
    AMOUNT("amount"), // (1/5) 이런식으로 비가 있음
    METHOD("method"); // 300g

    private final String value;


    AmountType(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static AmountType from(String value) {
        for (AmountType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException(value);
    }
}
