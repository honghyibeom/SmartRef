package com.hong.smartref.service;

import com.hong.smartref.config.security.UserDetailsImpl;
import com.hong.smartref.data.dto.food.FoodIdDTO;
import com.hong.smartref.data.dto.food.FoodRequest;
import com.hong.smartref.data.dto.location.LocationInfo;
import com.hong.smartref.data.dto.storage.*;
import com.hong.smartref.data.entity.*;
import com.hong.smartref.data.enumerate.DefaultStorageColor;
import com.hong.smartref.data.enumerate.DefaultStorageName;
import com.hong.smartref.data.enumerate.StorageRole;
import com.hong.smartref.data.enumerate.StorageTypeEnum;
import com.hong.smartref.exception.CustomException;
import com.hong.smartref.exception.ErrorCode;
import com.hong.smartref.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final StorageRepository storageRepository;
    private final UserRepository userRepository;
    private final StorageUserRepository storageUserRepository;
    private final LocationRepository locationRepository;
    private final FoodRepository foodRepository;
    private final StorageTypeRepository storageTypeRepository;
    private final StorageLocationRepository storageLocationRepository;

    // storage 저장
    @Transactional
    public Long insertStorage(StorageRequest storageRequest, UserDetailsImpl userDetails) {
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));

        StorageType storageType = storageTypeRepository.findByStorageTypeId(storageRequest.getStorageTypeId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_STORAGE));

        // storage 저장
        String name = Optional.ofNullable(storageRequest.getStorageName())
                .orElse(DefaultStorageName.getRandomStorageName());

        String color = Optional.ofNullable(storageRequest.getStorageColor())
                .orElse(DefaultStorageColor.getRandomColor());

        Storage storage = Storage.create(name, color, storageType);
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

        storage.setStorageColor(color);
        storage.setStorageName(name);
        Storage result = storageRepository.save(storage);
        return result.getStorageId();
    }

    @Transactional
    public Long deleteStorage(Long storageId, UserDetailsImpl userDetails) {
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));

        Storage storage = storageRepository.findById(storageId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_STORAGE));

        Storage trash = storageRepository
                .findByStorageUserList_User_EmailAndStorageType_StorageTypeEnum(
                        user.getEmail(),
                        StorageTypeEnum.TRASH
                ).orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_STORAGE));

        Location trashLocation = locationRepository
                .findTrashLocation(StorageTypeEnum.TRASH.name())
                .stream()
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_LOCATION));


        StorageUser storageUser = storageUserRepository.findByUserAndStorage(user, storage)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_STORAGE_USER));


        int memberCount = storageUserRepository.countByStorage(storage);

        // 1️⃣ 혼자 남아있는 경우 → Storage 삭제
        if (memberCount == 1) {
            if (storageUser.getRole() != StorageRole.OWNER) {
                throw new CustomException(ErrorCode.NO_AUTHORITY);
            }
            // food를 쓰레기 storage에 옮긴다.
            List<Food> food = foodRepository.findByStorage(storage);
            for (Food foodItem : food) {
                foodItem.setStorage(trash);
                foodItem.setLocation(trashLocation);
                foodRepository.save(foodItem);
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

        //유저가 가지고있는 모든 storageType을 조회한다.
        List<StorageType> storageTypes = storageUserList.stream()
                .map(su -> su.getStorage().getStorageType())
                .distinct()
                .toList();

        // StorageLocation 을 전부 조회
        List<StorageLocation> allLocations =
                storageLocationRepository.findByStorageTypeIn(storageTypes);

        //Map으로 묶는다.
        Map<StorageType, List<Long>> locationMap =
                allLocations.stream()
                        .collect(Collectors.groupingBy(
                                StorageLocation::getStorageType,
                                Collectors.mapping(sl -> sl.getLocation().getLocationId(), Collectors.toList())
                        ));


        List<StorageInfo> storageInfoList = new ArrayList<>();

        for (StorageUser storageUser : storageUserList) {
            Storage storage = storageUser.getStorage();

            // storageType으로 묶는다
            List<Long> locationIds =
                    locationMap.getOrDefault(storage.getStorageType(), new ArrayList<>());

            StorageInfo storageInfo = StorageInfo.builder()
                    .storageColor(storage.getStorageColor())
                    .storageName(storage.getStorageName())
                    .storageTypeEnum(storage.getStorageType().getStorageTypeEnum())
                    .storageId(storage.getStorageId())
                    .locationIds(locationIds)
                    .build();

            storageInfoList.add(storageInfo);
        }

        return storageInfoList;
    }


    @Transactional
    public void foodStorageMigration(StorageMoveRequest storageMoveRequest) {
        // 1. 옮길 storageId에 옮길 locationId에 기존 foodId와 이동한 갯수 quantity 를 리스트로 전달 할거임
        // 2. 이때 기존 foodId는 quantity 를 (기존 - 이동한 갯수) 를 해서 quantity 수정 후 저장
        // 3. 옮길 storageId에 있는 타입에 locationId가 옮길 locationId와 일치한지 검증 필요
        // 4. food를 옮길 stroageId와 location에 새로 생성한다.

        Storage nextStorage = storageRepository.findById(storageMoveRequest.getNextStorageId()).
                orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_STORAGE));

        for (LocationMoveRequest storageMoveRequestItem : storageMoveRequest.getList()) {
            Location nextLocation = locationRepository.findById(storageMoveRequestItem.getNextLocationId())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_LOCATION));

            validateLocation(nextStorage, nextLocation);

            for (FoodMoveRequest foodMoveRequestItem : storageMoveRequestItem.getFoodIdList()) {

                // 기존 food quantity 수정
                Food oriFood = foodRepository.findById(foodMoveRequestItem.getFoodId())
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_FOOD));


                BigDecimal moveQty = foodMoveRequestItem.getQuantity();

                // 검증
                if (oriFood.getQuantity().compareTo(moveQty) < 0) {
                    throw new CustomException(ErrorCode.INVALID_QUANTITY);
                }

                // 기존 차감
                BigDecimal newQty = oriFood.getQuantity().subtract(moveQty);

                if (newQty.compareTo(BigDecimal.ZERO) <= 0) {
                    foodRepository.delete(oriFood);
                }
                else {
                    oriFood.update(
                            oriFood.getStorage(),
                            oriFood.getLabel(),
                            oriFood.getMasterId(),
                            oriFood.getName(),
                            newQty,
                            oriFood.getUnit(),
                            oriFood.getExpiredAt(),
                            oriFood.getLocation(),
                            oriFood.getAmountType(),
                            oriFood.getMemo(),
                            oriFood.getImageUrl()
                    );
                    foodRepository.save(oriFood);
                }

                // 새 food 등록(옮기는 작업)
                Food divideFood = Food.create(
                        nextStorage,
                        oriFood.getLabel(),
                        oriFood.getName(),
                        oriFood.getAmountType(),
                        moveQty,
                        oriFood.getUnit(),
                        oriFood.getExpiredAt(),
                        nextLocation,
                        oriFood.getImageUrl(),
                        null,
                        oriFood.getMasterId()
                );
                foodRepository.save(divideFood);
            }
        }
    }
    private void validateLocation(Storage storage, Location location) {
        boolean valid = storageLocationRepository
                .existsByStorageTypeAndLocation(storage.getStorageType(), location);

        if (!valid) {
            throw new CustomException(ErrorCode.NOT_EXIST_STORAGE_LOCATION);
        }
    }

}
