package com.hong.smartref.service;

import com.hong.smartref.config.security.UserDetailsImpl;
import com.hong.smartref.data.dto.recipe.RecipeIdDTO;
import com.hong.smartref.data.dto.recipe.RecipeRequest;
import com.hong.smartref.data.entity.Recipe;
import com.hong.smartref.data.entity.RecipeIngredient;
import com.hong.smartref.data.entity.RecipeSave;
import com.hong.smartref.data.entity.RecipeStep;
import com.hong.smartref.data.enumerate.RecipeVisibility;
import com.hong.smartref.exception.CustomException;
import com.hong.smartref.exception.ErrorCode;
import com.hong.smartref.repository.RecipeIngredientRepository;
import com.hong.smartref.repository.RecipeRepository;
import com.hong.smartref.repository.RecipeSaveRepository;
import com.hong.smartref.repository.RecipeStepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeStepRepository recipeStepRepository;
    private final RecipeSaveRepository recipeSaveRepository;

    @Transactional
    public RecipeIdDTO addRecipe(List<RecipeRequest> recipeRequests, UserDetailsImpl userDetails) {
        List<Long> recipeIds = new ArrayList<>();
        for (RecipeRequest recipeRequest : recipeRequests) {
            Recipe newRecipe = Recipe.builder()
                    .title(recipeRequest.getTitle())
                    .user(userDetails.getUser())
                    .recipeType(recipeRequest.getRecipeType())
                    .category(recipeRequest.getCategory())
                    .cookingMethod(recipeRequest.getCookingMethod())
                    .technique(recipeRequest.getTechnique())
                    .dietaryGoal(recipeRequest.getDietaryGoal())
                    .dietaryRestriction(recipeRequest.getDietaryRestriction())
                    .primaryIngredient(recipeRequest.getPrimaryIngredient())
                    .occasion(recipeRequest.getOccasion())
                    .difficulty(recipeRequest.getDifficulty())
                    .cookingTime(recipeRequest.getCookingTime())
                    .cuisineRegion(recipeRequest.getCuisineRegion())
                    .servings(recipeRequest.getServings())
                    .requiredTool(recipeRequest.getRequiredTool())
                    .source(recipeRequest.getSource())
                    .visibility(RecipeVisibility.PUBLIC)
                    .build();
            Recipe result = recipeRepository.save(newRecipe);
            recipeIds.add(result.getRecipeId());

            recipeSaveRepository.save(
                    RecipeSave.builder()
                            .recipe(result)
                            .user(userDetails.getUser())
                            .build());

            List<RecipeIngredient> ingredients = new ArrayList<>();
            for (String ingredient  : recipeRequest.getIngredients()) {
                ingredients.add(
                        RecipeIngredient.builder()
                                .recipe(result)
                                .name(ingredient )
                                .build()
                );
            }
            recipeIngredientRepository.saveAll(ingredients);

            List<RecipeStep> steps = new ArrayList<>();
            for (String step : recipeRequest.getSteps()) {
                steps.add(RecipeStep.builder()
                        .recipe(result)
                        .description(step)
                        .build()
                );
            }
            recipeStepRepository.saveAll(steps);
        }
        return RecipeIdDTO.from(recipeIds);
    }

    @Transactional
    public Long deleteRecipe(Long recipeId, UserDetailsImpl userDetails) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_RECIPE));

        // (선택) 권한 체크
        // 예: 작성자만 삭제 가능
         if (recipe.getUser().getEmail().equals(userDetails.getUser().getEmail())) {
             throw new CustomException(ErrorCode.NO_AUTHORITY);
         }

        recipeRepository.delete(recipe);
        return recipeId;
    }

    public Long registerRecipe(UserDetailsImpl) {

    }


}
