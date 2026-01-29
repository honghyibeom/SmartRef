package com.hong.smartref.data.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class SocialUserInfoDTO {
    private String userNickname;
    private String email;

    public SocialUserInfoDTO(String userNickname, String email) {
        this.userNickname = userNickname;
        this.email = email;
    }

}
