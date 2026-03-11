package com.hong.smartref.data.dto.recipe;

import com.hong.smartref.data.enumerate.AmountType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IngredientsDTO {
    private Long masterId;
    private AmountType amountType;
    private Integer quantity;
    private String unit;
}
