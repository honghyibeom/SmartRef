package com.hong.smartref.repository;

import com.hong.smartref.data.entity.Recipe;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query("""
            SELECT DISTINCT r
            FROM Recipe r
            JOIN r.ingredients ri
            WHERE ri.masterId IN :masterIds
            """)
    List<Recipe> findRecipesByMasterIds(List<Long> masterIds);

}
