package com.hong.smartref.repository;

import com.hong.smartref.data.entity.Storage;
import com.hong.smartref.data.enumerate.StorageTypeEnum;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Long> {
    /* 유저의 쓰레기통 조회
        SELECT *
        FROM storage s
        JOIN storage_user su
            ON s.storage_id = su.storage_id
        JOIN storage_type st
            ON s.storage_type_id = st.storage_type_id
        WHERE st.storage_type_enum = 'TRASH'
        AND su.user_id = 'a@naver.com';
    */
    Optional<Storage> findByStorageUserList_User_EmailAndStorageType_StorageTypeEnum(String storageUserListUserEmail, StorageTypeEnum storageTypeStorageTypeEnum);
}
