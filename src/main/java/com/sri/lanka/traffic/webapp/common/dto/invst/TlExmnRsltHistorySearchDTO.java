package com.sri.lanka.traffic.webapp.common.dto.invst;

import java.time.LocalDateTime;

import com.sri.lanka.traffic.webapp.common.util.LoginUtils;

import lombok.Data;

@Data
public class TlExmnRsltHistorySearchDTO {
	
    private String exmnrsltId; //조사 결과 아이디
    private String lcchgRsn; //위치 변경 사유
	
	private String paramDate; //검색 날짜
	private String exmndrctId; // 조사방향 아이디
	private String exmnmngId = LoginUtils.getExmnmngId(); //조사 관리 아이디
	private LocalDateTime startDt = LoginUtils.getInvstInfo().getStartDt();
	private LocalDateTime endDt = LoginUtils.getInvstInfo().getEndDt();
	
}
