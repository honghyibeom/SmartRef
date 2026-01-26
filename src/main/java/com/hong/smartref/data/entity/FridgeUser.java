package com.hong.smartref.data.entity;

import com.hong.smartref.data.enumerate.FridgeRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "fridge_user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"fridge", "user"})
        }
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FridgeUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fridgeUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fridge_id", nullable = false)
    private Fridge fridge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FridgeRole role;

    @Column(nullable = false, updatable = false)
    private LocalDateTime joinedAt;
}

