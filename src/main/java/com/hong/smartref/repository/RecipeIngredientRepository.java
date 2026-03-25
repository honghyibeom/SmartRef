package com.hong.smartref.repository;

import com.hong.smartref.data.entity.Recipe;
import com.hong.smartref.data.entity.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {
    List<RecipeIngredient> findByMasterIdIn(List<Long> masterIds);

    @Query("""
        SELECT ri FROM RecipeIngredient ri
        WHERE ri.recipe.recipeId IN :recipeIds
        """)
    List<RecipeIngredient> findByRecipeIds(@Param("recipeIds") List<Long> recipeIds);

    void deleteByRecipe(Recipe recipe);
}
