package com.sri.lanka.traffic.webapp.common.dto.common;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.sri.lanka.traffic.webapp.common.enums.code.ExmnSttsCd;
import com.sri.lanka.traffic.webapp.common.enums.code.ExmnTypeCd;
import com.sri.lanka.traffic.webapp.common.util.CommonUtils;

import lombok.Data;

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
	private LocalDateTime startDt;
	private LocalDateTime endDt;
	
	public void setExmnType(ExmnTypeCd exmnType) {
		if(!CommonUtils.isNull(exmnType)) {
			this.exmnTypeNm = exmnType.getName();
		}
		this.exmnType = exmnType;
	}
}
