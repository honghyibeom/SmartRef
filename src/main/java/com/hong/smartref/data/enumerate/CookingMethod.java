package com.hong.smartref.data.enumerate;

public enum CookingMethod implements DisplayEnum {
    GRILL("Grill"),
    BOIL("Boil"),
    BLANCH("Blanch"),
    MIX("Mix"),
    STIR_FRY("Stir-fry"),
    PAN_FRY("Pan-fry"),
    TOSS("Toss"),
    SIMMER("Simmer"),
    PICKLED("Pickled"),
    BRAISE("Braise"),
    STEAM("Steam"),
    DEEP_FRY("Deep-fry"),
    RAW("Raw");

    private final String value;

    CookingMethod(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}

