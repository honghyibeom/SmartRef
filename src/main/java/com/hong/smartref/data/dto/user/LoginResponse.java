package com.hong.smartref.data.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDTO {
    private String accessToken;
    private String refreshToken;
    private String validateTime;
    private String email;
}
