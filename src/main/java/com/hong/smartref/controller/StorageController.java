package com.hong.smartref.controller;

import com.hong.smartref.config.security.UserDetailsImpl;
import com.hong.smartref.data.dto.ApiResponse;
import com.hong.smartref.data.dto.location.LocationInfo;
import com.hong.smartref.data.dto.storage.StorageInfo;
import com.hong.smartref.data.dto.storage.StorageRequest;
import com.hong.smartref.service.StorageService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/storage")
public class StorageController {
    private final StorageService storageService;

    @PostMapping("/insert")
    public ResponseEntity<ApiResponse<Long>> insertStorage(@RequestBody StorageRequest storageRequest,
                                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                ApiResponse.success("Storage 생성", storageService.insertStorage(storageRequest, userDetails))
        );
    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponse<Long>> updateStorage(@RequestBody StorageRequest storageRequest,
                                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                ApiResponse.success("Storage 수정", storageService.updateStorage(storageRequest, userDetails))
        );
    }

    @PostMapping("/delete")
    public ResponseEntity<ApiResponse<Long>> deleteStorage(@RequestParam("storageId") Long storageId,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                ApiResponse.success("Storage 삭제", storageService.deleteStorage(storageId, userDetails))
        );
    }
    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<StorageInfo>>> getStorageAll(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                ApiResponse.success("모든 Storage 조회", storageService.findAllStorage(userDetails))
        );
    }

    @GetMapping("/location/all")
    public ResponseEntity<ApiResponse<List<LocationInfo>>> getLocationAll() {
        return ResponseEntity.ok(
                ApiResponse.success("모든 location 조회", storageService.getLocationAll())
        );
    }
}