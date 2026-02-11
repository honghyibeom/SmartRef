package com.hong.smartref.controller;

import com.hong.smartref.data.dto.ApiResponse;
import com.hong.smartref.data.dto.external.*;
import com.hong.smartref.data.dto.food.FoodRequest;
import com.hong.smartref.service.PythonService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/master")
public class PythonController {
    private final PythonService pythonService;

    // -------------------------------------------------
    // 1️⃣ 마스터 음식 검색
    // -------------------------------------------------
    @Operation(summary = "마스터 음식 검색", description = "음식 정보를 검색")
    @GetMapping("/search")
    public ResponseEntity<ExternalFoodSearchResponse> search(
            @RequestParam String q
    ) {
        return ResponseEntity.ok(pythonService.search(q));
    }

    // -------------------------------------------------
    // 2️⃣ 재료 master ID 기반 조회 (batch)
    // -------------------------------------------------
    @Operation(summary = "재료 마스터 조회", description = "재료 master id 기반 조회")
    @PostMapping("/get/masterinfo")
    public ResponseEntity<List<ExternalIngredientMasterResponse>> getIngredientBatch(
            @RequestBody List<Long> ids
    ) {
        return ResponseEntity.ok(pythonService.getMasterInfo(ids));
    }

    // -------------------------------------------------
    // 3️⃣ 일반 재료 생성
    // -------------------------------------------------
    @Operation(summary = "재료 생성", description = "일반 재료 batch 생성")
    @PostMapping("/post/ingredient")
    public ResponseEntity<List<ExternalIngredientCreateResponse>> createIngredient(
            @RequestBody List<ExternalIngredientCreateRequest> request
    ) {
        return ResponseEntity.ok(pythonService.createIngredients(request));
    }

    // -------------------------------------------------
    // 4️⃣ 미스테리 재료 생성
    // -------------------------------------------------
    @Operation(summary = "미스테리 재료 생성", description = "미스테리 재료 batch 생성")
    @PostMapping("/post/mistery/ingredient")
    public ResponseEntity<List<ExternalMisteryIngredientCreateResponse>> createMisteryIngredient(
            @RequestBody List<ExternalMisteryIngredientCreateRequest> request
    ) {
        return ResponseEntity.ok(pythonService.createMisteryIngredients(request));
    }

    // -------------------------------------------------
    // 5️⃣ 요리(cuisine) 재료 생성
    // -------------------------------------------------
    @Operation(summary = "요리 재료 생성", description = "요리(cuisine) 재료 batch 생성")
    @PostMapping("/post/cuisine/ingredient")
    public ResponseEntity<List<ExternalCuisineIngredientCreateResponse>> createCuisineIngredient(
            @RequestBody List<ExternalCuisineIngredientCreateRequest> request
    ) {
        return ResponseEntity.ok(pythonService.createCuisineIngredients(request));
    }

    // -------------------------------------------------
    // 6️⃣ 재료 ID 대역 이전
    // -------------------------------------------------
    @Operation(summary = "재료 ID 이전", description = "재료 ID 대역 마이그레이션")
    @PostMapping("/post/migrated/id")
    public ResponseEntity<List<ExternalIngredientMigrationResponse>> migrateIngredient(
            @RequestBody List<ExternalIngredientMigrationRequest> request
    ) {
        return ResponseEntity.ok(pythonService.migrateIngredientIds(request));
    }

    // -------------------------------------------------
    // 7️⃣ ingredient → nickname 변환
    // -------------------------------------------------
    @Operation(summary = "ingredient → nickname 변환", description = "ingredient를 nickname으로 이전")
    @PostMapping("/post/transfer/ingredientToNickname")
    public ResponseEntity<List<ExternalIngredientToNicknameResponse>> transferToNickname(
            @RequestBody List<ExternalIngredientToNicknameRequest> request
    ) {
        return ResponseEntity.ok(pythonService.transferIngredientToNickname(request));
    }

    // -------------------------------------------------
    // 8️⃣ nickname 추가
    // -------------------------------------------------
    @Operation(summary = "닉네임 추가", description = "ingredient에 nickname batch 추가")
    @PostMapping("/post/nickname")
    public ResponseEntity<List<ExternalNicknameCreateResponse>> addNickname(
            @RequestBody List<ExternalNicknameCreateRequest> request
    ) {
        return ResponseEntity.ok(pythonService.createNicknames(request));
    }

    // -------------------------------------------------
    // 9️⃣ ingredient 데이터 수정 (PATCH)
    // -------------------------------------------------
    @Operation(summary = "ingredient 수정", description = "ingredient master 데이터 수정")
    @PatchMapping("/patch/ingredient")
    public ResponseEntity<Void> updateIngredient(
            @RequestBody List<ExternalIngredientUpdateRequest> request
    ) {
        pythonService.updateIngredients(request);
        return ResponseEntity.ok(null);
    }
}
