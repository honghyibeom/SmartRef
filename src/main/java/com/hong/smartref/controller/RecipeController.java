package com.hong.smartref.controller;

import com.hong.smartref.config.security.UserDetailsImpl;
import com.hong.smartref.data.dto.ApiResponse;
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
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    @Operation(summary = "레시피 등록 api", description = "레시피를 등록한다.")
    @PostMapping("/enroll")
    public ResponseEntity<ApiResponse<RecipeIdDTO>> insertRecipe(@RequestBody List<RecipeRequest> recipeRequests,
                                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                ApiResponse.success("레시피 추가", recipeService.addRecipe(recipeRequests, userDetails))
        );
    }

    @Operation(summary = "레시피 등록 api", description = "레시피를 등록한다.")
    @PostMapping("/delete")
    public ResponseEntity<ApiResponse<Long>> deleteRecipe(@RequestParam("recipeId") Long recipeId,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                ApiResponse.success("레시피 삭제", recipeService.deleteRecipe(recipeId, userDetails))
        );
    }
}
