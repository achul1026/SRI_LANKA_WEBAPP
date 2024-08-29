package com.sri.lanka.traffic.webapp.common.enums.code;

import com.sri.lanka.traffic.webapp.common.converter.EnumConverter;
import com.sri.lanka.traffic.webapp.common.util.CommonUtils;

import lombok.Getter;

@Getter
public enum UserTypeCd implements CommonEnumType<String> {
	
	SUPER("UTC001","enums.UserTypeCd.SUPER"),
	GENERAL("UTC002","enums.UserTypeCd.SUPER");
	
	private String code; 
	private String name;
	
	UserTypeCd(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	@Override
    public String getCode() {
        return code;
    }
	
	@Override
	public String getName() {
		return CommonUtils.getMessage(name);
	}
	
	public static class Converter extends EnumConverter<UserTypeCd, String> {
        public Converter() {
            super(UserTypeCd.class);
        }
    }
}
