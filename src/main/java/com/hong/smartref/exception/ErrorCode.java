package com.hong.smartref.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    NOT_EXIST_USER("사용자가 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    EXTERNAL_API_ERROR("외부 API 호출 실패", HttpStatus.BAD_REQUEST),
    NOT_EXIST_FOOD("음식이 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    FAIL_CREATE_NICKNAME("닉네임 생성 실패", HttpStatus.BAD_REQUEST),
    FAIL_UPDATE_EXTERNAL_FOOD("외부 ingredient 수정 실패", HttpStatus.BAD_REQUEST),
    FAIL_TRANSFER_INGREDIENT_TO_NICKNAME("ingredient -> nickname 변환 실패",HttpStatus.BAD_REQUEST),
    FAIL_MIGRATE_EXTERNAL_FOOD("외부 재료 ID 마이그래이션 실패" , HttpStatus.BAD_REQUEST),
    FAIL_CREATE_EXTERNAL_FOOD("외부음식 생성 실패",HttpStatus.BAD_REQUEST),
    NOT_EXIST_EXTERNAL_FOOD("외부 음식이 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    NOT_EXIST_RECIPE_SAVE("저장한 음식이 없습니다.", HttpStatus.BAD_REQUEST),
    NOT_EXIST_RECIPE_LIKE("즐겨찾기한 음식이 없습니다.", HttpStatus.BAD_REQUEST),
    NOT_EXIST_STORAGE_USER("사용자의 냉장고가 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    NOT_EQUALS_USER("사용자가 일지하지 않습니다.", HttpStatus.BAD_REQUEST),
    EXIST_USER("중복된 사용자가 존재합니다.", HttpStatus.BAD_REQUEST),
    EXIST_RECIPE_SAVE("이미 저장된 레시피입니다.", HttpStatus.BAD_REQUEST),
    NOT_EXIST_TICKET("사용할 티켓이 부족합니다.", HttpStatus.BAD_REQUEST),
    NOT_EQUALS_PASSWORD("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    SMS_CERTIFICATION_NUMBER_MISMATCH("인증번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    NOT_VALIDATE_USER("인증되지 않은 유저입니다.", HttpStatus.UNAUTHORIZED),
    NOT_VALIDATE_TOKEN("유효한 토큰이 아닙니다.", HttpStatus.BAD_REQUEST),
    NOT_VALIDATE_REFRESH_TOKEN("유효한 리프레시 토큰이 아닙니다.", HttpStatus.BAD_REQUEST),
    EXIST_NICKNAME("같은 닉네임이 존재 합니다.", HttpStatus.BAD_REQUEST),
    NOT_EXIST_STORAGE("존재하지 않는 저장소",HttpStatus.BAD_REQUEST),
    NOT_EXIST_LABEL("존재하지 않는 라벨",HttpStatus.BAD_REQUEST),
    NOT_EXIST_NOTIFICATION("존재하지 않는 알림",HttpStatus.BAD_REQUEST),
    NOT_EXIST_FOOD_FAVORITE("좋아하는 음식이 없습니다.",HttpStatus.BAD_REQUEST),
    NOT_EXIST_RECIPE("레시피가 없습니다.",HttpStatus.BAD_REQUEST),
    EMPTY_FILE_EXCEPTION("파일이 없습니다.",HttpStatus.BAD_REQUEST),
    IO_EXCEPTION_ON_IMAGE_UPLOAD("S3 이미지 업로드 에러.",HttpStatus.BAD_REQUEST),
    NO_FILE_EXTENTION("파일 형식이 아닙니다.",HttpStatus.BAD_REQUEST),
    INVALID_FILE_EXTENTION("유효하지 않은 파일 형식입니다.",HttpStatus.BAD_REQUEST),
    PUT_OBJECT_EXCEPTION("S3 이미지 업로드중 에러.",HttpStatus.BAD_REQUEST),
    IO_EXCEPTION_ON_IMAGE_DELETE("S3 이미지 삭제중 에러.",HttpStatus.BAD_REQUEST),
    ALREADY_CHANGED_TOKEN("이미 재발급이 완료된 토큰입니다.",HttpStatus.UNAUTHORIZED),
    FAIL_TO_CERTIFICATE("인증 요청이 정상적으로 실행되지 않았습니다.",HttpStatus.UNAUTHORIZED),
    EXPIRED_TOKEN("토큰이 만료되었습니다.", HttpStatus.FORBIDDEN),
    INVALID_SIGNATURE_TOKEN("jwt 서명 오류", HttpStatus.FORBIDDEN),
    UNSUPPORTED_TOKEN("지원하지 않는 토큰", HttpStatus.FORBIDDEN),
    MALFORMED_TOKEN("형식 오류 등 일반적인 잘못된 토큰", HttpStatus.FORBIDDEN),
    S3_CHECK_FILE_EXISTENCE_EXCEPTION("S3_CHECK_FILE_EXISTENCE_EXCEPTION", HttpStatus.BAD_REQUEST),
    S3_DELETE_EXCEPTION("S3 이미지 삭제 실패...", HttpStatus.BAD_REQUEST),
    EXIST_IMAGE("기존이미지가 존재합니다.", HttpStatus.BAD_REQUEST),
    NOT_EXIST_LOCATION("위치가 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    WRONG_REQUEST("잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    UPLOAD_LIMIT_EXCEEDED("업로드한 이미지 갯수 초과", HttpStatus.BAD_REQUEST),
    BUCKET_FULL("버킷 100GB 초과 관리자 문의", HttpStatus.BAD_REQUEST),
    EXPIRED_CERT_CODE("이메일 인증코드 시간 초과", HttpStatus.BAD_REQUEST),
    NO_AUTHORITY("주인이 아닙니다.", HttpStatus.BAD_REQUEST),
    INVALID_CERT_CODE("이메일 인증코드 불일치", HttpStatus.BAD_REQUEST);



    private final HttpStatus httpStatus;
    private final String errorMessage;
    ErrorCode(String errorMessage, HttpStatus httpStatus) {
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}
