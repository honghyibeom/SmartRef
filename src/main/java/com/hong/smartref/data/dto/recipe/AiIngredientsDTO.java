package com.hong.smartref.data.dto.recipe;

import com.hong.smartref.data.enumerate.AmountType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AiIngredientsDTO {
    private Long masterId;
    private String name;
    private Integer q;
    private String u;
}
