package com.hong.smartref.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "foodFavorite",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "food_id"})
        }
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodFavorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodFavoriteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;
}