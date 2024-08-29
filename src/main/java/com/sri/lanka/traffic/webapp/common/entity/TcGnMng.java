package com.sri.lanka.traffic.webapp.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data //지방 사무국 관리	
@EqualsAndHashCode(callSuper=true)
public class TcGnMng extends BaseEntity {
	
	@Id
	private String gnId; //스리랑카지방사무국아이디
	
	private String provinCd; //스리랑카행정주코드
	
	private String provinNm; //스리랑카행정주명
	
	private String districtCd; //스리랑카행정구역코드
	
	private String districtNm; //스리랑카행정구역명
	
	private String dsdCd; //스리랑카사무국코드
	
	private String dsdNm; //스리랑카사무국명
	
	private String gnCd; //스리랑카지방사무국코드
	
	private String gnNm; //스리랑카지방사무국명

	private String gnNo; //스리랑카지방사무국번호
	
	private String useYn; //사용여부
	
}
