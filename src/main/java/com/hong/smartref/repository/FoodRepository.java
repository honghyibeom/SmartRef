package com.hong.smartref.repository;

import com.hong.smartref.data.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    @Query("""
        SELECT f
        FROM Food f
        JOIN f.storage s
        JOIN s.storageUserList su
        WHERE su.user.email = :email
    """)
    List<Food> findAllByUserId(@Param("email") String email);
}
