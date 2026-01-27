package com.hong.smartref.data.entity;

import com.hong.smartref.data.enumerate.FridgeRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "fridge_user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"pantry_id", "user_id"})
        }
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PantryUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pantryUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pantry_id", nullable = false)
    private Pantry pantry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FridgeRole role;

    @Column(nullable = false, updatable = false)
    private LocalDateTime joinedAt;

    public static PantryUser create(User user, Pantry pantry) {
        PantryUser newFridgeUser = new PantryUser();
        newFridgeUser.user = user;
        newFridgeUser.pantry = pantry;
        return newFridgeUser;
    }
}

