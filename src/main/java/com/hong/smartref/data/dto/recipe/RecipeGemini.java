package com.hong.smartref.data.dto.recipe;

import lombok.Data;

import java.util.List;

@Data
public class RecipeGemini {
    private List<String> ingredients;
    private String prompt;
    private String difficulty;
    private String servings;
    private boolean isUseLocalData;
    private String stay_region;
}
