package com.hong.smartref.service;

import com.hong.smartref.data.dto.location.LocationInfo;
import com.hong.smartref.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public List<LocationInfo>
}
