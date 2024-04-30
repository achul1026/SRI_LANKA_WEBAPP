package com.sri.lanka.traffic.webapp.common.dto.invst;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.sri.lanka.traffic.webapp.common.entity.TlSrvyRslt;
import com.sri.lanka.traffic.webapp.common.entity.TlTrfvlRslt;
import com.sri.lanka.traffic.webapp.common.util.CommonUtils;

import lombok.Data;

@Data
public class TlExmnRsltDetailSaveDTO {
	
    private String exmnrsltId; //조사 결과 아이디
    private String exmnmngId; //조사 관리 아이디
    private String trfvlOrSrvyId = CommonUtils.getUuid(); //교통량 조사 및 설문 조사 결과 아이디
    private LocalDateTime exmnstartDt; //조사 시작 일시
    private LocalDateTime exmnendDt; //조사 종료 일시
    private BigDecimal pollsterLat; //조사원 위도	
    private BigDecimal pollsterLon; //조사원 경도
    private String pollsterLc; //조사원 위치
    private String lcchgRsn; //위치 변경 사유
    private String type;
	private String startlcNm; //출발 방향(교통량)
	private String endlcNm; //도착 방향(교통량)
	
    public TlTrfvlRslt toTlTrfvlRslt() {
    	return TlTrfvlRslt.builder()
    						.trfvlmexmnId(trfvlOrSrvyId)
    						.exmnrsltId(exmnrsltId)
    						.pollsterLat(pollsterLat)
    						.pollsterLon(pollsterLon)
    						.pollsterLc(pollsterLc)
    						.exmnstartDt(exmnstartDt)
    						.exmnendDt(exmnendDt)
    						.lcchgRsn(CommonUtils.isNull(lcchgRsn) ? null : lcchgRsn )
							.startlcNm(startlcNm)
							.endlcNm(endlcNm)
    						.build();
    }
    public TlSrvyRslt toTlSrvyRslt() {
    	return TlSrvyRslt.builder()
			    			.srvyrsltId(trfvlOrSrvyId)
			    			.exmnrsltId(exmnrsltId)
			    			.pollsterLc(pollsterLc)
			    			.exmnstartDt(exmnstartDt)
			    			.exmnendDt(exmnendDt)
			    			.build();
    }
}
