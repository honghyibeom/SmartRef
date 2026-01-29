package com.hong.smartref.repository;

import com.hong.smartref.data.entity.StorageUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageUserRepository extends JpaRepository<StorageUser, Long> {
}
