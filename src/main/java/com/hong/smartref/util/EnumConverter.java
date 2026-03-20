package com.hong.smartref.util;

public class EnumConverter {
    public static <T extends Enum<T>> T convert(Class<T> enumClass, String value) {
        if (value == null || value.isBlank()) return null;

        try {
            return Enum.valueOf(enumClass, value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid enum value: " + value);
        }
    }
}
