package com.sri.lanka.traffic.webapp.web.service.schedule;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sri.lanka.traffic.webapp.common.dto.invst.TmExmnMngDTO;
import com.sri.lanka.traffic.webapp.common.dto.invst.TmExmnScheduleDTO;
import com.sri.lanka.traffic.webapp.common.util.CommonUtils;

/**
  * @FileName : ScheduleMngService.java
  * @Project : sri_lanka_admin
  * @Date : 2024. 3. 28. 
  * @작성자 : NK.KIM
  * @프로그램 설명 :
  */
@Service
public class ScheduleMngService {

	/**
	  * @Method Name : getInvstScheduleListForCalendar
	  * @작성일 : 2024. 4. 3.
	  * @작성자 : NK.KIM
	  * @Method 설명 : 조사 현황 및 이력 > 달력 데이터 세팅
	  * @param invstList
	  * @return
	  */
	public String getInvstScheduleListForCalendar(List<TmExmnMngDTO> invstList) {
		List<TmExmnScheduleDTO> tmExmnScheduleDTOList = invstList.stream()
														    .map(invst -> {
														    	TmExmnScheduleDTO tmExmnScheduleDTO = new TmExmnScheduleDTO();
														    	tmExmnScheduleDTO.setBackgroundColor(invst.getColrCd().getName());
														    	tmExmnScheduleDTO.setStart(CommonUtils.formatLocalDateTime(invst.getStartDt(),"yyyy-MM-dd"));
														    	tmExmnScheduleDTO.setEnd(CommonUtils.formatLocalDateTime(invst.getEndDt().plusDays(1),"yyyy-MM-dd"));
														        return tmExmnScheduleDTO;
														    })
														    .collect(Collectors.toList());
		
		return CommonUtils.getListToJsonString(tmExmnScheduleDTOList);
	}
}
