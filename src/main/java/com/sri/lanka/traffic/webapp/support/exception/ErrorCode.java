package com.sri.lanka.traffic.webapp.support.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
	INVALID_PARAMETER(400, "enums.ErrorCode.INVALID_PARAMETER"),
	UNAUTHORIZED(401, "enums.ErrorCode.UNAUTHORIZED"),
	INTERNAL_SERVER_ERROR(500, "enums.ErrorCode.INTERNAL_SERVER_ERROR"),
	
	//회원가입
	REGEX_NOT_FOUND(1001, "enums.ErrorCode.REGEX_NOT_FOUND"),					//정규식 못찾았을때
	REQUIRED_FIELDS(1002,"enums.ErrorCode.REQUIRED_FIELDS"),					//필수 파라미터 누락
	VALIDATION_FAILED(1003,"enums.ErrorCode.VALIDATION_FAILED"),				//유효성 검증 실패
	
	
	NOT_FOUNT_INVST_INFO(2001, "enums.ErrorCode.NOT_FOUNT_INVST_INFO"),
	EXISTS_INVST_INFO(2002, "enums.ErrorCode.EXISTS_INVST_INFO"),

	//common
	ENTITY_COPY_FAIL(9001, "enums.ErrorCode.ENTITY_COPY_FAIL"),

	LOGIN_TIME_OUT(9002, "enums.ErrorCode.LOGIN_TIME_OUT"),
	ENTITY_SAVE_FAILED(9003, "enums.ErrorCode.ENTITY_SAVE_FAILED")
	;
	
	private int status;
	private String message;
	
}
