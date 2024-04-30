package com.sri.lanka.traffic.webapp.common.enums.code;

import com.sri.lanka.traffic.webapp.common.converter.EnumConverter;
import com.sri.lanka.traffic.webapp.common.util.CommonUtils;

import lombok.Getter;

@Getter
public enum MngrSttsCd implements CommonEnumType<String> {
	
	APPROVAL("MSC001","승인"),
	NOT_APPROVED("MSC002","미승인"),
	SUSPENDED("MSC003","정지"),
	RESIGN("MSC004","탈퇴");
	
	private String code; 
	private String name;
	
	MngrSttsCd(String code, String name) {
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
	
	public static MngrSttsCd getEnums(String code) {
		if(!CommonUtils.isNull(code)) {
			for(MngrSttsCd r : MngrSttsCd.values()) {
				if(r.code.equals(code)) {
					return r;
				}
			}
		}
		return null;
	}
	
	public static class Converter extends EnumConverter<MngrSttsCd, String> {
        public Converter() {
            super(MngrSttsCd.class);
        }
    }
}
