package com.hong.smartref.data.enumerate;

public enum Difficulty implements DisplayEnum {
    EASY("Easy"),
    BEGINNER("Beginner"),
    INTERMEDIATE("Intermediate"),
    ADVANCED("Advanced"),
    MASTER("Master");

    private final String value;

    Difficulty(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}

