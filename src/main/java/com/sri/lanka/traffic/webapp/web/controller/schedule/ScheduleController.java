package com.sri.lanka.traffic.webapp.web.controller.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sri.lanka.traffic.webapp.common.dto.invst.TmExmnMngDTO;
import com.sri.lanka.traffic.webapp.common.enums.code.ExmnSttsCd;
import com.sri.lanka.traffic.webapp.common.querydsl.QTmExmnMngRepository;
import com.sri.lanka.traffic.webapp.common.util.CommonUtils;
import com.sri.lanka.traffic.webapp.common.util.LoginUtils;
import com.sri.lanka.traffic.webapp.web.service.schedule.ScheduleMngService;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
	
	@Autowired
	QTmExmnMngRepository qTmExmnMngRepository;
	
	@Autowired
	ScheduleMngService scheduleMngService;

	/**
	 * @Method Name : schedule
	 * @작성일 : 2024. 2. 19
	 * @작성자 : NK.KIM
	 * @Method 설명 : 일정
	 * @param model
	 * @return
	 */
	@GetMapping
	public String schedule(Model model) {
		List<TmExmnMngDTO> invstCalendarList = qTmExmnMngRepository.getInvstScheduleListByNotSttsCd(ExmnSttsCd.INVEST_WRITING,LoginUtils.getExmnmngId());
		if(!CommonUtils.isListNull(invstCalendarList)) {
			String calendarData = scheduleMngService.getInvstScheduleListForCalendar(invstCalendarList); 
			model.addAttribute("calendarData", calendarData);
		}
		return "views/schedule/schedule";
	}

	/**
	 * @Method Name : scheduleDetail
	 * @작성일 : 2024. 2. 20
	 * @작성자 : NK.KIM
	 * @Method 설명 : 일정 상세
	 * @param model
	 * @return
	 */

	@GetMapping("/detail")
	public String scheduleDetail(Model model) {
		return "views/schedule/modal/scheduleDetail";
	}
}
