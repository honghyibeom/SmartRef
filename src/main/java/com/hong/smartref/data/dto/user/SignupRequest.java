package com.hong.smartref.data.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupRequest {
    @Email(message = "do not match email form")
    @NotBlank(message = "email cannot be blank")
    private String email;
    private String password;
}
