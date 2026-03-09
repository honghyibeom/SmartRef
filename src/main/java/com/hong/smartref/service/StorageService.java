package com.hong.smartref.service;

import com.hong.smartref.config.security.UserDetailsImpl;
import com.hong.smartref.data.dto.location.LocationInfo;
import com.hong.smartref.data.dto.storage.StorageInfo;
import com.hong.smartref.data.dto.storage.StorageRequest;
import com.hong.smartref.data.entity.Location;
import com.hong.smartref.data.entity.Storage;
import com.hong.smartref.data.entity.StorageUser;
import com.hong.smartref.data.entity.User;
import com.hong.smartref.data.enumerate.DefaultStorageColor;
import com.hong.smartref.data.enumerate.DefaultStorageName;
import com.hong.smartref.data.enumerate.StorageRole;
import com.hong.smartref.data.enumerate.StorageType;
import com.hong.smartref.exception.CustomException;
import com.hong.smartref.exception.ErrorCode;
import com.hong.smartref.repository.LocationRepository;
import com.hong.smartref.repository.StorageRepository;
import com.hong.smartref.repository.StorageUserRepository;
import com.hong.smartref.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final StorageRepository storageRepository;
    private final UserRepository userRepository;
    private final StorageUserRepository storageUserRepository;
    private final LocationRepository locationRepository;

    // storage 저장
    @Transactional
    public Long insertStorage(StorageRequest storageRequest, UserDetailsImpl userDetails) {
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));

        // storage 저장
        String name = Optional.ofNullable(storageRequest.getStorageName())
                .orElse(DefaultStorageName.getRandomStorageName());

        String color = Optional.ofNullable(storageRequest.getStorageColor())
                .orElse(DefaultStorageColor.getRandomColor());

        Storage storage = Storage.create(name, color, storageRequest.getStorageType());
        Storage result = storageRepository.save(storage);

        // storage User 매핑 저장
        StorageUser storageUser = StorageUser.create(user, storage);
        storageUserRepository.save(storageUser);

        return result.getStorageId();
    }

    // storage 수정
    @Transactional
    public Long updateStorage(StorageRequest storageRequest, UserDetailsImpl userDetails) {
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));

        Storage storage = storageRepository.findById(storageRequest.getStorageId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_STORAGE));

        StorageUser storageUser = storageUserRepository.findByUserAndStorage(user, storage)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_STORAGE_USER));

        if(storageUser.getRole() != StorageRole.OWNER) {
           throw new CustomException(ErrorCode.NO_AUTHORITY);
        }

        String name = Optional.ofNullable(storageRequest.getStorageName())
                .orElse(DefaultStorageName.getRandomStorageName());

        String color = Optional.ofNullable(storageRequest.getStorageColor())
                .orElse(DefaultStorageColor.getRandomColor());

        storage.setStorageColor(name);
        storage.setStorageName(color);
        Storage result = storageRepository.save(storage);
        return result.getStorageId();
    }

    @Transactional
    public Long deleteStorage(Long storageId, UserDetailsImpl userDetails) {
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));

        Storage storage = storageRepository.findById(storageId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_STORAGE));

        StorageUser storageUser = storageUserRepository.findByUserAndStorage(user, storage)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_STORAGE_USER));

        int memberCount = storageUserRepository.countByStorage(storage);

        // 1️⃣ 혼자 남아있는 경우 → Storage 삭제
        if (memberCount == 1) {
            if (storageUser.getRole() != StorageRole.OWNER) {
                throw new CustomException(ErrorCode.NO_AUTHORITY);
            }
            storageRepository.delete(storage);
            return storageId;
        }

        boolean isOwner = storageUser.getRole() == StorageRole.OWNER;

        // 2️⃣ 방 나가기
        storageUserRepository.delete(storageUser);

        // 3️⃣ OWNER였던 경우만 다음 OWNER 지정
        if (isOwner) {
            StorageUser nextOwner = storageUserRepository
                    .findTopByStorageOrderByJoinedAtAsc(storage)
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_STORAGE_USER));

            nextOwner.setRole(StorageRole.OWNER);
        }

        return storageId;
    }

    //모든 location 조회
    public List<LocationInfo> getLocationAll() {
        List<Location> locationList = locationRepository.findAll();
        return locationList.stream().map(location ->
                LocationInfo.builder().
                        locationId(location.getLocationId())
                        .locationColor(location.getLocationColor())
                        .locationName(location.getLocationName())
                        .build()
                ).toList();
    }

    public List<StorageInfo> findAllStorage(UserDetailsImpl userDetails) {
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));

        List<StorageUser> storageUserList = storageUserRepository.findByUser(user);

        if (storageUserList.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_EXIST_STORAGE_USER);
        }
        List<StorageInfo> storageInfoList = new ArrayList<>();

        for (StorageUser storageUser : storageUserList) {
            Storage storage = storageUser.getStorage();

            StorageInfo storageInfo = StorageInfo.builder()
                    .storageColor(storage.getStorageColor())
                    .storageName(storage.getStorageName())
                    .storageType(storage.getStorageType())
                    .storageId(storage.getStorageId())
                    .locationIds(storage.getStorageType().getLocationIds())
                    .build();

            storageInfoList.add(storageInfo);
        }

        return storageInfoList;
    }

}
