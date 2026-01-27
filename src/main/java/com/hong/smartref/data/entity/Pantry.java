package com.hong.smartref.data.entity;

import com.hong.smartref.data.enumerate.DefaultColor;
import com.hong.smartref.data.enumerate.DefaultFridgeName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pantry")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Pantry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pantryId;

    @Column(nullable = false)
    private String pantryName;

    @Column(nullable = false)
    private String pantryColor;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "pantry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Food> foodList = new ArrayList<>();

    @OneToMany(mappedBy = "pantry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PantryUser> pantryUserList = new ArrayList<>();

    public static Pantry create(
            String pantryName,
            String pantryColor
    ) {
        Pantry pantry = new Pantry();
        pantry.pantryName = pantryName == null ? DefaultFridgeName.getRandomFridgeName() : pantryName;
        pantry.pantryColor = pantryColor == null ? DefaultColor.getRandomColor() :pantryColor;
        pantry.createdAt = LocalDateTime.now();

        return pantry;
    }

}

