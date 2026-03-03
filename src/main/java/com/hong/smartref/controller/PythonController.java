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

    @Operation(summary = "마스터 음식 검색", description = "음식 정보를 검색")
    @GetMapping("/search")
    public ResponseEntity<ExternalFoodSearchResponse> search(
            @RequestParam String q
    ) {
        return ResponseEntity.ok(pythonService.search(q));
    }

    @Operation(summary = "재료 x000 번대 재료들 조회", description = "재료 x000 번대 재료들 조회")
    @PostMapping("/get/xdegitInfo")
    public ResponseEntity<ExternalDigitFoodResponse> xdegitInfo(
            @RequestParam Integer digitNumberInteger
    ) {
        return ResponseEntity.ok(pythonService.getDigitItems(digitNumberInteger));
    }

    @Operation(summary = "재료 id 기반 내용 검색(복수 가능)", description = "재료 id 기반 내용 검색(복수 가능)")
    @PostMapping("/get/idInfo")
    public ResponseEntity<List<ExternalIngredientMasterResponse>> getMasterInfo(
            @RequestBody List<Long> masterIds
    ) {
        return ResponseEntity.ok(pythonService.getMasterInfo(masterIds));
    }

    @Operation(summary = "재료추가", description = "재료추가")
    @PostMapping("/post/ingredient")
    public ResponseEntity<ExternalCreateIngredientResponse> createIngredients(
            @RequestBody List<ExternalCreateIngredientRequest> requestList
    ) {
        return ResponseEntity.ok(pythonService.createIngredients(requestList));
    }

    @Operation(summary = "닉네임 추가", description = "닉네임 추가")
    @PostMapping("/post/nickname")
    public ResponseEntity<ExternalAddNicknameResponse> addNicknames(
            @RequestBody List<ExternalAddNicknameRequest> requestList
    ) {
        return ResponseEntity.ok(pythonService.addNicknames(requestList));
    }

    @Operation(summary = "미스테리 재료 추가", description = "미스테리 재료 추가")
    @PostMapping("/post/mistery/ingredient")
    public ResponseEntity<ExternalMisteryIngredientCreateResponse> createMisteryIngredients(
            @RequestBody List<ExternalMisteryIngredientCreateRequest> requestList
    ) {
        return ResponseEntity.ok(pythonService.createMisteryIngredients(requestList));
    }

    @Operation(summary = "요리 추가", description = "요리 추가")
    @PostMapping("/post/cuisine/ingredient")
    public ResponseEntity<ExternalCuisineIngredientCreateResponse> createCuisineIngredients(
            @RequestBody List<ExternalCuisineIngredientCreateRequest> requests
    ) {
        return ResponseEntity.ok(pythonService.createCuisineIngredients(requests));
    }

    @Operation(summary = "x번대 id 를 y번대 id로 이전", description = "x번대 id 를 y번대 id로 이전")
    @PostMapping("/post/migrated/id")
    public ResponseEntity<ExternalIngredientMigrationResponse> migrateIngredientIds(
            @RequestBody List<ExternalIngredientMigrationRequest> requests
    ) {
        return ResponseEntity.ok(pythonService.migrateIngredientIds(requests));
    }

    @Operation(summary = "ingredient 를 nickname 으로 변환", description = "ingredient 를 nickname 으로 변환")
    @PostMapping("/post/transfer/ingredientToNickname")
    public ResponseEntity<ExternalIngredientToNicknameResponse> transferIngredientToNickname(
            @RequestBody List<ExternalIngredientToNicknameRequest> requests
    ) {
        return ResponseEntity.ok(pythonService.transferIngredientToNickname(requests));
    }

    @Operation(summary = "ingredient data 편집", description = "ingredient data 편집")
    @PatchMapping("/patch/ingredient")
    public ResponseEntity<ExternalPatchIngredientResponse> patchIngredients(
            @RequestBody  List<ExternalIngredientUpdateRequest> requestList
    ) {
        return ResponseEntity.ok(pythonService.patchIngredients(requestList));
    }

    @Operation(summary = "nickname data 편집", description = "nickname data 편집")
    @PatchMapping("/patch/nickName")
    public ResponseEntity<ExternalPatchNicknameResponse> patchNicknames(
            @RequestBody  List<ExternalPatchNicknameRequest> requestList
    ) {
        return ResponseEntity.ok(pythonService.patchNicknames(requestList));
    }

    @Operation(summary = "ingredient 삭제", description = "ingredient 삭제")
    @DeleteMapping("/delete/ingredient")
    public ResponseEntity<ExternalDeleteIngredientResponse> deleteIngredients(
            @RequestBody  List<ExternalDeleteIngredientRequest> requestList
    ) {
        return ResponseEntity.ok(pythonService.deleteIngredients(requestList));
    }

    @Operation(summary = "nickname 삭제", description = "nickname 삭제")
    @DeleteMapping("/delete/nickName")
    public ResponseEntity<ExternalDeleteNicknameResponse> deleteNicknames(
            @RequestBody  List<ExternalDeleteNicknameRequest> requestList
    ) {
        return ResponseEntity.ok(pythonService.deleteNicknames(requestList));
    }

}
