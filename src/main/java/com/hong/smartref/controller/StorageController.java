package com.hong.smartref.controller;

import com.hong.smartref.config.security.UserDetailsImpl;
import com.hong.smartref.data.dto.ApiResponse;
import com.hong.smartref.data.dto.location.LocationInfo;
import com.hong.smartref.data.dto.storage.StorageInfo;
import com.hong.smartref.data.dto.storage.StorageMoveRequest;
import com.hong.smartref.data.dto.storage.StorageRequest;
import com.hong.smartref.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "스토리지 등록 api", description = "스토리지 등록한다.")
    @PostMapping("/insert")
    public ResponseEntity<ApiResponse<Long>> insertStorage(@RequestBody StorageRequest storageRequest,
                                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                ApiResponse.success("Storage 생성", storageService.insertStorage(storageRequest, userDetails))
        );
    }

    @Operation(summary = "스토리지 수정 api", description = "스토리지 수정한다.")
    @PostMapping("/update")
    public ResponseEntity<ApiResponse<Long>> updateStorage(@RequestBody StorageRequest storageRequest,
                                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                ApiResponse.success("Storage 수정", storageService.updateStorage(storageRequest, userDetails))
        );
    }

    @Operation(summary = "스토리지 삭제 api", description = "스토리지 삭제한다.")
    @PostMapping("/delete")
    public ResponseEntity<ApiResponse<Long>> deleteStorage(@RequestParam("storageId") Long storageId,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                ApiResponse.success("Storage 삭제", storageService.deleteStorage(storageId, userDetails))
        );
    }

    @Operation(summary = "스토리지 정보 다 불러오기 api", description = "스토리지 정보 다 불러오기.")
    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<StorageInfo>>> getStorageAll(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                ApiResponse.success("모든 Storage 조회", storageService.findAllStorage(userDetails))
        );
    }

    @Operation(summary = "위치 정보 다 불러오기 api", description = "위치 정보 다 불러오기.")
    @GetMapping("/location/all")
    public ResponseEntity<ApiResponse<List<LocationInfo>>> getLocationAll() {
        return ResponseEntity.ok(
                ApiResponse.success("모든 location 조회", storageService.getLocationAll())
        );
    }

    @Operation(summary = "food storage 마이그레이션 api", description = "food storage 마이그레이션.")
    @PostMapping("/swap")
    public ResponseEntity<ApiResponse<Void>> foodStorageMigration(@RequestBody StorageMoveRequest storageMoveRequest) {
        storageService.foodStorageMigration(storageMoveRequest);
        return ResponseEntity.ok(
                ApiResponse.success("수정 완료")
        );
    }
}