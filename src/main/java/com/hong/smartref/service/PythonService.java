package com.hong.smartref.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.hong.smartref.data.dto.external.*;
import com.hong.smartref.exception.CustomException;
import com.hong.smartref.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class PythonService {
    private final RestClient recipeRestClient;

    // 재료 검색(별명 포함)
    public ExternalFoodSearchResponse search(String keyword) {

        try {
            return recipeRestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/search")
                            .queryParam("q", keyword)
                            .build())
                    .retrieve()
                    .body(ExternalFoodSearchResponse.class);

        } catch (Exception e) {
            log.error("외부 음식 검색 API 호출 실패. keyword={}", keyword, e);
            throw new CustomException(ErrorCode.NOT_EXIST_EXTERNAL_FOOD);
        }
    }

    // 재료 x000 번대 재료들 조회
    public ExternalDigitFoodResponse getDigitItems(Integer digitNumber) {

        try {
            return recipeRestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/getxdigititems")
                            .queryParam("digitNumber", digitNumber)
                            .build())
                    .retrieve()
                    .body(ExternalDigitFoodResponse.class);

        } catch (Exception e) {
            log.error("외부 x000 재료 조회 실패. digitNumber={}", digitNumber, e);
            throw new CustomException(ErrorCode.NOT_EXIST_EXTERNAL_FOOD);
        }
    }

    // 재료 id 기반 내용 검색(복수 가능)
    public List<ExternalIngredientMasterResponse> getMasterInfo(List<Long> masterIds) {

        try {
            return recipeRestClient.post()
                    .uri("/ingredients")
                    .body(masterIds)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

        } catch (Exception e) {
            log.error("외부 재료 master 조회 실패. ids={}", masterIds, e);
            throw new CustomException(ErrorCode.NOT_EXIST_EXTERNAL_FOOD);
        }
    }

    //재료추가
    public ExternalCreateIngredientResponse createIngredients(List<ExternalCreateIngredientRequest> requestList) {

        try {
            return recipeRestClient.post()
                    .uri("/createNewFood")
                    .body(requestList)
                    .retrieve()
                    .body(ExternalCreateIngredientResponse.class);

        } catch (Exception e) {
            log.error("외부 재료 생성 API 호출 실패", e);
            throw new CustomException(ErrorCode.FAIL_CREATE_EXTERNAL_FOOD);
        }
    }

    //닉네임 추가
    public ExternalAddNicknameResponse addNicknames(
            List<ExternalAddNicknameRequest> requestList) {

        try {
            return recipeRestClient.post()
                    .uri("/addNickname")
                    .body(requestList)
                    .retrieve()
                    .body(ExternalAddNicknameResponse.class);

        } catch (Exception e) {
            log.error("외부 닉네임 추가 API 호출 실패", e);
            throw new CustomException(ErrorCode.EXTERNAL_API_ERROR);
        }
    }

    // 미스테리 재료 추가
    public ExternalMisteryIngredientCreateResponse createMisteryIngredients(
            List<ExternalMisteryIngredientCreateRequest> requests
    ) {
        try {
            return recipeRestClient.post()
                    .uri("/createMisteryFood")
                    .body(requests)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

        } catch (Exception e) {
            log.error("미스테리 재료 생성 실패. request={}", requests, e);
            throw new CustomException(ErrorCode.FAIL_CREATE_EXTERNAL_FOOD);
        }
    }

    // 요리 추가
    public ExternalCuisineIngredientCreateResponse createCuisineIngredients(
            List<ExternalCuisineIngredientCreateRequest> requests
    ) {
        try {
            return recipeRestClient.post()
                    .uri("/createCuisineFood")
                    .body(requests)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

        } catch (Exception e) {
            log.error("요리 재료 생성 실패. request={}", requests, e);
            throw new CustomException(ErrorCode.FAIL_CREATE_EXTERNAL_FOOD);
        }
    }

    // x번대 id 를 y번대 id로 이전
    public ExternalIngredientMigrationResponse migrateIngredientIds(
            List<ExternalIngredientMigrationRequest> requests
    ) {
        try {
            return recipeRestClient.post()
                    .uri("/migrationNewFood")
                    .body(requests)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

        } catch (Exception e) {
            log.error("외부 재료 ID 마이그레이션 실패. request={}", requests, e);
            throw new CustomException(ErrorCode.FAIL_MIGRATE_EXTERNAL_FOOD);
        }
    }

    //ingredient 를 nickname 으로 변환
    public ExternalIngredientToNicknameResponse transferIngredientToNickname(
            List<ExternalIngredientToNicknameRequest> requests
    ) {
        try {
            return recipeRestClient.post()
                    .uri("/migrationIngredientToNickname")
                    .body(requests)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

        } catch (Exception e) {
            log.error("ingredient → nickname 변환 실패. request={}", requests, e);
            throw new CustomException(ErrorCode.FAIL_TRANSFER_INGREDIENT_TO_NICKNAME);
        }
    }

    // ingredient data 편집
    public ExternalPatchIngredientResponse patchIngredients(
            List<ExternalIngredientUpdateRequest> requestList) {

        try {
            return recipeRestClient.patch()
                    .uri("/patch/fooditem")
                    .body(requestList)
                    .retrieve()
                    .body(ExternalPatchIngredientResponse.class);

        } catch (Exception e) {
            log.error("ingredient 수정 실패", e);
            throw new CustomException(ErrorCode.EXTERNAL_API_ERROR);
        }
    }

    // nickname data 편집
    public ExternalPatchNicknameResponse patchNicknames(
            List<ExternalPatchNicknameRequest> requestList) {

        try {
            return recipeRestClient.patch()
                    .uri("/patch/nickName")
                    .body(requestList)
                    .retrieve()
                    .body(ExternalPatchNicknameResponse.class);

        } catch (Exception e) {
            log.error("nickname 수정 실패", e);
            throw new CustomException(ErrorCode.EXTERNAL_API_ERROR);
        }
    }

    //ingredient 삭제
    public ExternalDeleteIngredientResponse deleteIngredients(
            List<ExternalDeleteIngredientRequest> requestList) {

        try {
            return recipeRestClient.method(HttpMethod.DELETE)
                    .uri("/delete/ingredient")
                    .body(requestList)
                    .retrieve()
                    .body(ExternalDeleteIngredientResponse.class);

        } catch (Exception e) {
            log.error("ingredient 삭제 실패", e);
            throw new CustomException(ErrorCode.EXTERNAL_API_ERROR);
        }
    }

    // nickname 삭제
    public ExternalDeleteNicknameResponse deleteNicknames(
            List<ExternalDeleteNicknameRequest> requestList) {

        try {
            return recipeRestClient.method(HttpMethod.DELETE)
                    .uri("/delete/nickName")
                    .body(requestList)
                    .retrieve()
                    .body(ExternalDeleteNicknameResponse.class);

        } catch (Exception e) {
            log.error("nickname 삭제 실패", e);
            throw new CustomException(ErrorCode.EXTERNAL_API_ERROR);
        }
    }

    // 한번 요청으로 한번에 여러 재료를 검색하게끔
    public JsonNode searchBatch(List<String> ingredients) {

        return recipeRestClient.post()
                .uri("/search/batch")
                .body(ingredients)
                .retrieve()
                .body(JsonNode.class);
    }

}
