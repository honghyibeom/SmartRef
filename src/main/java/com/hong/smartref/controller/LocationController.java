package com.hong.smartref.controller;

import com.hong.smartref.data.dto.location.CurrentLocationRequest;
import com.hong.smartref.data.dto.location.LocationResponse;
import com.hong.smartref.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/location")
public class LocationController {
    private final LocationService locationService;

    @Operation(summary = "구글맵 요청 api", description = "구글 맵 요청 후 정보 전달")
    @PostMapping("/currentLocation")
    public ResponseEntity<LocationResponse> getLocation(@RequestBody CurrentLocationRequest request) {
        return ResponseEntity.ok(locationService.getLocation(request));
    }

}
