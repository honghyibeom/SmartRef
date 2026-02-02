package com.hong.smartref.data.enumerate;

public enum CuisineRegion implements DisplayEnum {
    KOREAN("Korean"),
    JAPANESE("Japanese"),
    CHINESE("Chinese"),
    THAI("Thai"),
    VIETNAMESE("Vietnamese"),
    FRENCH("French"),
    ITALIAN("Italian"),
    MEXICAN("Mexican"),
    INDIAN("Indian"),
    MIDDLE_EASTERN("Middle-Eastern"),
    WESTERN("Western"),
    FUSION("Fusion"),
    GLOBAL("Global");

    private final String value;

    CuisineRegion(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}

