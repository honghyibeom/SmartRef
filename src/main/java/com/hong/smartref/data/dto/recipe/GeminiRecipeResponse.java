package com.hong.smartref.data.dto.recipe;

import lombok.Data;

import java.util.List;

@Data
public class GeminiRecipeResponse {
    private List<RecipeItem> items;
    private Meta meta;
}
