package com.skhu.skhucapstone.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Google 로그인
    INVALID_GOOGLE_TOKEN(HttpStatus.UNAUTHORIZED, "INVALID_GOOGLE_TOKEN", "유효하지 않은 구글 토큰입니다."),
    FORBIDDEN_LOGIN(HttpStatus.FORBIDDEN, "FORBIDDEN_LOGIN", "허용되지 않은 로그인 요청입니다."),
    EMAIL_ALREADY_LINKED(HttpStatus.CONFLICT, "EMAIL_ALREADY_LINKED", "이미 다른 계정으로 가입된 이메일입니다."),

    // 학교 이메일
    INVALID_SCHOOL_EMAIL(HttpStatus.FORBIDDEN, "INVALID_SCHOOL_EMAIL", "학교 이메일 형식이 아닙니다."),
    ALREADY_VERIFIED_EMAIL(HttpStatus.CONFLICT, "ALREADY_VERIFIED_EMAIL", "이미 인증된 학교 이메일입니다."),
    EMAIL_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "EMAIL_SEND_FAILED", "인증번호 발송에 실패했습니다."),

    // 인증코드
    INVALID_VERIFICATION_CODE(HttpStatus.FORBIDDEN, "INVALID_VERIFICATION_CODE", "인증번호가 일치하지 않습니다."),
    VERIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "VERIFICATION_NOT_FOUND", "인증 요청 정보를 찾을 수 없습니다."),
    VERIFICATION_EXPIRED(HttpStatus.GONE, "VERIFICATION_EXPIRED", "인증번호가 만료되었습니다."),
    TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS, "TOO_MANY_REQUESTS", "요청이 너무 많습니다. 잠시 후 다시 시도해주세요."),

    // 공통
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "로그인이 필요합니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "INVALID_REQUEST", "올바르지 않은 요청입니다."),

    // 커피챗
    COFFEECHAT_PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "COFFEECHAT_PROFILE_NOT_FOUND", "커피챗 프로필을 찾을 수 없습니다."),
    INVALID_COFFEECHAT_PROFILE_REQUEST(HttpStatus.BAD_REQUEST, "INVALID_COFFEECHAT_PROFILE_REQUEST", "커피챗 프로필 요청 형식이 올바르지 않습니다."),
    COFFEECHAT_PROFILE_PRIVATE(HttpStatus.FORBIDDEN, "COFFEECHAT_PROFILE_PRIVATE", "비공개 커피챗 프로필입니다."),


    // ClubMember
    CLUB_MEMBER_PRESIDENT_REQUIRED(HttpStatus.BAD_REQUEST, "CLUB_MEMBER_PRESIDENT_REQUIRED", "부원 명단에는 PRESIDENT 역할이 최소 1명 이상 필요합니다."),
    CLUB_MEMBER_DUPLICATE_USER(HttpStatus.BAD_REQUEST, "CLUB_MEMBER_DUPLICATE_USER", "요청한 부원 명단에 중복된 사용자가 있습니다."),
    CLUB_MEMBER_ALREADY_REGISTERED(HttpStatus.CONFLICT, "CLUB_MEMBER_ALREADY_REGISTERED", "이미 해당 동아리에 등록된 사용자입니다."),
    CLUB_NOT_FOUND(HttpStatus.NOT_FOUND, "CLUB_NOT_FOUND", "해당 동아리를 찾을 수 없습니다."),

    // Post
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST_NOT_FOUND", "게시글을 찾을 수 없습니다."),
    POST_WRITE_FORBIDDEN(HttpStatus.FORBIDDEN, "POST_WRITE_FORBIDDEN", "게시글 작성 권한이 없습니다."),
    POST_UPDATE_FORBIDDEN(HttpStatus.FORBIDDEN, "POST_UPDATE_FORBIDDEN", "게시글 수정 권한이 없습니다."),
    POST_DELETE_FORBIDDEN(HttpStatus.FORBIDDEN, "POST_DELETE_FORBIDDEN", "게시글 삭제 권한이 없습니다."),

    // 협업모집
    CLUB_COLLAB_NOT_FOUND(HttpStatus.NOT_FOUND, "CLUB_COLLAB_NOT_FOUND", "협업 모집글을 찾을 수 없습니다."),
    CLUB_COLLAB_WRITE_FORBIDDEN(HttpStatus.FORBIDDEN, "CLUB_COLLAB_WRITE_FORBIDDEN", "협업 모집글 작성 권한이 없습니다."),
    CLUB_COLLAB_UPDATE_FORBIDDEN(HttpStatus.FORBIDDEN, "CLUB_COLLAB_UPDATE_FORBIDDEN", "협업 모집글 수정 권한이 없습니다."),
    CLUB_COLLAB_DELETE_FORBIDDEN(HttpStatus.FORBIDDEN, "CLUB_COLLAB_DELETE_FORBIDDEN", "협업 모집글 삭제 권한이 없습니다."),
    CLUB_COLLAB_INVALID_DATE(HttpStatus.BAD_REQUEST, "CLUB_COLLAB_INVALID_DATE", "마감일은 대회 날짜보다 늦을 수 없습니다."),

    // 댓글
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT_NOT_FOUND", "댓글을 찾을 수 없습니다."),
    COMMENT_DELETE_FORBIDDEN(HttpStatus.FORBIDDEN, "COMMENT_DELETE_FORBIDDEN", "댓글 삭제 권한이 없습니다."),


    // 유저
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "유저를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
