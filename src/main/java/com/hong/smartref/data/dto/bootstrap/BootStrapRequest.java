package com.hong.smartref.data.dto.bootstrap;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hong.smartref.data.dto.food.FoodInfo;
import com.hong.smartref.data.dto.location.LocationInfo;
import com.hong.smartref.data.dto.storage.StorageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BootStrapRequest {
    @JsonProperty("storage_info")
    private List<StorageInfo> storageInfo;

    @JsonProperty("location_info")
    private List<LocationInfo> locationInfo;

    @JsonProperty("food_items")
    private List<FoodInfo> foodItems;

}