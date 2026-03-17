package com.hong.smartref.data.dto.storage;

import com.hong.smartref.data.enumerate.StorageTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorageInfo {
    private Long storageId;
    private StorageTypeEnum storageTypeEnum;
    private String storageColor;
    private String storageName;
    private List<Long> locationIds;
}
