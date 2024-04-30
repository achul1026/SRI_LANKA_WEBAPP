package com.sri.lanka.traffic.webapp.common.enums.code;

import com.sri.lanka.traffic.webapp.common.converter.EnumConverter;

import lombok.Getter;

@Getter
public enum PollsterTypeCd implements CommonEnumType<String> {
	
	MEMBER("PTC001","팀원"),
	LEADER("PTC002","팀장");
	
	private String code; 
	private String name;
	
	PollsterTypeCd(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	@Override
    public String getCode() {
        return code;
    }
	
	@Override
	public String getName() {
		return name;
	}
	
	public static class Converter extends EnumConverter<PollsterTypeCd, String> {
        public Converter() {
            super(PollsterTypeCd.class);
        }
    }
}
