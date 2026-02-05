package com.hong.smartref.data.dto.food;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class FoodRegisterRequest {
    private List<FoodRequest> foods;
}
