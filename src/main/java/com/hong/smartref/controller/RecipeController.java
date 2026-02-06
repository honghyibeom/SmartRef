package com.hong.smartref.controller;

import com.hong.smartref.config.security.UserDetailsImpl;
import com.hong.smartref.data.dto.ApiResponse;
import com.hong.smartref.data.dto.recipe.GeminiRecipeResponse;
import com.hong.smartref.data.dto.recipe.RecipeGemini;
import com.hong.smartref.data.dto.recipe.RecipeIdDTO;
import com.hong.smartref.data.dto.recipe.RecipeRequest;
import com.hong.smartref.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RecipeController {

    private final RecipeService recipeService;

    @Operation(summary = "레시피 등록 api", description = "레시피를 등록한다.")
    @PostMapping("/recipe/enroll")
    public ResponseEntity<ApiResponse<RecipeIdDTO>> insertRecipe(@RequestBody List<RecipeRequest> recipeRequests,
                                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                ApiResponse.success("레시피 추가", recipeService.addRecipe(recipeRequests, userDetails))
        );
    }

    @Operation(summary = "레시피 삭제 api", description = "레시피를 삭제한다.")
    @PostMapping("/recipe/delete")
    public ResponseEntity<ApiResponse<Long>> deleteRecipe(@RequestParam("recipeId") Long recipeId,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                ApiResponse.success("레시피 삭제", recipeService.deleteRecipe(recipeId, userDetails))
        );
    }

    @Operation(summary = "유저가 레시피 저장 api", description = "유저가 저장한 레시피 등록")
    @PostMapping("/user/recipe/save")
    public ResponseEntity<ApiResponse<Long>> RegisterUserRecipe(@RequestParam("recipeId") Long recipeId,
                                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                ApiResponse.success("유저가 저장한 레시피 저장 완료", recipeService.registerRecipeSave(recipeId, userDetails))
        );
    }

    @Operation(summary = "유저가 저장한 레시피 삭제 api", description = "유저가 저장한 레시피 삭제")
    @PostMapping("/user/recipe/delete")
    public ResponseEntity<ApiResponse<Long>> deleteUserRecipe(@RequestParam("recipeId") Long recipeId,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                ApiResponse.success("유저가 저장한 레시피 삭제 완료", recipeService.deleteRecipeSave(recipeId, userDetails))
        );
    }

    @Operation(summary = "레시피 즐겨찾기 추가 api", description = "유저가 저장한 레시피 즐겨찾기 추가")
    @PostMapping("/user/recipe/addFavorite")
    public ResponseEntity<ApiResponse<Long>> RecipeLike(@RequestParam("recipeId") Long recipeId,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                ApiResponse.success("유저가 저장한 레시피 즐겨찾기 추가 완료", recipeService.RecipeLike(recipeId, userDetails))
        );
    }

    @Operation(summary = "레시피 즐겨찾기 삭제 api", description = "유저가 저장한 레시피 즐겨찾기 삭제")
    @PostMapping("/user/recipe/removeFavorite")
    public ResponseEntity<ApiResponse<Long>> RecipeDisLike(@RequestParam("recipeId") Long recipeId,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                ApiResponse.success("유저가 저장한 레시피 즐겨찾기 삭제 완료", recipeService.RecipeDisLike(recipeId, userDetails))
        );
    }

    @Operation(summary = "레시피 재미나이 요청 api", description = "레시피 재미나이 요청")
    @PostMapping("/user/recipe/gemini")
    public ResponseEntity<ApiResponse<GeminiRecipeResponse>> getGemini(
            @RequestBody RecipeGemini recipeGemini,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "레시피 재미나이 생성 요청",
                        recipeService.getGemini(recipeGemini,userDetails)
                )
        );
    }
}
