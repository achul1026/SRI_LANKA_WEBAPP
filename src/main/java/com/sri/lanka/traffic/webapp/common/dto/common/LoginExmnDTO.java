package com.sri.lanka.traffic.webapp.common.dto.common;

import com.sri.lanka.traffic.webapp.common.enums.code.ExmnSttsCd;
import com.sri.lanka.traffic.webapp.common.enums.code.ExmnTypeCd;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class LoginExmnDTO {
	
	private String exmnmngId;
	private String exmnNm;
	private String srvyId;
	private String partcptCd;
	private ExmnTypeCd exmnType;
	private String exmnTypeNm;
	private String exmnDiv;
	private ExmnSttsCd sttsCd;
	private BigDecimal exmnNop;
	private String exmnLc;
	private BigDecimal goalCnt;
	private BigDecimal lat;
	private BigDecimal lon;
	private String exmnRange;
	private LocalDateTime startDt;
	private LocalDateTime endDt;
	private String registId;
	private String pollsterTel;
	private String pollsterId;
	
//	public void setExmnType(ExmnTypeCd exmnType) {
//		if(!CommonUtils.isNull(exmnType)) {
//			this.exmnTypeNm = exmnType.getName();
//		}
//		this.exmnType = exmnType;
//	}
}
