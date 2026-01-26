package com.hong.smartref.repository;

import com.hong.smartref.data.entity.FoodFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodFavoriteRepository extends JpaRepository<FoodFavorite, Long> {
}
