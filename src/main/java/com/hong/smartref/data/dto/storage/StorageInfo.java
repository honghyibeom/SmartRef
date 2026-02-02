package com.hong.smartref.data.dto.storage;

import com.hong.smartref.data.enumerate.StorageType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StorageInfo {
    private Long locationId;
    private StorageType storageType;
    private String storageName;
    private String storageColor;
}
