package com.hong.smartref.data.dto.location;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class GoogleGeocodeResponse {

    private List<Result> results;

    @Getter
    public static class Result {

        @JsonProperty("place_id")
        private String placeId;

        @JsonProperty("address_components")
        private List<AddressComponent> addressComponents;

        private Geometry geometry; // 🔥 추가
    }

    @Getter
    public static class Geometry {
        private Location location;
        private Viewport viewport;
    }

    @Getter
    public static class Location {
        private Double lat;
        private Double lng;
    }

    @Getter
    public static class Viewport {
        private Bound northeast;
        private Bound southwest;
    }

    @Getter
    public static class Bound {
        private Double lat;
        private Double lng;
    }

    @Getter
    public static class AddressComponent {
        @JsonProperty("long_name")
        private String longName;

        @JsonProperty("short_name")
        private String shortName;

        private List<String> types;
    }
}
