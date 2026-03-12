package com.hong.smartref.controller;

import com.hong.smartref.config.security.UserDetailsImpl;
import com.hong.smartref.data.dto.ApiResponse;
import com.hong.smartref.data.dto.food.*;
import com.hong.smartref.data.dto.recipe.GeminiRecipeResponse;
import com.hong.smartref.service.FoodService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/food")
public class FoodController {
    private final FoodService foodService;

    @Operation(summary = "음식 등록 api", description = "음식 정보를 저장")
    @PostMapping(value = "/register")
    public ResponseEntity<ApiResponse<FoodIdDTO>> insertFood(@RequestBody List<FoodRequest> foods) {
        return ResponseEntity.ok(
                ApiResponse.success("식품 등록 완료", foodService.addFood(foods))
        );
    }
    @Operation(summary = "음식 수정 api", description = "음식 정보를 수정")
    @PostMapping("/edit")
    public ResponseEntity<ApiResponse<Long>> updateFood(
            @RequestBody FoodRequest foodRequest) {

        return ResponseEntity.ok(
                ApiResponse.success("식품 수정 완료", foodService.updateFood(foodRequest))
        );
    }
    @Operation(summary = "음식 삭제 api", description = "음식 정보를 삭제")
    @PostMapping("/delete")
    public ResponseEntity<ApiResponse<Long>> deleteFood(@RequestParam Long foodId) {
        return ResponseEntity.ok(
                ApiResponse.success("식품 삭제 완료", foodService.deleteFood(foodId))
        );
    }

    @Operation(summary = "음식 조회 api", description = "자신의 음식 정보를 전부 조회")
    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse<List<FoodInfo>>> deleteFood(@AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(
                ApiResponse.success("음식 조회 성공", foodService.getFoodInfo(user))
        );
    }

    @Operation(summary = "음식 재미나이 요청 api", description = "음식 재미나이 요청")
    @PostMapping("/gemini")
    public ResponseEntity<ApiResponse<ImageAnalyzeResponse>> analyzeImage(
            @RequestBody ImageAnalyzeRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "이미지 분석 완료",
                        foodService.analyzeImage(request)
                )
        );
    }


}
