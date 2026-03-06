package com.hong.smartref.data.dto.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationInfo {
    private Long locationId;
    private String locationColor;
    private String locationName;
}
