package com.hong.smartref.data.dto.recipe;

import com.hong.smartref.data.enumerate.*;
import lombok.*;

import java.beans.Visibility;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class RecipeRequest {

    private String title;
    private RecipeType recipeType;
    private Occasion occasion;
    private CookingMethod cookingMethod;
    private DietaryGoal dietaryGoal;
    private DietaryRestriction dietaryRestriction;
    private PrimaryIngredient primaryIngredient;
    private RecipeCategory category;
    private CookingTechnique technique;
    private Difficulty difficulty;
    private CookingTime cookingTime;
    private CuisineRegion cuisineRegion;
    private Servings servings;
    private RequiredTool requiredTool;

    private List<String> ingredients;
    private List<String> steps;

    private RecipeSource source;   // AI / USER
}

