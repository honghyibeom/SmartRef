package com.hong.smartref.data.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "recipe_like",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_recipe_like_user_recipe",
                        columnNames = {"user_id", "recipe_id"}
                )
        },
        indexes = {
                @Index(name = "idx_recipe_like_recipe", columnList = "recipe_id"),
                @Index(name = "idx_recipe_like_user", columnList = "user_id")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RecipeLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    // ===== Relations =====
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    // User 엔티티와 직접 연관 맺어도 되고
    // 지금은 String FK 로 단순화
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime createdAt;

    // ===== Factory =====
    public static RecipeLike create(Recipe recipe, User user) {
        return RecipeLike.builder()
                .recipe(recipe)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
