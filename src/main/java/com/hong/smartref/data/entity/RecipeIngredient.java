package com.hong.smartref.data.entity;

import com.hong.smartref.data.enumerate.AmountType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recipe_ingredient")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RecipeIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ingredientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @Column(nullable = false)
    private Long masterId;

    private String unit;

    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AmountType amountType;

}
