package com.hong.smartref.repository;

import com.hong.smartref.data.entity.Recipe;
import com.hong.smartref.data.entity.RecipeSave;
import com.hong.smartref.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeSaveRepository extends JpaRepository<RecipeSave, Long> {
    Optional<RecipeSave> findByUserAndRecipe(User user, Recipe recipe);

    boolean existsRecipeSaveByRecipeAndUser(Recipe recipe, User user);

    Page<RecipeSave> findByUser(User user, Pageable pageable);

}
