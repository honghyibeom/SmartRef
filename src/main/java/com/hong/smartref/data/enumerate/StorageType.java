package com.hong.smartref.data.enumerate;

import lombok.Getter;

import java.util.List;

@Getter
public enum StorageType {
    FRIDGE(List.of(1L,2L,3L,4L)),
    PANTRY(List.of(5L,6L,7L)),
    WINE_CELLAR(List.of(8L,9L,10L));

    private final List<Long> locationIds;

    StorageType(List<Long> locationIds) {
        this.locationIds = locationIds;
    }

}
