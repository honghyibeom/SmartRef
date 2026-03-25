package com.hong.smartref.repository;

import com.hong.smartref.data.entity.Location;
import com.hong.smartref.data.entity.StorageLocation;
import com.hong.smartref.data.entity.StorageType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface StorageLocationRepository extends CrudRepository<StorageLocation, Long> {

    @Query("""
            select sl
            from StorageLocation sl
            join fetch sl.location
            join fetch sl.storageType
            where sl.storageType in :types
            """)
    List<StorageLocation> findByStorageTypeInFetch(Collection<StorageType> types);

    boolean existsByStorageTypeAndLocation(StorageType storageType, Location location);
}
