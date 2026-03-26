package com.hong.smartref.repository;

import com.hong.smartref.data.entity.StorageType;
import com.hong.smartref.data.enumerate.StorageTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StorageTypeRepository extends JpaRepository<StorageType, Long> {
    Optional<StorageType> findByStorageTypeId(Long storageTypeId);

    Optional<StorageType> findByStorageTypeEnum(StorageTypeEnum storageTypeEnum);
}
