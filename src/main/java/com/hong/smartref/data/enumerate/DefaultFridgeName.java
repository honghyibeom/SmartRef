package com.hong.smartref.data.enumerate;

import lombok.Getter;

import java.util.Random;

@Getter
public enum DefaultFridgeName {
    default_fridgeName_1("default_fridgeName_1"),
    default_fridgeName_2("default_fridgeName_2"),
    default_fridgeName_3("default_fridgeName_3"),
    default_fridgeName_4("default_fridgeName_4"),
    default_fridgeName_5("default_fridgeName_5"),
    default_fridgeName_6("default_fridgeName_6"),
    default_fridgeName_7("default_fridgeName_7"),
    default_fridgeName_8("default_fridgeName_8"),
    default_fridgeName_9("default_fridgeName_9"),
    default_fridgeName_10("default_fridgeName_10"),
    default_fridgeName_11("default_fridgeName_11"),
    default_fridgeName_12("default_fridgeName_12"),
    default_fridgeName_13("default_fridgeName_13"),
    default_fridgeName_14("default_fridgeName_14"),
    default_fridgeName_15("default_fridgeName_15"),
    default_fridgeName_16("default_fridgeName_16"),
    default_fridgeName_17("default_fridgeName_17"),
    default_fridgeName_18("default_fridgeName_18"),
    default_fridgeName_19("default_fridgeName_19"),
    default_fridgeName_20("default_fridgeName_20");


    private final String defaultFridgeName;

    DefaultFridgeName(String defaultFridgeName) {
        this.defaultFridgeName = defaultFridgeName;
    }

    private static final Random RANDOM = new Random();

    public static String getRandomFridgeName() {
        DefaultFridgeName[] values = DefaultFridgeName.values();
        int randomIndex = RANDOM.nextInt(values.length);
        return values[randomIndex].defaultFridgeName;
    }
}
