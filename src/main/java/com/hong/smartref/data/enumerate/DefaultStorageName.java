package com.hong.smartref.data.enumerate;

import lombok.Getter;

import java.util.Random;

@Getter
public enum DefaultStorageName {
    storage_default_name_1("storage_default_name_1"),
    storage_default_name_2("storage_default_name_2"),
    storage_default_name_3("storage_default_name_3"),
    storage_default_name_4("storage_default_name_4"),
    storage_default_name_5("storage_default_name_5"),
    storage_default_name_6("storage_default_name_6"),
    storage_default_name_7("storage_default_name_7"),
    storage_default_name_8("storage_default_name_8"),
    storage_default_name_9("storage_default_name_9"),
    storage_default_name_10("storage_default_name_10"),
    storage_default_name_11("storage_default_name_11"),
    storage_default_name_12("storage_default_name_12"),
    storage_default_name_13("storage_default_name_13"),
    storage_default_name_14("storage_default_name_14"),
    storage_default_name_15("storage_default_name_15"),
    storage_default_name_16("storage_default_name_16"),
    storage_default_name_17("storage_default_name_17"),
    storage_default_name_18("storage_default_name_18"),
    storage_default_name_19("storage_default_name_19"),
    storage_default_name_20("storage_default_name_20");


    private final String defaultStorageName;

    DefaultStorageName(String defaultStorageName) {
        this.defaultStorageName = defaultStorageName;
    }

    private static final Random RANDOM = new Random();

    public static String getRandomStorageName() {
        DefaultStorageName[] values = DefaultStorageName.values();
        int randomIndex = RANDOM.nextInt(values.length);
        return values[randomIndex].defaultStorageName;
    }
}
