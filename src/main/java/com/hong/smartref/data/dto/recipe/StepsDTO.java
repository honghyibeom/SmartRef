package com.hong.smartref.data.dto.recipe;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StepsDTO {
    private String way;
    private String imageUrl;
    private int stepNumber;
}
