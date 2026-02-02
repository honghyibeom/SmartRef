package com.hong.smartref.repository;

import com.hong.smartref.data.entity.Food;
import com.hong.smartref.data.entity.FoodFavorite;
import com.hong.smartref.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodFavoriteRepository extends JpaRepository<FoodFavorite, Long> {
    Optional<FoodFavorite> findByFoodAndUser(Food food, User user);

    Boolean existsByUserAndFood(User user, Food food);
}
