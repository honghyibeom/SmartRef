package com.hong.smartref.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hong.smartref.config.security.UserDetailsImpl;
import com.hong.smartref.data.dto.food.*;
import com.hong.smartref.data.entity.*;
import com.hong.smartref.exception.CustomException;
import com.hong.smartref.exception.ErrorCode;
import com.hong.smartref.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;
    private final LabelRepository labelRepository;
    private final LocationRepository locationRepository;
    private final StorageRepository storageRepository;
    private final S3ImageService s3ImageService;
    private final RestClient imageAnalyzeRestClient;
    private final ObjectMapper objectMapper;

    //food 생성
    public FoodIdDTO addFood(List<FoodRequest> foodRequestList) {
        // food를 등록하기 이전에 storage, label, location을 먼저 찾아야 됨.
        List<Long> foodIds = new ArrayList<>();

        for (FoodRequest foodRequest : foodRequestList) {

            Label label = labelRepository.findByName(foodRequest.getLabel())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_LABEL));

            Location location = locationRepository.findById(foodRequest.getLocationId())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_LOCATION));

            Storage storage = storageRepository.findById(foodRequest.getStorageId())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_STORAGE));

            Food food = Food.create(
                    storage,
                    label,
                    foodRequest.getName(),
                    foodRequest.getAmountType(),
                    foodRequest.getQuantity(),
                    foodRequest.getUnit(),
                    foodRequest.getExpiryDate(),
                    location,
                    foodRequest.getImageUrl(),
                    foodRequest.getMemo(),
                    foodRequest.getMasterId()
            );

            Food savedFood = foodRepository.save(food);
            foodIds.add(savedFood.getFoodId());
        }

        return FoodIdDTO.builder()
                .foodId(foodIds)
                .build();
    }

    //food 수정
    @Transactional
    public FoodIdDTO updateFood(List<FoodRequest> foodListRequest) {

        List<Long> foodIds = new ArrayList<>();

        for (FoodRequest foodRequest : foodListRequest) {
            Food food = foodRepository.findById(foodRequest.getFoodId())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_FOOD));

            Storage storage = null;
            if (foodRequest.getStorageId() != null) {
                storage = storageRepository.findById(foodRequest.getStorageId())
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_STORAGE));
            }

            Label label = null;
            if (foodRequest.getLabel() != null) {
                label = labelRepository.findByName(foodRequest.getLabel())
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_LABEL));
            }

            Location location = null;
            if (foodRequest.getLocationId() != null) {
                location = locationRepository.findById(foodRequest.getLocationId())
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_LOCATION));
            }

            // 🔥 이미지 변경 감지 (서비스에서 처리하는게 맞다)
            if (!Objects.equals(food.getImageUrl(), foodRequest.getImageUrl())) {

                if (food.getImageUrl() != null) {
                    s3ImageService.deleteFile(food.getImageUrl());
                }
            }

            // 🔥 엔티티에 위임
            food.update(
                    storage,
                    label,
                    foodRequest.getMasterId(),
                    foodRequest.getName(),
                    foodRequest.getQuantity(),
                    foodRequest.getUnit(),
                    foodRequest.getExpiryDate(),
                    location,
                    foodRequest.getAmountType(),
                    foodRequest.getMemo(),
                    foodRequest.getImageUrl()
            );
            foodIds.add(food.getFoodId());
        }



        return FoodIdDTO.builder()
                .foodId(foodIds)
                .build();
    }

    @Transactional
    public Long deleteFood(Long foodId) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_FOOD));

        // 이미지가 있으면 S3에서도 삭제
        if (food.getImageUrl() != null) {
            s3ImageService.deleteFile(food.getImageUrl());
        }

        foodRepository.delete(food);
        return foodId;
    }

    public List<FoodInfo> getFoodInfo(UserDetailsImpl user) {
        return foodRepository.findFoodInfoByUserId(user.getEmail());
    }

    @Transactional
    public ImageAnalyzeResponse analyzeImage(ImageAnalyzeRequest request) {

        // 1️⃣ 마크다운 URL → 순수 URL
        List<String> pureUrls = request.getImageUrl().stream()
                .map(this::extractPureUrl)
                .toList();

        // 2️⃣ 외부 API 요청 DTO 생성
        ImageAnalyzeRequest externalRequest =
                ImageAnalyzeRequest.builder()
                        .imageUrl(pureUrls)
                        .Country(request.getCountry())
                        .build();

        // 3️⃣ 외부 API 호출
        String rawResponse = imageAnalyzeRestClient.post()
                .uri("/imageAnalyzied")
                .body(externalRequest)
                .retrieve()
                .body(String.class);

        if (rawResponse == null || rawResponse.isBlank()) {
            throw new RuntimeException("이미지 분석 API 응답이 비어있음");
        }

        try {
            // 4️⃣ 바로 JSON 파싱 (ApiResponse 아님!)
            return objectMapper.readValue(
                    rawResponse,
                    ImageAnalyzeResponse.class
            );
        } catch (Exception e) {
            throw new RuntimeException(
                    "이미지 분석 응답 파싱 실패. rawResponse=" + rawResponse,
                    e
            );
        }
    }


    private String extractPureUrl(String raw) {
        if (raw == null) return null;

        // [text](url) 형태면 url만 추출
        if (raw.contains("(") && raw.contains(")")) {
            return raw.substring(
                    raw.indexOf("(") + 1,
                    raw.lastIndexOf(")")
            );
        }

        return raw; // 이미 순수 URL이면 그대로
    }
}
