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
    private Boolean isFavorite;
    private String imageUrl;
    private String memo;
}
