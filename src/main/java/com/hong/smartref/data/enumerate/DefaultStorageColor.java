package com.hong.smartref.data.enumerate;

import lombok.Getter;

import java.util.Random;

@Getter
public enum DefaultStorageColor {
    storage_default_color_1("storage_default_color_1"),
    storage_default_color_2("storage_default_color_2"),
    storage_default_color_3("storage_default_color_3"),
    storage_default_color_4("storage_default_color_4"),
    storage_default_color_5("storage_default_color_5"),
    storage_default_color_6("storage_default_color_6"),
    storage_default_color_7("storage_default_color_7"),
    storage_default_color_8("storage_default_color_8"),
    storage_default_color_9("storage_default_color_9"),
    storage_default_color_10("storage_default_color_10");


    private final String defaultColor;

    DefaultStorageColor(String defaultColor) {
        this.defaultColor = defaultColor;
    }

    private static final Random RANDOM = new Random();

    public static String getRandomColor() {
        DefaultStorageColor[] values = DefaultStorageColor.values();
        int randomIndex = RANDOM.nextInt(values.length);
        return values[randomIndex].defaultColor;
    }
}
