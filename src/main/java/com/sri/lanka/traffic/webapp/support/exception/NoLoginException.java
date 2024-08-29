package com.sri.lanka.traffic.webapp.support.exception;

import lombok.Getter;

@Getter
public class NoLoginException extends RuntimeException{
	private ErrorCode errorCode;
	private String message;

	public NoLoginException() {
		super();
		this.errorCode = ErrorCode.NOT_FOUNT_INVST_INFO;
		this.message = ErrorCode.NOT_FOUNT_INVST_INFO.getMessage();
	}
	
	public NoLoginException(ErrorCode errorCode) {
		super();
		this.errorCode = errorCode;
		this.message = errorCode.getMessage();
	}
	public NoLoginException(String errorMessage) {
		super();
		this.message = errorMessage;
	}

	public NoLoginException(ErrorCode errorCode, String message) {
		super();
		this.errorCode = errorCode;
		this.message = message;
	}
}
