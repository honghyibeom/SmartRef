package com.hong.smartref.data.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialLoginRequest {
    private String code;
    private String typeOfPlatform;
}
