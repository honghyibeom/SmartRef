package com.hong.smartref.repository;

import com.hong.smartref.data.entity.StorageLocation;
import com.hong.smartref.data.entity.StorageType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface StorageLocationRepository extends CrudRepository<StorageLocation, Long> {
    List<StorageLocation> findByStorageType(StorageType storageType);

    List<StorageLocation> findByStorageTypeIn(Collection<StorageType> storageTypes);
}
