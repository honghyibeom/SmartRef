package com.hong.smartref.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "storage_location",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_storage_location",
                        columnNames = {"storage_type_id", "location_id"}
                )
        }
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StorageLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storageLocationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_type_id")
    private StorageType storageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

}
