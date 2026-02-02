package com.hong.smartref.data.dto.food;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class FoodInfo {
    private Long storageId;
    private String label;
    private String name;
    private Double quantity;
    private String unit;
    private LocalDate expiryDate;
    private Long locationId;
    private boolean isFavorite;
    private String imageUrl;
    private String memo;

    public FoodInfo(
            Long storageId,
            String label,
            String name,
            Double quantity,
            String unit,
            LocalDate expiryDate,
            Long locationId,
            Boolean isFavorite,
            String imageUrl,
            String memo
    ) {
        this.storageId = storageId;
        this.label = label;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = expiryDate;
        this.locationId = locationId;
        this.isFavorite = isFavorite;
        this.imageUrl = imageUrl;
        this.memo = memo;
    }
}
