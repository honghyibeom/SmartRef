package com.hong.smartref.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hong.smartref.config.security.UserDetailsImpl;
import com.hong.smartref.data.dto.ApiResponse;
import com.hong.smartref.data.dto.recipe.GeminiRecipeResponse;
import com.hong.smartref.data.dto.recipe.RecipeGemini;
import com.hong.smartref.data.dto.recipe.RecipeIdDTO;
import com.hong.smartref.data.dto.recipe.RecipeRequest;
import com.hong.smartref.data.entity.*;
import com.hong.smartref.data.enumerate.RecipeVisibility;
import com.hong.smartref.exception.CustomException;
import com.hong.smartref.exception.ErrorCode;
import com.hong.smartref.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeStepRepository recipeStepRepository;
    private final RecipeSaveRepository recipeSaveRepository;
    private final RecipeLikeRepository recipeLikeRepository;
    private final RestClient recipeRestClient;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    // 레시피 생성
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
                    .writtenLang(recipeRequest.getWrittenLang())
                    .stayRegion(recipeRequest.getStayRegion())

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

    // 진짜 레시피 삭제
    @Transactional
    public Long deleteRecipe(Long recipeId, UserDetailsImpl userDetails) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_RECIPE));

        // (선택) 권한 체크
        // 예: 작성자만 삭제 가능
         if (!recipe.getUser().getEmail().equals(userDetails.getUser().getEmail())) {
             throw new CustomException(ErrorCode.NO_AUTHORITY);
         }

        recipeRepository.delete(recipe);
        return recipeId;
    }

    // 유저가 저장한 레시피 등록
    @Transactional
    public Long registerRecipeSave(Long recipeId ,UserDetailsImpl userDetails) {
        //레시피를 먼저 구한 다음 RecipeSave에 저장한다.
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_RECIPE));

        boolean isExist = recipeSaveRepository.existsRecipeSaveByRecipeAndUser(recipe, userDetails.getUser());

        if (isExist) {
           throw new CustomException(ErrorCode.EXIST_RECIPE_SAVE);
        }

        RecipeSave recipeSave = RecipeSave.builder()
                .recipe(recipe)
                .user(userDetails.getUser())
                .build();

        recipeSaveRepository.save(recipeSave);
        return recipeSave.getId();
    }

    //유저가 저장한 레시피 삭제
    @Transactional
    public Long deleteRecipeSave(Long recipeId, UserDetailsImpl userDetails) {
        //레시피를 먼저 구한 다음 RecipeSave를 삭제한다.
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_RECIPE));
        RecipeSave recipeSave = recipeSaveRepository.findByUserAndRecipe(userDetails.getUser(), recipe)
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_RECIPE_SAVE));

        recipeSaveRepository.delete(recipeSave);
        return recipeSave.getId();
    }

    // 유저가 저장한 레시피 즐겨찾기 추가
    @Transactional
    public Long RecipeLike(Long recipeId, UserDetailsImpl userDetails) {
        //레시피를 먼저 구한 다음 RecipeSave에 저장한다.
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_RECIPE));

        RecipeLike recipeLike = RecipeLike.builder()
                .recipe(recipe)
                .user(userDetails.getUser())
                .build();

        recipeLikeRepository.save(recipeLike);
        return recipeLike.getLikeId();
    }

    @Transactional
    public Long RecipeDisLike(Long recipeId, UserDetailsImpl userDetails) {
        //레시피를 먼저 구한 다음 RecipeSave를 삭제한다.
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_RECIPE));
        RecipeLike recipeLike = recipeLikeRepository.findByUserAndRecipe(userDetails.getUser(), recipe)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_RECIPE_LIKE));

        recipeLikeRepository.delete(recipeLike);
        return recipeLike.getLikeId();
    }

    @Transactional
    public GeminiRecipeResponse getGemini(RecipeGemini recipeGemini, UserDetailsImpl userDetails) {

        User user = userDetails.getUser();

        if (user.getTicketCount() == 0) {
            throw new CustomException(ErrorCode.NOT_EXIST_TICKET);
        }
        user.setTicketCount(user.getTicketCount() - 1);
        userRepository.save(user);

        String rawResponse = recipeRestClient.post()
                .uri("/stageAitracker/recipe")
                .body(recipeGemini)
                .retrieve()
                .body(String.class);

        if (rawResponse == null || rawResponse.isBlank()) {
            throw new RuntimeException("Gemini API 응답이 비어있습니다.");
        }

        try {
            // 🔥 바로 파싱 (ApiResponse 아님!)
            return objectMapper.readValue(
                    rawResponse,
                    GeminiRecipeResponse.class
            );

        } catch (Exception e) {
            throw new RuntimeException(
                    "Gemini 응답 파싱 실패. rawResponse=" + rawResponse, e
            );
        }
    }
}
