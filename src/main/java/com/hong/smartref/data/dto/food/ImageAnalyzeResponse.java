package com.hong.smartref.data.dto.food;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageAnalyzeResponse {

    private List<Item> items;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        private String name;
        private String label;
        private LocalDate expiryDate;
        private Quantity quantity;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Quantity {
        private String type;
        private int value;
        private String unit;
    }
}

