package com.hong.smartref.data.dto.storage;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StorageMoveRequest {
    private Long nextStorageId;
    private List<LocationMoveRequest> list;
}
