package com.hong.smartref.service;

import com.hong.smartref.config.security.UserDetailsImpl;
import com.hong.smartref.data.entity.Food;
import com.hong.smartref.data.entity.FoodFavorite;
import com.hong.smartref.exception.CustomException;
import com.hong.smartref.exception.ErrorCode;
import com.hong.smartref.repository.FoodFavoriteRepository;
import com.hong.smartref.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodFavoriteService {
    private final FoodFavoriteRepository foodFavoriteRepository;
    private final FoodRepository foodRepository;

    public Long addFavorite(Long foodId, UserDetailsImpl user) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_FOOD));
        FoodFavorite foodFavorite = FoodFavorite.create(user.getUser(), food);
        foodFavoriteRepository.save(foodFavorite);

        return food.getFoodId();
    }

    public Long deleteFavorite(Long foodId, UserDetailsImpl user) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_FOOD));

        FoodFavorite foodFavorite = foodFavoriteRepository.findByFoodAndUser(food, user.getUser())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_FOOD_FAVORITE));

        foodFavoriteRepository.delete(foodFavorite);

        return food.getFoodId();
    }
}
