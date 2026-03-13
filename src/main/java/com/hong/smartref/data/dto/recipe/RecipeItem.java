package com.hong.smartref.data.dto.recipe;

import lombok.Data;

import java.util.List;

@Data
public class RecipeItem {
    private String title;
    private String recipeType;
    private String occasion;
    private String cookingMethod;
    private String dietaryGoal;
    private String dietaryRestriction;
    private String primaryIngredient;
    private String category;
    private String technique;
    private String difficulty;
    private String cookingTime;
    private String cuisineRegion;
    private String servings;
    private String requiredTool;
    private String writtenLang;

    private List<IngredientsDTO> ingredients;
    private List<IngredientsDTO> mIngredients;
    private List<WayDTO> steps;

    private String stayRegion;
    private boolean isUseLocalData;
    private String source;
    private String visibility;
}
