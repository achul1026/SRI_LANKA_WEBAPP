package com.sri.lanka.traffic.webapp.common.enums.code;

import com.sri.lanka.traffic.webapp.common.converter.EnumConverter;

import lombok.Getter;

@Getter
public enum MngrLvlCd implements CommonEnumType<String> {
	
	SUPER("MLC001","최고 관리자"),
	GENERAL("MLC002","일반 관리자");
	
	private String code; 
	private String name;
	
	MngrLvlCd(String code, String name) {
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
	
	public static class Converter extends EnumConverter<MngrLvlCd, String> {
        public Converter() {
            super(MngrLvlCd.class);
        }
    }
}
