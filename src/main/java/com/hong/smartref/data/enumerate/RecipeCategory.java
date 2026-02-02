package com.hong.smartref.data.enumerate;

public enum RecipeCategory implements DisplayEnum {
    MAIN_DISH("Main Dish"),
    SIDE_DISH("Side Dish"),
    SOUP_STEW("Soup/Stew"),
    RICE_PORRIDGE("Rice/Porridge"),
    NOODLE_DUMPLING("Noodle/Dumpling"),
    SNACK_COOKIE("Snack/Cookie"),
    DESSERT("Dessert"),
    BREAD("Bread"),
    SALAD("Salad"),
    SOUP("Soup"),
    SAUCE_JAM("Sauce/Jam"),
    KIMCHI_PASTE("Kimchi/Paste"),
    WESTERN("Western"),
    STEW("Stew"),
    DRINK_ALCOHOL("Drink/Alcohol"),
    FUSION("Fusion"),
    ETC("Etc");

    private final String value;

    RecipeCategory(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}

