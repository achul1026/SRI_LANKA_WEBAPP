package com.sri.lanka.traffic.webapp.common.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data //설문조사 관리
@EqualsAndHashCode(callSuper=true)
public class TmSrvyInfo extends CreateEntity{

    @Id
    private String srvyId; //설문 정보 아이디

    private String srvyTitle; //설문명

    private String srvyType; //조사 종류

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDt; //시작 일시
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDt; //종료 일시

    
}
