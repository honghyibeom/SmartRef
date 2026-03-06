package com.hong.smartref.data.dto.recipe;

import com.hong.smartref.data.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIdDTO {
    private List<Long> recipeId;

    public static RecipeIdDTO from(List<Long> recipeId) {
        RecipeIdDTO recipeIdDTO = new RecipeIdDTO();
        recipeIdDTO.recipeId = recipeId;
        return recipeIdDTO;
    }
}
