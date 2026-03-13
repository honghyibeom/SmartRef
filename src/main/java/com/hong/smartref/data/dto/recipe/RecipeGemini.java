package com.hong.smartref.data.dto.recipe;

import lombok.Data;

import java.util.List;

@Data
public class RecipeGemini {
    private List<AiIngredientsDTO> ingredients;

    private String prompt;
    private String recipeType;
    private String cookingMethod;
    private String occasion;
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

    private List<String> primaryIngredientsToUse;

    private String deviceLang;

    private boolean isUseLocalData;
    private String stayRegion;
    private boolean useOwnedIngredientsOnly;
}
