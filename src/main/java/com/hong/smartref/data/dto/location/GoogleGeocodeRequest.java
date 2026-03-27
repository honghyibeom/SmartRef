package com.hong.smartref.data.dto.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoogleGeocodeRequest {
    private String latlng; // Lat, Lng;
    private String key;        // API KEY
    private String language;   // ko
    private String resultType; // sublocality|locality|neighborhood
}
