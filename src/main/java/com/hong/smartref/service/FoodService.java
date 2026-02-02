package com.hong.smartref.service;

import com.hong.smartref.config.security.UserDetailsImpl;
import com.hong.smartref.data.dto.food.FoodInfo;
import com.hong.smartref.data.dto.food.FoodRequest;
import com.hong.smartref.data.entity.*;
import com.hong.smartref.exception.CustomException;
import com.hong.smartref.exception.ErrorCode;
import com.hong.smartref.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
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
    private final StorageUserRepository storageUserRepository;
    private final FoodFavoriteRepository foodFavoriteRepository;

    //food 생성
    public Long addFood(FoodRequest foodRequest, MultipartFile imageUrl) {
        // food를 등록하기 이전에 storage, label, location을 먼저 찾아야 됨.
        Label label = labelRepository.findByName(foodRequest.getLabel())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_LABEL));

        Location location = locationRepository.findById(foodRequest.getLocationId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_LOCATION));

        Storage storage = storageRepository.findById(foodRequest.getStorageId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_STORAGE));

        Food food = Food.create(
                storage, label, foodRequest.getName(), foodRequest.getAmountType(), foodRequest.getQuantity(),
                foodRequest.getUnit(), foodRequest.getExpired_date(), location, null, foodRequest.getMemo()
        );

        if (imageUrl != null) {
            String getImage = s3ImageService.uploadFile(imageUrl);
            food.setImageUrl(getImage);
        }

        Food result = foodRepository.save(food);
        return result.getFoodId();
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
        List<Food> findAllByUserId = foodRepository.findAllByUserId(user.getEmail());
        List<FoodInfo> foodInfoList = new ArrayList<>();
        for (Food food : findAllByUserId) {
            FoodInfo foodInfo = FoodInfo.builder()
                    .storageId(food.getStorage().getStorageId())
                    .label(food.getLabel() != null ? food.getLabel().getName() : null)
                    .name(food.getName())
                    .quantity(food.getQuantity())
                    .unit(food.getUnit())
                    .expiryDate(food.getExpiredAt())
                    .locationId(food.getLocation() != null
                            ? food.getLocation().getLocationId()
                            : null)
                    .isFavorite(foodFavoriteRepository.existsByUserAndFood(user.getUser(), food))
                    .imageUrl(food.getImageUrl())
                    .memo(food.getMemo())
                    .build();
            foodInfoList.add(foodInfo);
        }
        return foodInfoList;
    }
}
