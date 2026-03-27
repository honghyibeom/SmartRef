package com.hong.smartref.data.dto.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentLocationRequest {
    private Double lat; // 위도
    private Double lng; // 경도
    private String language; // ko, en 등

}
