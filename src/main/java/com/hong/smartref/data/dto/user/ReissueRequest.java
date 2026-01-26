package com.hong.smartref.data.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReissueRequest {
    private String email;
    private String refreshToken;
}
