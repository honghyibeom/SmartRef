package com.hong.smartref.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recipe_step")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RecipeStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stepId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    private int stepNumber;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String imageUrl;
}

