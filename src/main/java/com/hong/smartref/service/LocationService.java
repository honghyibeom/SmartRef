package com.hong.smartref.service;

import com.hong.smartref.data.dto.location.LocationInfo;
import com.hong.smartref.data.entity.Location;
import com.hong.smartref.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public List<LocationInfo> getLocations() {
        List<Location> result = locationRepository.findAll();
        List<LocationInfo> locationInfos = new ArrayList<>();
        for (Location location : result) {
            LocationInfo locationInfo = LocationInfo.builder()
                        .locationColor(location.getLocationColor())
                        .locationName(location.getLocationName())
                        .locationId(location.getLocationId())
                        .build();
            locationInfos.add(locationInfo);
        }
        return locationInfos;
    }
}
