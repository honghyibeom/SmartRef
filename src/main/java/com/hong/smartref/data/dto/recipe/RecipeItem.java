package com.hong.smartref.data.dto.recipe;

import lombok.Data;

import java.util.List;

@Data
public class RecipeItem {
    private String title;
    private String recipeType;
    private String cooking_method;
    private String occasion;
    private String dietary_goal;
    private String dietary_restriction;
    private String primary_ingredient;
    private String category;
    private String technique;
    private String difficulty;
    private String cooking_time;
    private String cuisine_region;
    private String servings;
    private String required_tool;
    private List<String> ingredients;
    private List<String> steps;
    private String ai;
    private String visibility;
    private String stay_region;
    private boolean isUseLocalData;
}
