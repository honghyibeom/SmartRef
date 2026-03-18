package com.hong.smartref.data.entity;

import com.hong.smartref.data.enumerate.StorageTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "storage_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StorageType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storageTypeId;

    @Enumerated(EnumType.STRING)
    private StorageTypeEnum storageTypeEnum;

    @OneToMany(mappedBy = "storageType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StorageLocation> storageLocationList = new ArrayList<>();
}
