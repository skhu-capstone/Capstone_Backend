package com.skhu.skhucapstone.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    LOGIN_SUCCESS(HttpStatus.OK, "LOGIN_SUCCESS", "구글 소셜 로그인이 완료되었습니다."),

    // 인증번호
    EMAIL_SEND_SUCCESS(HttpStatus.OK, "EMAIL_SEND_SUCCESS", "인증번호가 발송되었습니다."),
    EMAIL_VERIFY_SUCCESS(HttpStatus.OK, "EMAIL_VERIFY_SUCCESS", "학교 이메일 인증이 완료되었습니다."),
    EMAIL_RESEND_SUCCESS(HttpStatus.OK, "EMAIL_RESEND_SUCCESS", "인증번호가 재발송되었습니다."),

    // 커피챗
    COFFEECHAT_PROFILE_SAVE_SUCCESS(HttpStatus.OK, "COFFEECHAT_PROFILE_SAVE_SUCCESS", "커피챗 프로필이 저장되었습니다."),
    COFFEECHAT_PROFILE_VISIBILITY_UPDATE_SUCCESS(HttpStatus.OK, "COFFEECHAT_PROFILE_VISIBILITY_UPDATE_SUCCESS", "커피챗 프로필 공개 여부가 변경되었습니다."),
    COFFEECHAT_PROFILE_GET_SUCCESS(HttpStatus.OK, "COFFEECHAT_PROFILE_GET_SUCCESS", "커피챗 프로필 조회가 완료되었습니다."),
    COFFEECHAT_PROFILE_LIST_GET_SUCCESS(HttpStatus.OK, "COFFEECHAT_PROFILE_LIST_GET_SUCCESS", "커피챗 프로필 목록 조회가 완료되었습니다."),

    // Post
    POST_CREATE_SUCCESS(HttpStatus.OK, "POST_CREATE_SUCCESS", "게시글 작성이 완료되었습니다."),
    POST_LIST_GET_SUCCESS(HttpStatus.OK, "POST_LIST_GET_SUCCESS", "게시글 목록 조회가 완료되었습니다."),
    POST_GET_SUCCESS(HttpStatus.OK, "POST_GET_SUCCESS", "게시글 조회가 완료되었습니다."),
    POST_UPDATE_SUCCESS(HttpStatus.OK, "POST_UPDATE_SUCCESS", "게시글 수정이 완료되었습니다."),
    POST_DELETE_SUCCESS(HttpStatus.OK, "POST_DELETE_SUCCESS", "게시글 삭제가 완료되었습니다."),

    //Commment
    COMMENT_DELETE_SUCCESS(HttpStatus.OK, "COMMENT_DELETE_SUCCESS", "댓글 삭제가 완료 되었습니다."),
    COMMENT_CREATE_SUCCESS(HttpStatus.OK, "COMMENT_CREATE_SUCCESS", "댓글 작성이 완료 되었습니다."),
    COMMENT_LIST_GET_SUCCESS(HttpStatus.OK, "COMMENT_LIST_GET_SUCCESS", "댓글 목록 조회가 완료 되었습니다."),

    // 마이페이지
    MYPAGE_GET_SUCCESS(HttpStatus.OK, "MYPAGE_GET_SUCCESS", "마이페이지 조회가 완료되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
