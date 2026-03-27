package com.hong.smartref.data.dto.location;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LocationResponse {
    private String placeId;
    private String city;     // 시 (서울)
    private String district; // 구 (강남구)

    private Double centerLat;
    private Double centerLng;

    private Double viewportNeLat;
    private Double viewportNeLng;
    private Double viewportSwLat;
    private Double viewportSwLng;

    private String country;
}
