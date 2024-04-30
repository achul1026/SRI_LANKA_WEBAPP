package com.sri.lanka.traffic.webapp.web.controller.main;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sri.lanka.traffic.webapp.common.dto.common.LoginExmnDTO;
import com.sri.lanka.traffic.webapp.common.dto.invst.TmExmnPollsterDTO;
import com.sri.lanka.traffic.webapp.common.entity.TlBbsInfo;
import com.sri.lanka.traffic.webapp.common.querydsl.QTlBbsInfoRepository;
import com.sri.lanka.traffic.webapp.common.querydsl.QTmExmnPollsterRepository;
import com.sri.lanka.traffic.webapp.common.util.LoginUtils;
import com.sri.lanka.traffic.webapp.web.service.main.MainService;

@Controller
@RequestMapping("/main")
public class MainController {
	
	@Autowired
	QTlBbsInfoRepository qTlBbsInfoRepository;
	
	@Autowired
	QTmExmnPollsterRepository qTmExmnPollsterRepository;
	
	@Autowired
	MainService mainService;
	
	/**
	  * @Method Name : main
	  * @작성일 : 2024. 3. 25.
	  * @작성자 : SM.KIM
	  * @Method 설명 : 메인 페이지
	  * @param model
	  * @return
	  */
	@GetMapping
	public String main(Model model) {
		String exmnmngId = LoginUtils.getExmnmngId();
		LoginExmnDTO exmnInfo = LoginUtils.getInvstInfo();
		
		if("traffic".equals(exmnInfo.getExmnType().getType())) {
			List<Map<String,Object>> hourList = mainService.getHourList(exmnInfo);
			model.addAttribute("hourList", hourList);
		}

		// 갯수 제한 없는 공지사항 목록 조회 (4개)
		List<TlBbsInfo> noticeList = qTlBbsInfoRepository.getTlBbsInfoList(4);
		List<TmExmnPollsterDTO> pollsterInfo = qTmExmnPollsterRepository.getPollsterInfo(exmnmngId);
		
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("pollsterInfo", pollsterInfo);
		model.addAttribute("exmnInfo", exmnInfo);
		model.addAttribute("exmnmngId", exmnmngId);
		return "views/main/main";
	}
}
