package com.hong.smartref.data.enumerate;

public enum CookingTechnique implements DisplayEnum {

    NO_COOK("No-Cook"),
    ONE_PAN("One-Pan"),
    KNIFE_ONLY("Knife-Only"),
    FERMENTATION("Fermentation"),
    BAKING("Baking"),
    SOUS_VIDE("Sous-Vide"),
    PRESSURE_COOK("Pressure-Cook");

    private final String value;

    CookingTechnique(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}

