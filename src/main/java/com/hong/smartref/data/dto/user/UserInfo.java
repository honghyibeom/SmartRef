package com.hong.smartref.data.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfo {
    private String username;
    private String email;
    private String color;
    private Boolean isPremium;
}
