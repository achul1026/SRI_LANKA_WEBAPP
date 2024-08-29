package com.sri.lanka.traffic.webapp.web.controller.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sri.lanka.traffic.webapp.common.entity.TlBbsInfo;
import com.sri.lanka.traffic.webapp.common.querydsl.QTlBbsInfoRepository;
import com.sri.lanka.traffic.webapp.common.repository.TlBbsInfoRepository;


@Controller
@RequestMapping("/notice")
public class NoticeController {

	@Autowired
	QTlBbsInfoRepository qTlBbsInfoRepository;
	
	@Autowired
	TlBbsInfoRepository tlBbsInfogRepository;
	
	/**
	 * @Method Name : noticeList
	 * @작성일 : 2024. 2. 20
	 * @작성자 : NK.KIM
	 * @Method 설명 : noticePage
	 * @param model
	 * @return
	 */

	@GetMapping
	public String notice(Model model) {
		List<TlBbsInfo> noticeList = qTlBbsInfoRepository.getTlBbsInfoList();
		model.addAttribute("noticeList", noticeList);
		return "views/notice/notice";
	}

	/**
	 * @Method Name : noticeDetail
	 * @작성일 : 2024. 2. 20
	 * @작성자 : NK.KIM
	 * @Method 설명 : noticeDetailPage
	 * @param model
	 * @return
	 */

	@GetMapping("/detail/{bbsId}")
	public String noticeDetail(Model model, @PathVariable String bbsId) {
		TlBbsInfo findAlarm = tlBbsInfogRepository.findById(bbsId).get();
		// 줄바꿈 적용
		String formattedContent = findAlarm.getBbsCnts().replace("\n", "<br>");
	    findAlarm.setBbsCnts(formattedContent);
		model.addAttribute("notice", findAlarm);
		model.addAttribute("pageType","notice");
		return "views/notice/noticeDetail";
	}
}
