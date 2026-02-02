package com.hong.smartref.data.dto.food;

import com.hong.smartref.data.enumerate.AmountType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class FoodRequest {
    private Long foodId;
    private Long storageId;
    private String label;
    private String name;
    private Double quantity;
    private String unit;
    private LocalDate expired_date;
    private Long locationId;
    private String ImageUrl;
    private String memo;
    private AmountType amountType;
}
