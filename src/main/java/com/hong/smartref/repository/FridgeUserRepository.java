package com.hong.smartref.repository;

import com.hong.smartref.data.entity.FridgeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FridgeUserRepository extends JpaRepository<FridgeUser, Long> {
}
