package com.hong.smartref.controller;

import com.hong.smartref.data.dto.ApiResponse;
import com.hong.smartref.data.dto.food.FoodRequest;
import com.hong.smartref.data.dto.user.SignupRequest;
import com.hong.smartref.service.FoodService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/food")
public class FoodController {
    private final FoodService foodService;

    @Operation(summary = "음식 등록 api", description = "음식 정보를 저장")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Long>> insertFood(@ModelAttribute FoodRequest foodRequest,
                                                    @RequestPart(value = "imageUrl", required = false) MultipartFile imageUrl) {
        return ResponseEntity.ok(
                ApiResponse.success("식품 등록 완료", foodService.addFood(foodRequest, imageUrl))
        );
    }
    @Operation(summary = "음식 수정 api", description = "음식 정보를 수정")
    @PostMapping("/edit")
    public ResponseEntity<ApiResponse<Long>> updateFood(@ModelAttribute FoodRequest foodRequest,
                                                        @RequestPart(value = "imageUrl", required = false) MultipartFile imageUrl) {
        return ResponseEntity.ok(
                ApiResponse.success("식품 수정 완료", foodService.updateFood(foodRequest, imageUrl))
        );
    }
    @Operation(summary = "음식 삭제 api", description = "음식 정보를 삭제")
    @PostMapping("/delete")
    public ResponseEntity<ApiResponse<Long>> deleteFood(@RequestParam Long foodId) {
        return ResponseEntity.ok(
                ApiResponse.success("식품 삭제 완료", foodService.deleteFood(foodId))
        );
    }


}
