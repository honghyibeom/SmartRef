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
    public ResponseEntity<ApiResponse<ExternalFoodSearchResponse>> search(
            @RequestParam String q
    ) {
        return ResponseEntity.ok(
                ApiResponse.success("마스터 음식 검색 성공", pythonService.search(q))
        );
    }

    // -------------------------------------------------
    // 2️⃣ 재료 master ID 기반 조회 (batch)
    // -------------------------------------------------
    @Operation(summary = "재료 마스터 조회", description = "재료 master id 기반 조회")
    @PostMapping("/get/masterinfo")
    public ResponseEntity<ApiResponse<List<ExternalIngredientMasterResponse>>> getIngredientBatch(
            @RequestBody List<Long> ids
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "재료 마스터 조회 성공",
                        pythonService.getMasterInfo(ids)
                )
        );
    }

    // -------------------------------------------------
    // 3️⃣ 일반 재료 생성
    // -------------------------------------------------
    @Operation(summary = "재료 생성", description = "일반 재료 batch 생성")
    @PostMapping("/ingredient")
    public ResponseEntity<ApiResponse<List<ExternalIngredientCreateResponse>>> createIngredient(
            @RequestBody List<ExternalIngredientCreateRequest> request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "재료 생성 성공",
                        pythonService.createIngredients(request)
                )
        );
    }

    // -------------------------------------------------
    // 4️⃣ 미스테리 재료 생성
    // -------------------------------------------------
    @Operation(summary = "미스테리 재료 생성", description = "미스테리 재료 batch 생성")
    @PostMapping("/ingredient/mistery")
    public ResponseEntity<ApiResponse<List<ExternalMisteryIngredientCreateResponse>>> createMisteryIngredient(
            @RequestBody List<ExternalMisteryIngredientCreateRequest> request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "미스테리 재료 생성 성공",
                        pythonService.createMisteryIngredients(request)
                )
        );
    }

    // -------------------------------------------------
    // 5️⃣ 요리(cuisine) 재료 생성
    // -------------------------------------------------
    @Operation(summary = "요리 재료 생성", description = "요리(cuisine) 재료 batch 생성")
    @PostMapping("/ingredient/cuisine")
    public ResponseEntity<ApiResponse<List<ExternalCuisineIngredientCreateResponse>>> createCuisineIngredient(
            @RequestBody List<ExternalCuisineIngredientCreateRequest> request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "요리 재료 생성 성공",
                        pythonService.createCuisineIngredients(request)
                )
        );
    }

    // -------------------------------------------------
    // 6️⃣ 재료 ID 대역 이전
    // -------------------------------------------------
    @Operation(summary = "재료 ID 이전", description = "재료 ID 대역 마이그레이션")
    @PostMapping("/ingredient/migrate")
    public ResponseEntity<ApiResponse<List<ExternalIngredientMigrationResponse>>> migrateIngredient(
            @RequestBody List<ExternalIngredientMigrationRequest> request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "재료 ID 마이그레이션 성공",
                        pythonService.migrateIngredientIds(request)
                )
        );
    }

    // -------------------------------------------------
    // 7️⃣ ingredient → nickname 변환
    // -------------------------------------------------
    @Operation(summary = "ingredient → nickname 변환", description = "ingredient를 nickname으로 이전")
    @PostMapping("/ingredient/transfer/nickname")
    public ResponseEntity<ApiResponse<List<ExternalIngredientToNicknameResponse>>> transferToNickname(
            @RequestBody List<ExternalIngredientToNicknameRequest> request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "ingredient → nickname 변환 완료",
                        pythonService.transferIngredientToNickname(request)
                )
        );
    }

    // -------------------------------------------------
    // 8️⃣ nickname 추가
    // -------------------------------------------------
    @Operation(summary = "닉네임 추가", description = "ingredient에 nickname batch 추가")
    @PostMapping("/ingredient/nickname")
    public ResponseEntity<ApiResponse<List<ExternalNicknameCreateResponse>>> addNickname(
            @RequestBody List<ExternalNicknameCreateRequest> request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "닉네임 생성 성공",
                        pythonService.createNicknames(request)
                )
        );
    }

    // -------------------------------------------------
    // 9️⃣ ingredient 데이터 수정 (PATCH)
    // -------------------------------------------------
    @Operation(summary = "ingredient 수정", description = "ingredient master 데이터 수정")
    @PatchMapping("/ingredient")
    public ResponseEntity<ApiResponse<Void>> updateIngredient(
            @RequestBody List<ExternalIngredientUpdateRequest> request
    ) {
        pythonService.updateIngredients(request);
        return ResponseEntity.ok(
                ApiResponse.success("ingredient 수정 완료", null)
        );
    }
}
