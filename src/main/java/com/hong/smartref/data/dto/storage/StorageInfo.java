package com.hong.smartref.data.dto.storage;

import com.hong.smartref.data.enumerate.StorageType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StorageInfo {
    private Long StorageId;
    private StorageType storageType;
    private String storageColor;
    private String storageName;
    private List<Long> locationIds;
}
