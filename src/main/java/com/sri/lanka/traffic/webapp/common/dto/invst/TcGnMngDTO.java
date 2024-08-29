package com.sri.lanka.traffic.webapp.common.dto.invst;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sri.lanka.traffic.webapp.common.util.CommonUtils;
import lombok.Data;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TcGnMngDTO {

    private String provinCd; //스리랑카행정주코드

    private String provinNm; //스리랑카행정주명

    private String districtCd; //스리랑카행정구역코드

    private String districtNm; //스리랑카행정구역명

    private String dsdCd; //스리랑카사무국코드

    private String dsdNm; //스리랑카사무국명

    private List<GnInfo> gnInfoList; //GN정보목록

    @Data
    public static class GnInfo{
        private String gnCd;
        private String gnNm;
    }


    public void setJsonGnInfoList(String strGnInfo) {
        if(!CommonUtils.isNull(strGnInfo)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                List<GnInfo> gnInfoList = Arrays.asList(objectMapper.readValue(strGnInfo, GnInfo[].class))
                        .stream()
                        .sorted(Comparator.comparing(GnInfo::getGnCd))
                        .collect(Collectors.toList());
                this.gnInfoList = gnInfoList;
            } catch (JsonProcessingException e) {
                this.gnInfoList = null;
            }
        }
    }
}
