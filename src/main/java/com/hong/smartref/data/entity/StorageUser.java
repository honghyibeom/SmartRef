package com.hong.smartref.data.entity;

import com.hong.smartref.data.enumerate.StorageRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "storage_user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"storage_id", "user_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StorageUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storageUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id", nullable = false)
    private Storage storage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StorageRole role;

    @Column(nullable = false, updatable = false)
    private LocalDateTime joinedAt;

    public static StorageUser create(User user, Storage storage) {
        StorageUser newFridgeUser = new StorageUser();
        newFridgeUser.user = user;
        newFridgeUser.storage = storage;
        newFridgeUser.role = StorageRole.OWNER;
        newFridgeUser.joinedAt = LocalDateTime.now();
        return newFridgeUser;
    }
}

