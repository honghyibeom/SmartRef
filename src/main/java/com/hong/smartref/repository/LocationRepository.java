package com.hong.smartref.repository;

import com.hong.smartref.data.entity.Location;
import com.hong.smartref.data.enumerate.StorageTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findByLocationId(Long locationId);

    @Query(value = """
    SELECT l.*
    FROM location l
    JOIN storage_location sl
        ON l.location_id = sl.location_id
    JOIN storage_type st
        ON sl.storage_type_id = st.storage_type_id
    WHERE st.storage_type_enum = :type
    LIMIT 1
""", nativeQuery = true)
    Optional<Location> findTrashLocation(@Param("type") String type);
}
