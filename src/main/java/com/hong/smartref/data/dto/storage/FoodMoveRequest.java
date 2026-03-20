package com.hong.smartref.data.dto.storage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodMoveRequest {
    private Long foodId;
    private BigDecimal quantity;

}
