package com.hong.smartref.data.entity;

import com.hong.smartref.data.enumerate.StorageTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
