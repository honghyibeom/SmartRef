package com.hong.smartref.data.entity;

import com.hong.smartref.data.enumerate.DefaultColor;
import com.hong.smartref.data.enumerate.DefaultFridgeName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fridge")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Fridge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fridgeId;

    @Column(nullable = false)
    private String fridgeName;

    @Column(nullable = false)
    private String fridgeColor;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "fridge", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Food> foodList = new ArrayList<>();

    @OneToMany(mappedBy = "fridge", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FridgeUser> fridgeUserList = new ArrayList<>();

    public static Fridge create(
            String fridgeName,
            String fridgeColor
    ) {
        Fridge fridge = new Fridge();
        fridge.fridgeName = fridgeName == null ? DefaultFridgeName.getRandomFridgeName() : fridgeName;
        fridge.fridgeColor = fridgeColor == null ? DefaultColor.getRandomColor() :fridgeColor;
        fridge.createdAt = LocalDateTime.now();

        return fridge;
    }

}

