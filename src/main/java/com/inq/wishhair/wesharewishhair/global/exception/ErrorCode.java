package com.inq.wishhair.wesharewishhair.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    NOT_EXIST_KEY("GLOBAL_001", "존재하지 않는 키 입니다.", HttpStatus.NOT_FOUND),
    LOGIN_FAIL("LOGIN_001", "아이디 혹은 비밀번호가 틀렸습니다.", HttpStatus.BAD_REQUEST),
    FILE_TRANSFER_EX("FILE_001", "파일 저장 오류", HttpStatus.INTERNAL_SERVER_ERROR),
    EMPTY_FILE_EX("FILE_002", "빈 파일 입니다.", HttpStatus.BAD_REQUEST),
    AUTH_EXPIRED_TOKEN("AUTH_001", "토큰의 유효기간이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    AUTH_INVALID_TOKEN("AUTH_002", "토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED),
    AUTH_REQUIRED_LOGIN("AUTH_003", "로그인이 필요합니다.", HttpStatus.UNAUTHORIZED),

    HAIR_STYLE_NO_FACE_SHAPE_TAG("HAIR_STYLE_002", "얼굴형 태그가 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    USER_TAG_MISMATCH("USER_001", "얼굴형 태그가 아닙니다.", HttpStatus.BAD_REQUEST),
    USER_INVALID_EMAIL("USER_002", "이메일 형식이 맞지 않습니다.", HttpStatus.BAD_REQUEST),
    USER_INVALID_PASSWORD("USER_003", "비밀번호 형식이 맞지 않습니다.", HttpStatus.BAD_REQUEST),
    USER_INVALID_NICKNAME("USER_004", "닉네임 형식이 맞지 않습니다.", HttpStatus.BAD_REQUEST),
    USER_DUPLICATED_NICKNAME("USER_005", "이미 존재하는 닉네임 입니다.", HttpStatus.CONFLICT),
    USER_DUPLICATED_EMAIL("USER_006", "이미 존재하는 이메일 입니다.", HttpStatus.CONFLICT),
    USER_WRONG_PASSWORD("USER_006", "일치하지 않는 비밀번호 입니다.", HttpStatus.BAD_REQUEST),
    USER_NO_FACE_SHAPE_TAG("USER_007", "얼굴형 분석을 시도하세요", HttpStatus.FORBIDDEN),
    USER_NOT_FOUND_BY_EMAIL("USER_008", "이메일에 해당하는 회원이 존재하지 않습니다.", HttpStatus.NOT_FOUND),

    REVIEW_NOT_WRITER("REVIEW_001", "리뷰 작성자가 아닙니다.", HttpStatus.BAD_REQUEST),

    REVIEW_ALREADY_LIKING("LIKE_001", "이미 좋아요한 리뷰입니다.", HttpStatus.BAD_REQUEST),
    REVIEW_NOT_LIKING("LIKE_002", "좋아요 하지 않은 리뷰입니다.", HttpStatus.BAD_REQUEST),

    CONTENTS_INVALID_LENGTH("CONTENTS_001", "5자 이상 100자 이하로 작성해주세요", HttpStatus.BAD_REQUEST),

    MAIL_EXPIRED_KEY("MAIL_001", "인증키가 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    MAIL_INVALID_KEY("MAIL_002", "인증키가 틀립니다.", HttpStatus.UNAUTHORIZED),

    POINT_INVALID_POINT_RANGE("POINT_001", "포인트는 반드시 0 보다 커야합니다.", HttpStatus.BAD_REQUEST),
    POINT_NOT_ENOUGH("POINT_002", "포인트가 부족합니다.", HttpStatus.BAD_REQUEST),

    RUN_NOT_ENOUGH_TAG("RUN_001", "태그는 필수입니다.", HttpStatus.BAD_REQUEST),
    RUN_NO_FACE_SHAPE_TAG("RUN_002", "얼굴형 분석을 다시 시도하세요.", HttpStatus.BAD_REQUEST),

    SCORE_MISMATCH("SCORE_001", "정해진 형식의 입력이 아닙니다.", HttpStatus.BAD_REQUEST),

    WISH_HAIR_NOT_EXIST("WISH_HAIR_001", "찜한 헤어스타일이 아닙니다", HttpStatus.BAD_REQUEST),
    WISH_HAIR_ALREADY_EXIST("WISH_HAIR_002", "이미 찜한 헤어스타일 입니다.", HttpStatus.BAD_REQUEST),

    GLOBAL_VALIDATION_ERROR("GLOBAL_001", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    GLOBAL_NOT_SUPPORTED_URI("GLOBAL_002", "지원하지 않는 URI 요청입니다.", HttpStatus.NOT_FOUND),
    GLOBAL_NOT_SUPPORTED_METHOD("GLOBAL_003", "지원하지 않는 Method 요청입니다.", HttpStatus.METHOD_NOT_ALLOWED),
    GLOBAL_INTERNAL_SERVER_ERROR("GLOBAL_004", "서버 내부 오류입니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    GOOGLE_OAUTH_EXCEPTION("AUTH_004", "Google 서버 요청 간 에러가 발생하였습니다.", HttpStatus.FORBIDDEN),

    FLASK_SERVER_EXCEPTION("FLASK_001", "Flask 서버 요청 간 에러가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    FLASK_RESPONSE_ERROR("FLASK_002", "Flask 서버의 응답값의 형식이 올바르지 않습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    AOP_GENERIC_EXCEPTION("AOP_001", "AOP 에서 발생한 Generic 에러 입니다.", HttpStatus.INTERNAL_SERVER_ERROR)
    ;

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
