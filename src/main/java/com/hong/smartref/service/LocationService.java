package com.hong.smartref.service;

import com.hong.smartref.data.dto.location.CurrentLocationRequest;
import com.hong.smartref.data.dto.location.GoogleGeocodeResponse;
import com.hong.smartref.data.dto.location.LocationResponse;
import com.hong.smartref.exception.CustomException;
import com.hong.smartref.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {


    private final RestClient googleRestClient;

    public LocationResponse getLocation(CurrentLocationRequest request) {

        String latlng = request.getLat() + "," + request.getLng();

        GoogleGeocodeResponse response = googleRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/maps/api/geocode/json")
                        .queryParam("latlng", latlng)
                        .queryParam("key", "{key}")
                        .queryParam("language", request.getLanguage())
                        .queryParam("result_type", "sublocality|locality|neighborhood")
                        .build()
                )
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    throw new CustomException(ErrorCode.EXTERNAL_API_ERROR);
                })
                .body(GoogleGeocodeResponse.class);

        return parseLocation(response); // 🔥 여기서 바로 가공
    }

    public LocationResponse parseLocation(GoogleGeocodeResponse response) {


        if (response.getResults() == null || response.getResults().isEmpty()) {
            return LocationResponse.builder().build();
        }

        GoogleGeocodeResponse.Result first = response.getResults().get(0);

        String placeId = first.getPlaceId();

        // 🔥 중심 좌표
        Double centerLat = first.getGeometry().getLocation().getLat();
        Double centerLng = first.getGeometry().getLocation().getLng();

        // 🔥 viewport 좌표
        Double neLat = first.getGeometry().getViewport().getNortheast().getLat();
        Double neLng = first.getGeometry().getViewport().getNortheast().getLng();
        Double swLat = first.getGeometry().getViewport().getSouthwest().getLat();
        Double swLng = first.getGeometry().getViewport().getSouthwest().getLng();

        String city = null;
        String district = null;
        String country = null;

        for (GoogleGeocodeResponse.Result result : response.getResults()) {
            for (GoogleGeocodeResponse.AddressComponent comp : result.getAddressComponents()) {

                List<String> types = comp.getTypes();
                String name = comp.getLongName();

                if (country == null && types.contains("country")) {
                    country = name;
                }

                if (city == null && types.contains("locality")) {
                    city = name;
                }

                if (district == null) {
                    if (types.contains("sublocality_level_2")) {
                        district = name;
                    } else if (types.contains("administrative_area_level_2")) {
                        district = name;
                    }
                }
            }
        }

        return LocationResponse.builder()
                .placeId(placeId)
                .centerLat(centerLat)
                .centerLng(centerLng)
                .viewportNeLat(neLat)
                .viewportNeLng(neLng)
                .viewportSwLat(swLat)
                .viewportSwLng(swLng)
                .city(city)
                .district(district)
                .country(country)
                .build();
    }

}
