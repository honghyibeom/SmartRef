package com.hong.smartref.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hong.smartref.config.security.UserDetailsImpl;
import com.hong.smartref.data.dto.ApiResponse;
import com.hong.smartref.data.dto.recipe.*;
import com.hong.smartref.data.entity.*;
import com.hong.smartref.data.enumerate.*;
import com.hong.smartref.exception.CustomException;
import com.hong.smartref.exception.ErrorCode;
import com.hong.smartref.repository.*;
import com.hong.smartref.util.EnumConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                    .isUseLocalData(recipeRequest.getIsUseLocalData())
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
            for (IngredientsDTO ingredient  : recipeRequest.getIngredients()) {
                ingredients.add(
                        RecipeIngredient.builder()
                                .masterId(ingredient.getMasterId())
                                .unit(ingredient.getUnit())
                                .quantity(ingredient.getQuantity())
                                .recipe(result)
                                .build()
                );
            }
            recipeIngredientRepository.saveAll(ingredients);

            List<RecipeStep> steps = new ArrayList<>();
            for (StepsDTO step : recipeRequest.getSteps()) {
                steps.add(RecipeStep.builder()
                        .recipe(result)
                        .way(step.getWay())
                        .imageUrl(step.getImageUrl())
                        .stepNumber(step.getStepNumber())
                        .build()
                );
            }
            recipeStepRepository.saveAll(steps);
        }
        return RecipeIdDTO.from(recipeIds);
    }

    @Transactional
    public Long updateRecipe(RecipeRequest recipeRequest, UserDetailsImpl userDetails) {

        // 1️⃣ 기존 레시피 조회
        Recipe recipe = recipeRepository.findById(recipeRequest.getRecipeId())
                .orElseThrow(() -> new IllegalArgumentException("레시피 없음"));

        // 2️⃣ 권한 체크
        if (!recipe.getUser().getEmail().equals(userDetails.getUser().getEmail())) {
            throw new IllegalArgumentException("수정 권한 없음");
        }

        // 3️⃣ 기본 정보 수정
        recipe.update(
                recipeRequest.getTitle(),
                recipeRequest.getRecipeType(),
                recipeRequest.getCategory(),
                recipeRequest.getCookingMethod(),
                recipeRequest.getTechnique(),
                recipeRequest.getDietaryGoal(),
                recipeRequest.getDietaryRestriction(),
                recipeRequest.getPrimaryIngredient(),
                recipeRequest.getOccasion(),
                recipeRequest.getDifficulty(),
                recipeRequest.getCookingTime(),
                recipeRequest.getCuisineRegion(),
                recipeRequest.getServings(),
                recipeRequest.getRequiredTool(),
                recipeRequest.getSource(),
                recipeRequest.getWrittenLang(),
                recipeRequest.getStayRegion(),
                recipeRequest.getIsUseLocalData()
        );

        // 4️⃣ 기존 재료 삭제
        recipeIngredientRepository.deleteByRecipe(recipe);

        // 5️⃣ 재료 재등록
        List<RecipeIngredient> ingredients = new ArrayList<>();
        for (IngredientsDTO ingredient : recipeRequest.getIngredients()) {
            ingredients.add(
                    RecipeIngredient.builder()
                            .masterId(ingredient.getMasterId())
                            .unit(ingredient.getUnit())
                            .quantity(ingredient.getQuantity())
                            .recipe(recipe)
                            .build()
            );
        }
        recipeIngredientRepository.saveAll(ingredients);

        // 6️⃣ 기존 step 삭제
        recipeStepRepository.deleteByRecipe(recipe);

        // 7️⃣ step 재등록
        List<RecipeStep> steps = new ArrayList<>();
        for (StepsDTO step : recipeRequest.getSteps()) {
            steps.add(
                    RecipeStep.builder()
                            .recipe(recipe)
                            .way(step.getWay())
                            .imageUrl(step.getImageUrl())
                            .stepNumber(step.getStepNumber())
                            .build()
            );
        }
        recipeStepRepository.saveAll(steps);

        return recipe.getRecipeId();
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

        recipe.setLikeCount(recipe.getLikeCount() + 1);
        recipeRepository.save(recipe);
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

        recipe.setLikeCount(recipe.getLikeCount() - 1);
        recipeRepository.save(recipe);

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
                .uri("/recipe")
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

    public RecipeSearchResponse recipeSearch(int page, RecipeSearchRequest dto) {

        PageRequest pageRequest = PageRequest.of(page, 5);

        List<Long> masterIds = null;

        if (dto.getIngredients() != null && !dto.getIngredients().isEmpty()) {
            masterIds = dto.getIngredients().stream()
                    .map(IngredientsDTO::getMasterId)
                    .toList();
        }

        String searchValue = dto.getSearchValue();

        if (searchValue == null || searchValue.isBlank()) {
            searchValue = "";
        }
        //레시피를 검색한다.
        Page<Recipe> recipes = recipeRepository.searchRecipes(
                masterIds,
                dto.getRecipeType(),
                dto.getCookingMethod(),
                dto.getCookingTechnique(),
                dto.getDietaryGoal(),
                dto.getDietaryRestriction(),
                dto.getPrimaryIngredient(),
                dto.getRecipeCategory(),
                dto.getOccasion(),
                dto.getDifficulty(),
                dto.getCookingTime(),
                dto.getServings(),
                dto.getRequiredTool(),
                dto.getCuisineRegion(),
                dto.getRecipeSource(),
                dto.getRecipeVisibility(),
                searchValue,
                pageRequest
        );
        if (recipes.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_EXIST_RECIPE);
        }

        List<RecipeInfo> recipeInfoList = new ArrayList<>();
        for (Recipe recipe : recipes) {
            List<IngredientsDTO> ingredients = new ArrayList<>();
            for (RecipeIngredient ingredient : recipe.getIngredients()) {
                IngredientsDTO ingredientsDTO = IngredientsDTO
                        .builder()
                        .unit(ingredient.getUnit())
                        .quantity(ingredient.getQuantity())
                        .amountType(ingredient.getAmountType())
                        .masterId(ingredient.getMasterId())
                        .build();
                ingredients.add(ingredientsDTO);
            }

            List<StepsDTO> steps = new ArrayList<>();
            for (RecipeStep step : recipe.getSteps()) {
                StepsDTO stepsDTO = StepsDTO.builder()
                        .imageUrl(step.getImageUrl())
                        .way(step.getWay())
                        .build();
                steps.add(stepsDTO);
            }

            RecipeInfo recipeInfo = RecipeInfo.builder()
                    .title(recipe.getTitle())
                    .recipeType(recipe.getRecipeType())
                    .writtenLang(recipe.getWrittenLang())
                    .category(recipe.getCategory())
                    .cookingMethod(recipe.getCookingMethod())
                    .technique(recipe.getTechnique())
                    .dietaryGoal(recipe.getDietaryGoal())
                    .dietaryRestriction(recipe.getDietaryRestriction())
                    .primaryIngredient(recipe.getPrimaryIngredient())
                    .occasion(recipe.getOccasion())
                    .difficulty(recipe.getDifficulty())
                    .cookingTime(recipe.getCookingTime())
                    .cuisineRegion(recipe.getCuisineRegion())
                    .servings(recipe.getServings())
                    .requiredTool(recipe.getRequiredTool())
                    .ingredients(ingredients)
                    .steps(steps)
                    .source(recipe.getSource())
                    .visibility(recipe.getVisibility())
                    .isUseLocalData(recipe.isUseLocalData())
                    .stayRegion(recipe.getStayRegion())
                    .likeCount(recipe.getLikeCount())
                    .viewCount(recipe.getViewCount())
                    .build();

            recipeInfoList.add(recipeInfo);
        }

        return RecipeSearchResponse.builder()
                .recipes(recipeInfoList)
                .isAfter(recipes.hasNext())
                .isPrev(recipes.hasPrevious())
                .pageNumber(recipes.getNumber())
                .build();
    }
}
