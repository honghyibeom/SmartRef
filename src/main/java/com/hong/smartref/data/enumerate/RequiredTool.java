package com.hong.smartref.data.enumerate;

public enum RequiredTool implements DisplayEnum {
    PAN("Pan"),
    OVEN("Oven"),
    AIR_FRYER("Air-Fryer"),
    MICROWAVE("Microwave"),
    RICE_COOKER("Rice-Cooker"),
    SLOW_COOKER("Slow-Cooker"),
    BLENDER("Blender"),
    NO_TOOL("No-Tool");

    private final String value;

    RequiredTool(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}

