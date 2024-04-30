package com.sri.lanka.traffic.webapp.support.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
	INVALID_PARAMETER(400, "pleace Check Parameter Value"),
	INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
	
	//회원가입
	REGEX_NOT_FOUND(1001, "Regex not found"),					//정규식 못찾았을때
	REQUIRED_FIELDS(1002,"Required field are missing"),			//필수 파라미터 누락
	VALIDATION_FAILED(1003,"validation failed"),				//유효성 검증 실패
	
	
	NOT_FOUNT_INVST_INFO(2001, "Not Fount Investigate Information"),
	EXISTS_INVST_INFO(2002, "Exists Investigate Information"),

	//common
	ENTITY_COPY_FAIL(9001, "Failed to copy properties")
	;
	
	private int status;
	private String message;
	
}
