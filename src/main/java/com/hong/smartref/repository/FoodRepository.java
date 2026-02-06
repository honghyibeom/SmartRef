package com.hong.smartref.repository;

import com.hong.smartref.data.dto.food.FoodInfo;
import com.hong.smartref.data.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    @Query("""
        SELECT new com.hong.smartref.data.dto.food.FoodInfo(
            f.foodId,
            s.storageId,
              l.name,
              f.name,
              f.quantity,
              f.unit,
              f.expiredAt,
              loc.locationId,
              CASE WHEN ff.foodFavoriteId IS NOT NULL THEN true ELSE false END,
              f.imageUrl,
              f.memo,
              f.amountType
        )
        FROM Food f
        JOIN f.storage s
        JOIN s.storageUserList su
        LEFT JOIN f.label l
        LEFT JOIN f.location loc
        LEFT JOIN FoodFavorite ff
            ON ff.food = f AND ff.user.email = :email
        WHERE su.user.email = :email
    """)
    List<FoodInfo> findFoodInfoByUserId(@Param("email") String email);
}
