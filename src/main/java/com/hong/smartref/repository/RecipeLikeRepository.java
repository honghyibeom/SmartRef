package com.hong.smartref.repository;

import com.hong.smartref.data.entity.Recipe;
import com.hong.smartref.data.entity.RecipeLike;
import com.hong.smartref.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeLikeRepository extends JpaRepository<RecipeLike, Long> {
    Optional<RecipeLike> findByUserAndRecipe(User user, Recipe recipe);
}
