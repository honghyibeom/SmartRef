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
    public FoodIdDTO addFood(List<FoodRequest> foodRequestList, MultipartFile imageUrl) {
        // food를 등록하기 이전에 storage, label, location을 먼저 찾아야 됨.
        List<Long> foodIds = new ArrayList<>();

        // 이미지가 공통이라면 한 번만 업로드
        String uploadedImageUrl = null;
        if (imageUrl != null && !imageUrl.isEmpty()) {
            uploadedImageUrl = s3ImageService.uploadFile(imageUrl);
        }

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
                    foodRequest.getExpired_date(),
                    location,
                    null,
                    foodRequest.getMemo()
            );

            if (uploadedImageUrl != null) {
                food.setImageUrl(uploadedImageUrl);
            }

            Food savedFood = foodRepository.save(food);
            foodIds.add(savedFood.getFoodId());
        }

        return FoodIdDTO.builder()
                .foodId(foodIds)
                .build();
    }

    //food 수정
    public Long updateFood(FoodRequest foodRequest, MultipartFile foodImage) {
        Food food = foodRepository.findById(foodRequest.getFoodId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_FOOD));

        if (foodRequest.getStorageId() != null) {
            Storage storage = storageRepository.findById(foodRequest.getStorageId())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_STORAGE));
            food.setStorage(storage);
        }
        if (foodRequest.getLabel() != null) {
            Label label = labelRepository.findByName(foodRequest.getName())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_LABEL));
            food.setLabel(label);
        }
        if (foodRequest.getName() != null) {
            food.setName(foodRequest.getName());
        }
        if (foodRequest.getQuantity() != null) {
            food.setQuantity(foodRequest.getQuantity());
        }
        if (foodRequest.getUnit() != null) {
            food.setUnit(foodRequest.getUnit());
        }
        if (foodRequest.getExpired_date() != null) {
            food.setExpiredAt(foodRequest.getExpired_date());
        }
        if (foodRequest.getLocationId() != null) {
            Location location = locationRepository.findById(foodRequest.getLocationId())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_LOCATION));
            food.setLocation(location);
        }
        if (foodRequest.getAmountType() != null) {
            food.setAmountType(foodRequest.getAmountType());
        }
        if (foodRequest.getMemo() != null) {
            food.setMemo(foodRequest.getMemo());
        }

        //이미지 조정
        // 새로운 이미지가 있다면
        if (foodImage != null) {
            // 기존 이미지가 있다면
            if (food.getImageUrl() != null) {
                s3ImageService.deleteFile(food.getImageUrl());
                String newImage = s3ImageService.uploadFile(foodImage);
                food.setImageUrl(newImage);
            }
            // 기존 이미지가 없다면 등록만
            else {
                String newImage = s3ImageService.uploadFile(foodImage);
                food.setImageUrl(newImage);
            }
        }
        return food.getFoodId();
    }

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
                .uri("/stageAitracker/imageAnalyzied")
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
