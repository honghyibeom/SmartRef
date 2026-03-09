package com.hong.smartref.data.entity;

import com.hong.smartref.data.enumerate.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "recipe")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column
    private String writtenLang;

    @Column
    private String stayRegion;

    @Column
    private boolean isUseLocalData;

    // ===== Context / Filter Enums =====
    @Enumerated(EnumType.STRING)
    private RecipeType recipeType;

    @Enumerated(EnumType.STRING)
    private RecipeCategory category;

    @Enumerated(EnumType.STRING)
    private CookingMethod cookingMethod;

    @Enumerated(EnumType.STRING)
    private CookingTechnique technique;

    @Enumerated(EnumType.STRING)
    private DietaryGoal dietaryGoal;

    @Enumerated(EnumType.STRING)
    private DietaryRestriction dietaryRestriction;

    @Enumerated(EnumType.STRING)
    private PrimaryIngredient primaryIngredient;

    @Enumerated(EnumType.STRING)
    private Occasion occasion;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    private CookingTime cookingTime;

    @Enumerated(EnumType.STRING)
    private CuisineRegion cuisineRegion;

    @Enumerated(EnumType.STRING)
    private Servings servings;

    @Enumerated(EnumType.STRING)
    private RequiredTool requiredTool;

    // ===== Meta =====
    @Enumerated(EnumType.STRING)
    private RecipeSource source;

    @Enumerated(EnumType.STRING)
    private RecipeVisibility visibility;

    @Builder.Default
    private int likeCount = 0;

    @Builder.Default
    private int viewCount = 0;

    private LocalDateTime createdAt;

    // ===== Relations =====
    @OneToMany(
            mappedBy = "recipe",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderBy("orderIndex ASC")
    private List<RecipeIngredient> ingredients;

    @OneToMany(
            mappedBy = "recipe",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderBy("stepNumber ASC")
    private List<RecipeStep> steps;

    @OneToMany(
            mappedBy = "recipe",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<RecipeSave> recipeSaveList;

    // ===== Factory =====

}
