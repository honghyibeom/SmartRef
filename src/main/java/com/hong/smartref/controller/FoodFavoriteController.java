package com.hong.smartref.controller;

import com.hong.smartref.config.security.UserDetailsImpl;
import com.hong.smartref.data.dto.ApiResponse;
import com.hong.smartref.data.dto.food.FoodRequest;
import com.hong.smartref.service.FoodFavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/food")
public class FoodFavoriteController {
    private final FoodFavoriteService foodFavoriteService;

    @Operation(summary = "음식 좋아요 api", description = "음식 정보를 저장")
    @PostMapping("/addFavorite")
    public ResponseEntity<ApiResponse<Long>> insertFoodFavorite(@RequestParam("foodId") Long foodId,
                                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                ApiResponse.success("식품 등록 완료", foodFavoriteService.addFavorite(foodId, userDetails))
        );
    }

    @Operation(summary = "음식 좋아요 취소 api", description = "음식 좋아요 취소")
    @PostMapping("/removeFavorite")
    public ResponseEntity<ApiResponse<Long>> deleteFoodFavorite(@RequestParam("foodId") Long foodId,
                                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                ApiResponse.success("식품 등록 완료", foodFavoriteService.deleteFavorite(foodId, userDetails))
        );
    }
}
