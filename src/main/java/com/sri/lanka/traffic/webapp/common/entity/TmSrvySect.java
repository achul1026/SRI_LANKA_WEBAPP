package com.sri.lanka.traffic.webapp.common.entity;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.sri.lanka.traffic.webapp.common.enums.code.SectTypeCd;

import lombok.Builder;
import lombok.Data;

@Entity
@Data //설문부문 관리
public class TmSrvySect{

    @Id
    private String sectId; //부문 아이디

    private String srvyId; //조사 아이디

    private String sectTitle; //부문 제목

    private String sectSubtitle; //부문 보조 제목
    
    @Convert(converter = SectTypeCd.Converter.class)
    private SectTypeCd sectType; //부문 유형

    private Integer sectSqno; //부문 순번
    
    public TmSrvySect(){}
    
    @Builder
	public TmSrvySect(String sectId, String srvyId, String sectTitle, String sectSubtitle, SectTypeCd sectType, Integer sectSqno) {
		this.sectId 		= sectId;
		this.srvyId 		= srvyId;
		this.sectTitle 		= sectTitle;
		this.sectSubtitle 	= sectSubtitle;
		this.sectType 		= sectType;
		this.sectSqno 		= sectSqno;
		
	}
}
