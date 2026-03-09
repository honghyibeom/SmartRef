package com.hong.smartref.controller;

import com.hong.smartref.config.security.UserDetailsImpl;
import com.hong.smartref.data.dto.ApiResponse;
import com.hong.smartref.data.dto.bootstrap.BootStrapRequest;
import com.hong.smartref.service.FoodService;
import com.hong.smartref.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BootstrapController {
    private final StorageService storageService;
    private final FoodService foodService;

    @Operation(summary = "부트스트랩 api", description = "자신의 저장고, 음식, 장소 정보를 전부 조회")
    @GetMapping("/bootstrap")
    public ResponseEntity<ApiResponse<BootStrapRequest>> getAllInfo(@AuthenticationPrincipal UserDetailsImpl user) {
         BootStrapRequest bootStrapRequest = BootStrapRequest.builder()
                 .foodItems(foodService.getFoodInfo(user))
                 .locationInfo(storageService.getLocationAll())
                 .storageInfo(storageService.findAllStorage(user))
                 .build();

        return ResponseEntity.ok(
                ApiResponse.success("조회 성공", bootStrapRequest)
        );
    }

}
