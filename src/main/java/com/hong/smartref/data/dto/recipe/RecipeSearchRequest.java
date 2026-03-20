package com.hong.smartref.data.dto.recipe;

import com.hong.smartref.data.enumerate.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeSearchRequest {
    private List<IngredientsDTO> ingredients;

    private RecipeType recipeType;
    private CookingMethod cookingMethod;
    private CookingTechnique cookingTechnique;
    private DietaryGoal dietaryGoal;
    private DietaryRestriction dietaryRestriction;
    private PrimaryIngredient primaryIngredient;
    private RecipeCategory recipeCategory;
    private Occasion occasion;
    private Difficulty difficulty;
    private CookingTime cookingTime;
    private Servings servings;
    private RequiredTool requiredTool;
    private CuisineRegion cuisineRegion;
    private RecipeSource recipeSource;
    private RecipeVisibility recipeVisibility;

    private String searchValue;
}
