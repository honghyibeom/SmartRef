package com.hong.smartref.data.dto.storage;

import com.hong.smartref.data.enumerate.StorageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorageRequest {
    private Long storageId;
    private String storageName;
    private String storageColor;
    private StorageType storageType;
}
