package com.hong.smartref.data.dto.food;

import com.hong.smartref.data.enumerate.AmountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodRequest {
    private Long foodId;
    private Long storageId;
    private String label;
    private String name;
    private Double quantity;
    private String unit;
    private LocalDate expired_date;
    private Long locationId;
    private String imageUrl;
    private String memo;
    private AmountType amountType;
    private Long masterId;
    private Boolean isFavorite;
}
