package com.hong.smartref.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeLike extends JpaRepository<RecipeLike, Long> {
}
