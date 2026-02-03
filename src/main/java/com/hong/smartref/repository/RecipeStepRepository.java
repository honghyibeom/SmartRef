package com.hong.smartref.repository;

import com.hong.smartref.data.entity.RecipeStep;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeStepRepository extends JpaRepository<RecipeStep, Long> {

}
