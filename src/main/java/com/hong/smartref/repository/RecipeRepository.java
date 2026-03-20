package com.hong.smartref.repository;

import com.hong.smartref.data.entity.Recipe;
import com.hong.smartref.data.enumerate.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long>{

    @Query("""
SELECT DISTINCT r FROM Recipe r
JOIN r.ingredients i
WHERE (:masterIds IS NULL OR i.masterId IN :masterIds)
AND (:recipeType IS NULL OR r.recipeType = :recipeType)
AND (:cookingMethod IS NULL OR r.cookingMethod = :cookingMethod)
AND (:technique IS NULL OR r.technique = :technique)
AND (:dietaryGoal IS NULL OR r.dietaryGoal = :dietaryGoal)
AND (:dietaryRestriction IS NULL OR r.dietaryRestriction = :dietaryRestriction)
AND (:primaryIngredient IS NULL OR r.primaryIngredient = :primaryIngredient)
AND (:category IS NULL OR r.category = :category)
AND (:occasion IS NULL OR r.occasion = :occasion)
AND (:difficulty IS NULL OR r.difficulty = :difficulty)
AND (:cookingTime IS NULL OR r.cookingTime = :cookingTime)
AND (:servings IS NULL OR r.servings = :servings)
AND (:requiredTool IS NULL OR r.requiredTool = :requiredTool)
AND (:cuisineRegion IS NULL OR r.cuisineRegion = :cuisineRegion)
AND (:source IS NULL OR r.source = :source)
AND (:visibility IS NULL OR r.visibility = :visibility)
AND (:searchValue IS NULL OR r.title LIKE CONCAT('%', :searchValue, '%'))
""")
    Page<Recipe> searchRecipes(
            @Param("masterIds") List<Long> masterIds,
            @Param("recipeType") RecipeType recipeType,
            @Param("cookingMethod") CookingMethod cookingMethod,
            @Param("technique") CookingTechnique technique,
            @Param("dietaryGoal") DietaryGoal dietaryGoal,
            @Param("dietaryRestriction") DietaryRestriction dietaryRestriction,
            @Param("primaryIngredient") PrimaryIngredient primaryIngredient,
            @Param("category") RecipeCategory category,
            @Param("occasion") Occasion occasion,
            @Param("difficulty") Difficulty difficulty,
            @Param("cookingTime") CookingTime cookingTime,
            @Param("servings") Servings servings,
            @Param("requiredTool") RequiredTool requiredTool,
            @Param("cuisineRegion") CuisineRegion cuisineRegion,
            @Param("source") RecipeSource source,
            @Param("visibility") RecipeVisibility visibility,
            @Param("searchValue") String searchValue,
            Pageable pageable
    );
}
