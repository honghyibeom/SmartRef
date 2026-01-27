package com.hong.smartref.repository;

import com.hong.smartref.data.entity.FridgeUser;
import com.hong.smartref.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FridgeUserRepository extends JpaRepository<FridgeUser, Long> {
    List<FridgeUser> findFridgeUserByUser(User user);
}
