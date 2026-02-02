package com.hong.smartref.data.enumerate;

public enum PrimaryIngredient implements DisplayEnum {
    PROCESSED("Processed"),
    DRIED_FISH("Dried Fish"),
    GRAINS("Grains"),
    FRUITS("Fruits"),
    DAIRY_EGG("Dairy/Egg"),
    CHICKEN("Chicken"),
    PORK("Pork"),
    FLOUR("Flour"),
    MUSHROOM("Mushroom"),
    BEEF("Beef"),
    RICE("Rice"),
    MEAT("Meat"),
    VEGETABLE("Vegetable"),
    BEAN_NUT("Bean/Nut"),
    SEAFOOD("Seafood");

    private final String value;

    PrimaryIngredient(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}

