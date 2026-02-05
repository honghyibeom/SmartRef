package com.hong.smartref.data.dto.food;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FoodIdDTO {
    private List<Long> foodId;
}
