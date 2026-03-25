package com.hong.smartref.data.entity;

import com.hong.smartref.data.enumerate.AmountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "food")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id", nullable = false)
    private Storage storage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "label_id")
    private Label label;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AmountType amountType;

    private BigDecimal quantity; // 정해진 량

    private String unit; // 있다면 amountType이 Method다.

    private LocalDate expiredAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    private String imageUrl;

    private String memo;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private Long masterId;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodFavorite> foodFavoriteList = new ArrayList<>();


    public static Food create(
            Storage storage,
            Label label,
            String name,
            AmountType amountType,
            BigDecimal quantity,
            String unit,
            LocalDate expiredAt,
            Location location,
            String imageUrl,
            String memo,
            Long masterId
    ) {
        Food food = new Food();
        food.storage = storage;
        food.label = label;
        food.name = name;
        food.amountType = amountType;
        food.quantity = quantity;
        food.unit = unit;
        food.expiredAt = expiredAt;
        food.location = location;
        food.imageUrl = imageUrl;
        food.memo = memo;
        food.createdAt = LocalDateTime.now();
        food.masterId = masterId;
        return food;
    }

    public void update(
            Storage storage,
            Label label,
            Long masterId,
            String name,
            BigDecimal quantity,
            String unit,
            LocalDate expiryDate,
            Location location,
            AmountType amountType,
            String memo,
            String imageUrl
    ) {
        if (storage != null) this.storage = storage;
        if (label != null) this.label = label;
        if (masterId != null) this.masterId = masterId;
        if (name != null) this.name = name;
        if (quantity != null) this.quantity = quantity;
        if (unit != null) this.unit = unit;
        if (expiryDate != null) this.expiredAt = expiryDate;
        if (location != null) this.location = location;
        if (amountType != null) this.amountType = amountType;
        if (memo != null) this.memo = memo;
        this.imageUrl = imageUrl;
    }

}

