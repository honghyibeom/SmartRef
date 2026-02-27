package com.hong.smartref.data.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDeviceRequest {
    private String fcmToken;
    private String deviceType;
}
