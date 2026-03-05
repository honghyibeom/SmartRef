package com.hong.smartref.data.entity;

import com.hong.smartref.data.enumerate.DefaultColor;
import com.hong.smartref.data.enumerate.DefaultStorageColor;
import com.hong.smartref.data.enumerate.DefaultStorageName;
import com.hong.smartref.data.enumerate.StorageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "storage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storageId;

    @Column(nullable = false)
    private String storageName;

    @Column(nullable = false)
    private String storageColor;

    @Enumerated(EnumType.STRING)
    private StorageType storageType;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "storage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Food> foodList = new ArrayList<>();

    @OneToMany(mappedBy = "storage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StorageUser> storageUserList = new ArrayList<>();

    public static Storage create(
            String storageName,
            String storageColor,
            StorageType storageType
    ) {
        Storage storage = new Storage();
        storage.storageName = storageName;
        storage.storageColor = storageColor;
        storage.storageType = storageType;
        storage.createdAt = LocalDateTime.now();
        return storage;
    }

}

