package com.sri.lanka.traffic.webapp.web.controller.main;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sri.lanka.traffic.webapp.common.dto.common.LoginExmnDTO;
import com.sri.lanka.traffic.webapp.common.entity.TlBbsInfo;
import com.sri.lanka.traffic.webapp.common.entity.TlTrfvlRslt;
import com.sri.lanka.traffic.webapp.common.enums.code.ExmnTypeCd;
import com.sri.lanka.traffic.webapp.common.querydsl.QTlBbsInfoRepository;
import com.sri.lanka.traffic.webapp.common.querydsl.QTlExmnRsltRepository;
import com.sri.lanka.traffic.webapp.common.repository.TlExmnRsltRepository;
import com.sri.lanka.traffic.webapp.common.util.CommonUtils;
import com.sri.lanka.traffic.webapp.common.util.LoginUtils;
import com.sri.lanka.traffic.webapp.support.exception.CommonResponse;
import com.sri.lanka.traffic.webapp.web.service.main.MainService;

@Controller
@RequestMapping("/main")
public class MainController {
	
	@Autowired
	QTlBbsInfoRepository qTlBbsInfoRepository;

	@Autowired
	TlExmnRsltRepository tlExmnRsltRepository;
	@Autowired
	QTlExmnRsltRepository qTlExmnRsltRepository;
	
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
		// 갯수 제한 없는 공지사항 목록 조회 (5개)
		List<TlBbsInfo> noticeList = qTlBbsInfoRepository.getTlBbsInfoList(5);
		List<Map<String,Object>> exmnRsltList = null;
		if("survey".equals(exmnInfo.getExmnType().getType())){
			String srvyMetadataCd = "SMD014";
			if(ExmnTypeCd.ROADSIDE.equals(exmnInfo.getExmnType())){
				srvyMetadataCd = "SMD065"; //차종 타입
			}
			exmnRsltList = tlExmnRsltRepository.getSrvyRsltListForMain(exmnmngId,exmnInfo.getExmnType().getCode(),srvyMetadataCd,null);
			List<String> pollsterList = qTlExmnRsltRepository.getSrvyRsltPollsterListForMain(exmnmngId,exmnInfo.getExmnType());
			model.addAttribute("pollsterList", pollsterList);
		}else{
			//MCC/TM
			exmnRsltList = tlExmnRsltRepository.getTrfvlRsltListForMain(exmnmngId,exmnInfo.getExmnType().getCode(),null,null);
			List<TlTrfvlRslt> drctList = qTlExmnRsltRepository.getTrfvlRsltPollsterListForMain(exmnmngId,exmnInfo.getExmnType());
			model.addAttribute("drctList", drctList);
		}
		
		//List<Map<String,Object>>
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("exmnRsltList", exmnRsltList);
		model.addAttribute("exmnInfo", exmnInfo);
		model.addAttribute("exmnmngId", exmnmngId);
		return "views/main/main";
	}

	@GetMapping("/exmnrslt/search")
	public @ResponseBody CommonResponse<?> exmnRsltSearch(@RequestParam(name = "pollsterNm", required = false) String pollsterNm
															,@RequestParam(name = "startlcNm", required = false) String startlcNm
															,@RequestParam(name = "endlcNm", required = false) String endlcNm
															) {
		LoginExmnDTO exmnInfo = LoginUtils.getInvstInfo();
		List<Map<String,Object>> exmnRsltList = null;
		if("survey".equals(exmnInfo.getExmnType().getType())){
			String srvyMetadataCd = "SMD014";
			if(ExmnTypeCd.ROADSIDE.equals(exmnInfo.getExmnType())){
				srvyMetadataCd = "SMD065"; //차종 타입
			}
			if(CommonUtils.isNull(pollsterNm)){
				pollsterNm = null;
			}
			exmnRsltList = tlExmnRsltRepository.getSrvyRsltListForMain(exmnInfo.getExmnmngId(),exmnInfo.getExmnType().getCode(),srvyMetadataCd,pollsterNm);
		} else {
			if(CommonUtils.isNull(startlcNm)){
				startlcNm = null;
			}
			if(CommonUtils.isNull(endlcNm)){
				endlcNm = null;
			}
			exmnRsltList = tlExmnRsltRepository.getTrfvlRsltListForMain(exmnInfo.getExmnmngId(),exmnInfo.getExmnType().getCode(),startlcNm,endlcNm);
		}
		return CommonResponse.ResponseCodeAndMessage(HttpStatus.OK, "",exmnRsltList);
	}


}
