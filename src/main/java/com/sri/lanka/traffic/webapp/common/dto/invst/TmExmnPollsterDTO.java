package com.sri.lanka.traffic.webapp.common.dto.invst;

import com.sri.lanka.traffic.webapp.common.enums.code.PollsterTypeCd;

import lombok.Data;

@Data
public class TmExmnPollsterDTO {

    private String pollsterId;
    private String pollsterNm;
    private String pollsterEmail;
    private PollsterTypeCd pollsterType;
    private String pollsterTel;


}
