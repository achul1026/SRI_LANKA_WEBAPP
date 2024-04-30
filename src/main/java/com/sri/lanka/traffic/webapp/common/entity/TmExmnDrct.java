package com.sri.lanka.traffic.webapp.common.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data //조사 방향 관리
public class TmExmnDrct {

    @Id
    private String exmndrctId; // 조사방향 아이디

    private String exmnmngId; //조사 관리 아이디

    private Integer drctSqno; //방향 순서

    private String startlcNm; //시작위치명

    private String endlcNm; //종료위치명
    
}
