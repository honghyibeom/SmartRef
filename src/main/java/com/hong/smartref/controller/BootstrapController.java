package com.hong.smartref.controller;

import com.hong.smartref.config.security.UserDetailsImpl;
import com.hong.smartref.data.dto.ApiResponse;
import com.hong.smartref.data.dto.bootstrap.BootStrapResponse;
import com.hong.smartref.data.dto.bootstrap.LocationStorageResponse;
import com.hong.smartref.data.dto.food.FoodInfo;
import com.hong.smartref.data.dto.location.LocationInfo;
import com.hong.smartref.data.dto.storage.StorageInfo;
import com.hong.smartref.service.FoodService;
import com.hong.smartref.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BootstrapController {
    private final StorageService storageService;
    private final FoodService foodService;

    @Operation(summary = "부트스트랩 api", description = "자신의 저장고, 음식, 장소 정보를 전부 조회")
    @GetMapping("/bootstrap")
    public ResponseEntity<ApiResponse<BootStrapResponse>> getAllInfo(@AuthenticationPrincipal UserDetailsImpl user) {
         BootStrapResponse bootStrapResponse = BootStrapResponse.builder()
                 .foodItems(foodService.getFoodInfo(user))
                 .locationInfo(storageService.getLocationAll())
                 .storageInfo(storageService.findAllStorage(user))
                 .build();

        return ResponseEntity.ok(
                ApiResponse.success("조회 성공", bootStrapResponse)
        );
    }

    @Operation(summary = "모든 음식 조회 api", description = "음식 정보를 전부 조회")
    @GetMapping("/foodItem")
    public ResponseEntity<ApiResponse<List<FoodInfo>>> getAllFoodItem(@AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(
                ApiResponse.success("조회 성공", foodService.getFoodInfo(user))
        );
    }

    @Operation(summary = "모든 저장소 조회 api", description = "저장소 정보를 전부 조회")
    @GetMapping("/storage")
    public ResponseEntity<ApiResponse<List<StorageInfo>>> getAllStorage(@AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(
                ApiResponse.success("조회 성공", storageService.findAllStorage(user))
        );
    }

    @Operation(summary = "모든 위치 조회 api", description = "위치 정보를 전부 조회")
    @GetMapping("/location")
    public ResponseEntity<ApiResponse<List<LocationInfo>>> getAllLocation() {
        return ResponseEntity.ok(
                ApiResponse.success("조회 성공", storageService.getLocationAll())
        );
    }

    @Operation(summary = "모든 저장소,위치 조회 api", description = "저장소 및 위치 정보를 전부 조회")
    @GetMapping("/storageLocation")
    public ResponseEntity<ApiResponse<LocationStorageResponse>> getAllStorageLocation(@AuthenticationPrincipal UserDetailsImpl user) {
        LocationStorageResponse bootStrapResponse = LocationStorageResponse.builder()
                .locationInfo(storageService.getLocationAll())
                .storageInfo(storageService.findAllStorage(user))
                .build();
        return ResponseEntity.ok(
                ApiResponse.success("조회 성공", bootStrapResponse)
        );
    }

}
