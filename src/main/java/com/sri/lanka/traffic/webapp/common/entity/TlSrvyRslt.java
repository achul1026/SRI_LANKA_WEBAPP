package com.sri.lanka.traffic.webapp.common.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Data;

@Entity
@Data //설문조사 결과정보
public class TlSrvyRslt {

	@Id
    private String srvyrsltId; //설문 결과 아이디

    private String exmnrsltId; //조사 결과 아이디
    
    private LocalDateTime exmnstartDt; //조사 기간 시작 일시
    
    private LocalDateTime exmnendDt; //조사 기간 종료 일시
    
    private String pollsterLc; //조사원 위치

	public TlSrvyRslt(){}

    @Builder
	public TlSrvyRslt(String srvyrsltId ,String exmnrsltId, String pollsterLc
						, LocalDateTime exmnstartDt, LocalDateTime exmnendDt
						) {
    	
		this.srvyrsltId 		= srvyrsltId;
		this.exmnrsltId 		= exmnrsltId;
		this.pollsterLc 		= pollsterLc;
		this.exmnstartDt 		= exmnstartDt;
		this.exmnendDt 			= exmnendDt;
		
	}
}
