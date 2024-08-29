package com.sri.lanka.traffic.webapp.common.dto.invst;

import lombok.Data;

@Data
public class TlSrvyRsltPollsterInfoSaveDTO {

    private String pollsterId;
    private String pollsterNm;
    private String pollsterEmail;
    private String pollsterTel;
    private String gnCd;
    private String pollsterLc;
    private String cordonLine;
    private String tollBooth;

}
