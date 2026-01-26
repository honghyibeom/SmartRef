package com.hong.smartref.data.enumerate;

import lombok.Getter;

import java.util.Random;

@Getter
public enum DefaultColor {
    default_color_1("default_color_1"),
    default_color_2("default_color_2"),
    default_color_3("default_color_3"),
    default_color_4("default_color_4"),
    default_color_5("default_color_5"),
    default_color_6("default_color_6"),
    default_color_7("default_color_7"),
    default_color_8("default_color_8"),
    default_color_9("default_color_9"),
    default_color_10("default_color_10");


    private final String defaultColor;

    DefaultColor(String defaultColor) {
        this.defaultColor = defaultColor;
    }

    private static final Random RANDOM = new Random();

    public static String getRandomColor() {
        DefaultColor[] values = DefaultColor.values();
        int randomIndex = RANDOM.nextInt(values.length);
        return values[randomIndex].defaultColor;
    }
}
