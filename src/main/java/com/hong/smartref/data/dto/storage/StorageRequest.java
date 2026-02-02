package com.hong.smartref.data.dto.storage;

import com.hong.smartref.data.enumerate.StorageType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StorageRequest {
    private Long storageId;
    private String storageName;
    private String StorageColor;
    private StorageType storageType;
}
