package com.hong.smartref.data.dto.food;

import com.hong.smartref.data.enumerate.AmountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
public class FoodInfo {
    private Long foodId;
    private Long storageId;
    private String label;
    private String name;
    private BigDecimal quantity;
    private String unit;
    private LocalDate expiryDate;
    private Long locationId;
    private Boolean isFavorite;
    private String imageUrl;
    private String memo;
    private AmountType amountType;
    private Long masterId;

    public FoodInfo(
            Long foodId,
            Long storageId,
            String label,
            String name,
            BigDecimal quantity,
            String unit,
            LocalDate expiryDate,
            Long locationId,
            Boolean isFavorite,
            String imageUrl,
            String memo,
            AmountType amountType,
            Long masterId
    ) {
        this.foodId = foodId;
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
        this.amountType = amountType;
        this.masterId = masterId;
    }
}
