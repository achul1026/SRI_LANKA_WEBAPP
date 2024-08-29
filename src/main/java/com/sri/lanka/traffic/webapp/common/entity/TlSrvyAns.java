package com.sri.lanka.traffic.webapp.common.entity;

import com.sri.lanka.traffic.webapp.common.util.CommonUtils;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data //설문조사 답변정보
public class TlSrvyAns {

    @Id
    private String srvyansId = CommonUtils.getUuid(); //설문 답변 아이디

    private String srvyrsltId; //설문 결과 아이디
    
    private String sectType; //섹션 타입
    private BigDecimal sectSqno;

    private String qstnTitle; //질문 제목
    
    private String qstnType; //질문 유형
    
    private BigDecimal qstnSqno; //질문 순서

    private String ansCnts; //답변 내용

    private String etcYn = "N"; // 기타 유무

    private String srvyMetadataCd;


	public void setAnsCnts(String ansCnts) {
		if(CommonUtils.isNull(ansCnts)) {
			ansCnts = null;
		}
		this.ansCnts = ansCnts;
	}
    
    
}
