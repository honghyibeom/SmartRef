package com.hong.smartref.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "region")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Region {
    @Id
    private String placeId;

    // 2. 마커 표시용 중심 좌표 (통계 핀 꽂기)
    private Double centerLat;
    private Double centerLng;

    // 3. 카메라 줌인(fitBounds)용 영역 좌표 (스무스한 화면 이동)
    private Double viewportNeLat;
    private Double viewportNeLng;
    private Double viewportSwLat;
    private Double viewportSwLng;

}
