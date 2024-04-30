package com.sri.lanka.traffic.webapp.common.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.sri.lanka.traffic.webapp.common.enums.code.MvmnmeanTypeCd;
import com.sri.lanka.traffic.webapp.common.util.CommonUtils;

import lombok.Data;

@Entity
@Data //교통량 결과 정보
public class TlTrfvlInfo {

    @Id
    private String trfvlmrsltId = CommonUtils.getUuid(); //교통량 결과 정보 아이디

    private String trfvlmexmnId; //교통량 조사 아이디
    
    private LocalDateTime ftnminunitTime; //15분단위 시간
    
    @Convert(converter = MvmnmeanTypeCd.Converter.class)
    private MvmnmeanTypeCd mvmnmeanType; //이동 수단 유형
    
    private BigDecimal trfvlm; //교통량 
    
}
