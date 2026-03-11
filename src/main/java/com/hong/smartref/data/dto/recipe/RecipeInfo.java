package com.hong.smartref.data.dto.recipe;

import com.hong.smartref.data.enumerate.*;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class RecipeInfo {

    private String title;
    private RecipeType recipeType;
    private String writtenLang;
    private RecipeCategory category;
    private CookingMethod cookingMethod;
    private CookingTechnique technique;
    private DietaryGoal dietaryGoal;
    private DietaryRestriction dietaryRestriction;
    private PrimaryIngredient primaryIngredient;
    private Occasion occasion;
    private Difficulty difficulty;
    private CookingTime cookingTime;
    private CuisineRegion cuisineRegion;
    private Servings servings;
    private RequiredTool requiredTool;

    private List<IngredientsDTO> ingredients;
    private List<StepsDTO> steps;

    private RecipeSource source;   // AI / USER
    private RecipeVisibility visibility;
    private Boolean isUseLocalData;
    private String stayRegion;
}

