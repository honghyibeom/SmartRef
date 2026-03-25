package com.hong.smartref.repository;

import com.hong.smartref.data.entity.Storage;
import com.hong.smartref.data.entity.StorageType;
import com.hong.smartref.data.entity.StorageUser;
import com.hong.smartref.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StorageUserRepository extends JpaRepository<StorageUser, Long> {
    Optional<StorageUser> findByUserAndStorage(User user, Storage storage);

    Integer countByStorage(Storage storage);

    Optional<StorageUser> findTopByStorageOrderByJoinedAtAsc(Storage storage);

    @Query("""
            select su from StorageUser su
            join fetch su.storage s
            join fetch s.storageType
            where su.user = :user
            """)
    List<StorageUser> findByUserWithStorage(User user);
}
