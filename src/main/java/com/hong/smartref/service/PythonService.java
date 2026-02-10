package com.hong.smartref.service;

import com.hong.smartref.data.dto.external.*;
import com.hong.smartref.exception.CustomException;
import com.hong.smartref.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class PythonService {
    private final RestClient recipeRestClient;

    // 음식 검색 (별명 포함)
    public ExternalFoodSearchResponse search(String keyword) {

        try {
            return recipeRestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/stageAitracker/search")
                            .queryParam("q", keyword)
                            .build())
                    .retrieve()
                    .body(ExternalFoodSearchResponse.class);

        } catch (Exception e) {
            log.error("외부 음식 검색 API 호출 실패. keyword={}", keyword, e);
            throw new CustomException(ErrorCode.NOT_EXIST_EXTERNAL_FOOD);
        }
    }

    /**
     * 재료 master id 목록으로 상세 정보 조회
     */
    public List<ExternalIngredientMasterResponse> getMasterInfo(List<Long> masterIds) {

        try {
            return recipeRestClient.post()
                    .uri("/stageAitracker/ingredients/batch")
                    .body(masterIds)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

        } catch (Exception e) {
            log.error("외부 재료 master 조회 실패. ids={}", masterIds, e);
            throw new CustomException(ErrorCode.NOT_EXIST_EXTERNAL_FOOD);
        }
    }

    /**
     * 재료 master 복수 생성
     */
    public List<ExternalIngredientCreateResponse> createIngredients(
            List<ExternalIngredientCreateRequest> requests
    ) {
        try {
            return recipeRestClient.post()
                    .uri("/stageAitracker/ingredients/batch")
                    .body(requests)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

        } catch (Exception e) {
            log.error("외부 재료 생성 실패. request={}", requests, e);
            throw new CustomException(ErrorCode.FAIL_CREATE_EXTERNAL_FOOD);
        }
    }

    // 미스테리 재료 추가
    public List<ExternalMisteryIngredientCreateResponse> createMisteryIngredients(
            List<ExternalMisteryIngredientCreateRequest> requests
    ) {
        try {
            return recipeRestClient.post()
                    .uri("/stageAitracker/createMisteryFood")
                    .body(requests)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

        } catch (Exception e) {
            log.error("미스테리 재료 생성 실패. request={}", requests, e);
            throw new CustomException(ErrorCode.FAIL_CREATE_EXTERNAL_FOOD);
        }
    }

    // 요리 추가
    public List<ExternalCuisineIngredientCreateResponse> createCuisineIngredients(
            List<ExternalCuisineIngredientCreateRequest> requests
    ) {
        try {
            return recipeRestClient.post()
                    .uri("/stageAitracker/createCuisineFood")
                    .body(requests)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

        } catch (Exception e) {
            log.error("요리 재료 생성 실패. request={}", requests, e);
            throw new CustomException(ErrorCode.FAIL_CREATE_EXTERNAL_FOOD);
        }
    }

    /**
     * 재료 ID 대역 이전 (x번대 → y번대)
     */
    public List<ExternalIngredientMigrationResponse> migrateIngredientIds(
            List<ExternalIngredientMigrationRequest> requests
    ) {
        try {
            return recipeRestClient.post()
                    .uri("/stageAitracker/migrationNewFood")
                    .body(requests)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

        } catch (Exception e) {
            log.error("외부 재료 ID 마이그레이션 실패. request={}", requests, e);
            throw new CustomException(ErrorCode.FAIL_MIGRATE_EXTERNAL_FOOD);
        }
    }

    /**
     * ingredient 를 nickname(별칭)으로 변환
     */
    public List<ExternalIngredientToNicknameResponse> transferIngredientToNickname(
            List<ExternalIngredientToNicknameRequest> requests
    ) {
        try {
            return recipeRestClient.post()
                    .uri("/stageAitracker/migrationIngredientToNickname")
                    .body(requests)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

        } catch (Exception e) {
            log.error("ingredient → nickname 변환 실패. request={}", requests, e);
            throw new CustomException(ErrorCode.FAIL_TRANSFER_INGREDIENT_TO_NICKNAME);
        }
    }

    /**
     * ingredient 에 nickname(별칭) 추가
     */
    public List<ExternalNicknameCreateResponse> createNicknames(
            List<ExternalNicknameCreateRequest> requests
    ) {
        try {
            return recipeRestClient.post()
                    .uri("/stageAitracker/addNicknameBatch")
                    .body(requests)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

        } catch (Exception e) {
            log.error("닉네임 생성 실패. request={}", requests, e);
            throw new CustomException(ErrorCode.FAIL_CREATE_NICKNAME);
        }
    }

    /**
     * ingredient master 데이터 수정 (batch)
     */
    public void updateIngredients(
            List<ExternalIngredientUpdateRequest> requests
    ) {
        try {
            recipeRestClient.patch()
                    .uri("/stageAitracker/patch/fooditem")
                    .body(requests)
                    .retrieve()
                    .toBodilessEntity();

        } catch (Exception e) {
            log.error("외부 ingredient 수정 실패. request={}", requests, e);
            throw new CustomException(ErrorCode.FAIL_UPDATE_EXTERNAL_FOOD);
        }
    }

}
