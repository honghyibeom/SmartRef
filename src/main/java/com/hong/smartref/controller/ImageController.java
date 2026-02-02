package com.hong.smartref.controller;

import com.hong.smartref.data.dto.ApiResponse;
import com.hong.smartref.exception.CustomException;
import com.hong.smartref.exception.ErrorCode;
import com.hong.smartref.service.S3ImageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final S3ImageService s3ImageService;
    @Operation(summary = "이미지 업로드 ",description = "이미지 업로드 api")
    @PostMapping(value = "/ask/s3")
    public ResponseEntity<ApiResponse<List<String>>> s3Upload(@RequestPart List<MultipartFile> images){
        if (images == null) {
            throw new CustomException(ErrorCode.EMPTY_FILE_EXCEPTION);
        }
        return ResponseEntity.ok(
                ApiResponse.success("이미지 리스트 전송", s3ImageService.uploadFileList(images))
        );
    }
}
