package com.sri.lanka.traffic.webapp.common.enums.code;

import com.sri.lanka.traffic.webapp.common.converter.EnumConverter;

import lombok.Getter;

@Getter
public enum ExmnTypeCd implements CommonEnumType<String> {
	
	MCC("ETC001","MCC 조사", "traffic"),
	TM("ETC002","TM 조사", "traffic"),
	AXLELOAD("ETC003","AXLELOAD 조사", "survey"),
	LABORSIDE("ETC004","노측 면접 조사", "survey"),
	OD("ETC005","OD 조사", "survey"),
	;
	
	private String code; 
	private String name;

	private String type;
	ExmnTypeCd(String code, String name, String type) {
		this.code = code;
		this.name = name;
		this.type = type;
	}
	
	@Override
    public String getCode() {
        return code;
    }
	
	@Override
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public static class Converter extends EnumConverter<ExmnTypeCd, String> {
        public Converter() {
            super(ExmnTypeCd.class);
        }
    }
}
