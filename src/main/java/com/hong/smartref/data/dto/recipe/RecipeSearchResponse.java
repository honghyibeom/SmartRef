package com.hong.smartref.data.dto.recipe;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeSearchResponse {
    private Boolean isPrev;
    private Boolean isAfter;
    private int pageNumber;

    private List<RecipeInfo> recipes;
}
