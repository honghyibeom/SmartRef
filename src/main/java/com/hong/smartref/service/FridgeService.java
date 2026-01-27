package com.hong.smartref.service;

import com.hong.smartref.config.security.UserDetailsImpl;
import com.hong.smartref.data.dto.Fridge.FridgeInfo;
import com.hong.smartref.data.dto.Fridge.FridgeRequest;
import com.hong.smartref.data.entity.Fridge;
import com.hong.smartref.data.entity.FridgeUser;
import com.hong.smartref.repository.FridgeRepository;
import com.hong.smartref.repository.FridgeUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FridgeService {
    private final FridgeRepository fridgeRepository;
    private final FridgeUserRepository fridgeUserRepository;

    // 냉장고 생성
    public Long createFridge(FridgeRequest fridgeRequest, UserDetailsImpl userDetails) {
        Fridge fridge = Fridge.create(fridgeRequest.getRefName(), fridgeRequest.getRefColor());
        Fridge value = fridgeRepository.save(fridge);

        // user와 fridge 매핑
        FridgeUser fridgeUser = FridgeUser.create(userDetails.getUser(), value);
        fridgeUserRepository.save(fridgeUser);

        return value.getFridgeId();
    }

    // 냉장고 조회
    public List<FridgeInfo> searchFridge(UserDetailsImpl user) {
        List<FridgeUser> FridgeUserList = fridgeUserRepository.findFridgeUserByUser(user.getUser());
        List<FridgeInfo> fridgeInfoList = new ArrayList<>();
        for (FridgeUser fridgeUser : FridgeUserList) {
            FridgeInfo fridgeInfo = FridgeInfo.builder()
                    .refNo(fridgeUser.getFridgeUserId())
                    .refName(fridgeUser.getFridge().getFridgeName())
                    .build();
            fridgeInfoList.add(fridgeInfo);
        }
        return fridgeInfoList;
    }

    // 냉장고 삭제
    public void deleteFridge(Long refNo) {
        fridgeRepository.deleteById(refNo);
    }

    // 냉장고 수정

}
