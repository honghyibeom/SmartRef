package com.hong.smartref.data.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RequiredTool implements DisplayEnum {

    PAN("pan"),
    OVEN("oven"),
    AIR_FRYER("air-fryer"),
    MICROWAVE("microwave"),
    RICE_COOKER("rice-cooker"),
    SLOW_COOKER("slow-cooker"),
    BLENDER("blender"),
    NO_TOOL("no-tool");

    private final String value;

    RequiredTool(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static RequiredTool from(String value) {
        for (RequiredTool type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown RequiredTool: " + value);
    }
}

