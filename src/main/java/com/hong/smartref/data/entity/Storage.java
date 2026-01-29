package com.hong.smartref.data.entity;

import com.hong.smartref.data.enumerate.DefaultColor;
import com.hong.smartref.data.enumerate.DefaultFridgeName;
import com.hong.smartref.data.enumerate.StorageRole;
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

    @Column(nullable = false)
    private StorageType storageType;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "storage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Food> foodList = new ArrayList<>();

    @OneToMany(mappedBy = "storage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StorageUser> storageUserList = new ArrayList<>();

    public static Storage create(
            String fridgeName,
            String fridgeColor,
            StorageType storageType
    ) {
        Storage storage = new Storage();
        storage.storageName = fridgeName == null ? DefaultFridgeName.getRandomFridgeName() : fridgeName;
        storage.storageColor = fridgeColor == null ? DefaultColor.getRandomColor() :fridgeColor;
        storage.storageType = storageType;
        storage.createdAt = LocalDateTime.now();
        return storage;
    }

}

