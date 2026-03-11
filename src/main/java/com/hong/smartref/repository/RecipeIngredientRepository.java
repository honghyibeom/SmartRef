package com.hong.smartref.repository;

import com.hong.smartref.data.entity.Recipe;
import com.hong.smartref.data.entity.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {
    List<RecipeIngredient> findByMasterIdIn(List<Long> masterIds);

}
