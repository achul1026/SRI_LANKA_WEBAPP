package com.sri.lanka.traffic.webapp.web.service.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sri.lanka.traffic.webapp.common.dto.common.LoginExmnDTO;
import com.sri.lanka.traffic.webapp.common.repository.TlExmnRsltRepository;
import com.sri.lanka.traffic.webapp.common.util.CommonUtils;

@Service
public class MainService {
	
	@Autowired
	TlExmnRsltRepository tlExmnRsltRepository;
	
	/**
	  * @Method Name : getHourList
	  * @작성일 : 2024. 4. 9.
	  * @작성자 : SM.KIM
	  * @Method 설명 : 조사 시간에 따른 시간 목록 생성
	  * @param exmnInfo
	  * @return
	  */
	public List<Map<String,Object>> getHourList(LoginExmnDTO exmnInfo){
		List<Map<String,Object>> hourList = new ArrayList<>();
	    int startHour = Integer.parseInt(CommonUtils.formatLocalDateTime(exmnInfo.getStartDt(), "HH"));
	    int endHour = Integer.parseInt(CommonUtils.formatLocalDateTime(exmnInfo.getEndDt(), "HH"));
	    List<Map<String,Object>> dataInfoList = tlExmnRsltRepository.getTimeListForMain(exmnInfo.getExmnmngId(), LocalDate.now());
		List<String> dataTimeList = dataInfoList.stream().filter(x -> x.containsKey("dataHour") && CommonUtils.isNull(x.get("lcchgRsn"))).map(m -> m.get("dataHour").toString()).collect(Collectors.toList());
	    for (int hour = startHour; hour <= endHour; hour++) {
	    	Map<String,Object> hourInfo = new HashMap<>();
	    	boolean isExists = false;
	        String formattedHour = String.format("%02d:00", hour);
	        hourInfo.put("hour", formattedHour);
	        if(!CommonUtils.isListNull(dataTimeList) && dataTimeList.contains(formattedHour)) {
	        	isExists = true;
	        }
	        hourInfo.put("isExists", isExists);
	        hourList.add(hourInfo);
	    }
	    return hourList;
	}

}