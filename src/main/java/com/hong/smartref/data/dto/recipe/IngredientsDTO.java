package com.hong.smartref.data.dto.recipe;

import com.hong.smartref.data.enumerate.AmountType;
import lombok.Data;

@Data
public class IngredientsDTO {
    private Long masterId;
    private AmountType amountType;
    private Integer q;
    private String u;
}
