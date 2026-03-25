package com.hong.smartref.repository;

import com.hong.smartref.data.entity.Recipe;
import com.hong.smartref.data.entity.RecipeStep;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeStepRepository extends JpaRepository<RecipeStep, Long> {

    void deleteByRecipe(Recipe recipe);

    @Query("""
        SELECT rs FROM RecipeStep rs
        WHERE rs.recipe.recipeId IN :recipeIds
        ORDER BY rs.recipe.recipeId, rs.stepNumber
        """)
    List<RecipeStep> findByRecipeIds(@Param("recipeIds") List<Long> recipeIds);
}
