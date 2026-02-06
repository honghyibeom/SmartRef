package com.hong.smartref.data.dto.food;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageAnalyzeRequest {
    private List<String> imageUrl;
    private String Country;
}
