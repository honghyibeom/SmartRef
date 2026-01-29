package com.hong.smartref.data.entity;

import com.hong.smartref.data.enumerate.StorageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "location")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    @Column
    private StorageType storageType;

    @Column
    private String locationName;

    @Column
    private String locationColor;

}
