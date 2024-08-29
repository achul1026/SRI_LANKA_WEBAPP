package com.sri.lanka.traffic.webapp.common.dto.invst;

import java.time.LocalDateTime;

import com.sri.lanka.traffic.webapp.common.enums.code.ColorTypeCd;
import com.sri.lanka.traffic.webapp.common.enums.code.ExmnSttsCd;
import com.sri.lanka.traffic.webapp.common.enums.code.ExmnTypeCd;
import com.sri.lanka.traffic.webapp.common.util.CommonUtils;

import lombok.Data;

@Data
public class TmExmnMngDTO {
	
	private String exmnmngId;
	private String usermngId;
	private String mngrDept;
	private String exmnpicId;
	private String exmnNm;
	private String roadCd;
	private ExmnTypeCd exmnType;
	private String exmnTypeNm;
	private ColorTypeCd colrCd;
	private ExmnSttsCd sttsCd;
	private String sttsCdNm;
	private String userNm;
	private LocalDateTime startDt;
	private LocalDateTime endDt;
	private String registId;
	private String exmnLc;
	
//	public void setExmnType(ExmnTypeCd exmnType) {
//		if(!CommonUtils.isNull(exmnType)) {
//			this.exmnTypeNm = exmnType.getName();
//		}
//		this.exmnType = exmnType;
//	}
	
	public void setSttsCd(ExmnSttsCd sttsCd) {
		if(!CommonUtils.isNull(sttsCd)) {
			this.sttsCdNm = sttsCd.getName();
		}
		this.sttsCd = sttsCd;
	}
	
	
}
