package com.hong.smartref.data.enumerate;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum UpdateImageType implements DisplayEnum {
    KEEP("keep"),
    UPDATE("update"),
    DELETE("delete");

    private final String value;

    UpdateImageType(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static UpdateImageType from(String value) {
        for (UpdateImageType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown RequiredTool: " + value);
    }
}
