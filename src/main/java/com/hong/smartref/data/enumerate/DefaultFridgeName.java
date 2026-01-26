package com.hong.smartref.data.enumerate;

import lombok.Getter;

import java.util.Random;

@Getter
public enum DefaultUserName {
    default_username_1("default_username_1"),
    default_username_2("default_username_2"),
    default_username_3("default_username_3"),
    default_username_4("default_username_4"),
    default_username_5("default_username_5"),
    default_username_6("default_username_6"),
    default_username_7("default_username_7"),
    default_username_8("default_username_8"),
    default_username_9("default_username_9"),
    default_username_10("default_username_10"),
    default_username_11("default_username_11"),
    default_username_12("default_username_12"),
    default_username_13("default_username_13"),
    default_username_14("default_username_14"),
    default_username_15("default_username_15"),
    default_username_16("default_username_16"),
    default_username_17("default_username_17"),
    default_username_18("default_username_18"),
    default_username_19("default_username_19"),
    default_username_20("default_username_20");


    private final String defaultUserName;

    DefaultUserName(String defaultUserName) {
        this.defaultUserName = defaultUserName;
    }

    private static final Random RANDOM = new Random();

    public static String getRandomBackGroundImage() {
        DefaultUserName[] values = DefaultUserName.values();
        int randomIndex = RANDOM.nextInt(values.length);
        return values[randomIndex].defaultUserName;
    }
}
