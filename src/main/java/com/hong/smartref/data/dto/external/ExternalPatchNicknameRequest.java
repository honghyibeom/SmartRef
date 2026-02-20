package com.hong.smartref.data.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExternalPatchNicknameRequest {

    private Long id;

    private String synonym;

    @JsonProperty("lang_code")
    private String langCode;
}
