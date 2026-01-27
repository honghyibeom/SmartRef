package com.hong.smartref.data.dto.Fridge;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FridgeRequest {
    private String refName;
    private String refColor;
}
