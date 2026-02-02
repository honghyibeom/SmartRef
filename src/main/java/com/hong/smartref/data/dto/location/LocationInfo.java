package com.hong.smartref.data.dto.location;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LocationInfo {
    private Long locationId;
    private String locationColor;
    private String locationName;
}
