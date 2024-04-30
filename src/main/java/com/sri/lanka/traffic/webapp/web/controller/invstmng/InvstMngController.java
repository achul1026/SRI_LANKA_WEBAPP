package com.sri.lanka.traffic.webapp.web.controller.invstmng;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sri.lanka.traffic.webapp.common.dto.invst.TlExmnRsltDetailSaveDTO;
import com.sri.lanka.traffic.webapp.common.dto.invst.TlExmnRsltHistoryDTO;
import com.sri.lanka.traffic.webapp.common.dto.invst.TlExmnRsltHistorySearchDTO;
import com.sri.lanka.traffic.webapp.common.dto.invst.TmSrvySectDetailDTO;
import com.sri.lanka.traffic.webapp.common.entity.TlExmnRslt;
import com.sri.lanka.traffic.webapp.common.entity.TlTrfvlInfo;
import com.sri.lanka.traffic.webapp.common.entity.TmExmnDrct;
import com.sri.lanka.traffic.webapp.common.entity.TmExmnMng;
import com.sri.lanka.traffic.webapp.common.enums.code.MvmnmeanTypeCd;
import com.sri.lanka.traffic.webapp.common.querydsl.QTlTrfvlRsltRepository;
import com.sri.lanka.traffic.webapp.common.querydsl.QTmExmnMngRepository;
import com.sri.lanka.traffic.webapp.common.repository.TlTrfvlInfoRepository;
import com.sri.lanka.traffic.webapp.common.repository.TmExmnDrctRepository;
import com.sri.lanka.traffic.webapp.common.util.CommonUtils;
import com.sri.lanka.traffic.webapp.common.util.LoginUtils;
import com.sri.lanka.traffic.webapp.support.exception.CommonException;
import com.sri.lanka.traffic.webapp.support.exception.CommonResponse;
import com.sri.lanka.traffic.webapp.support.exception.ErrorCode;
import com.sri.lanka.traffic.webapp.web.service.invstmng.InvstMngService;

@Controller
@RequestMapping("/invstmng")
public class InvstMngController {
	
	@Autowired
	QTmExmnMngRepository qtmExmnMngRepository;
	
	@Autowired
	TmExmnDrctRepository tmExmnDrctRepository;

	@Autowired
	QTlTrfvlRsltRepository qTlTrfvlRsltRepository;
	
	@Autowired
	TlTrfvlInfoRepository tlTrfvlInfoRepository;

	@Autowired
	InvstMngService invstMngService;
	/**
	  * @Method Name : invstSavePage
	  * @작성일 : 2024. 4. 8.
	  * @작성자 : NK.KIM
	  * @Method 설명 : 조사 등록 화면
	  * @param exmnmngId
	  * @return
	  */
	@GetMapping("/{exmnmngId}")
	public String invstSavePage(@PathVariable String exmnmngId, Model model) {
		//default return page
		String resultPage = "views/invstmng/invstTrafficSave";
		
		TmExmnMng invstInfo = qtmExmnMngRepository.getInvstInfoByExmnmngId(exmnmngId);
		if(CommonUtils.isNull(invstInfo)) {
			throw new CommonException(ErrorCode.NOT_FOUNT_INVST_INFO, "조사 가능 시간이 아닙니다. 조사 시간을 확인해주세요.");
		}
		TlExmnRslt invstRsltInfo = invstMngService.saveInvstRsltInfo(invstInfo);
		
		if("traffic".equals(invstInfo.getExmnType().getType())) {
			List<TmExmnDrct> exmnDrctList = tmExmnDrctRepository.findAllByExmnmngIdOrderByDrctSqnoAsc(exmnmngId);
			model.addAttribute("exmnDrctList", exmnDrctList);
		}
		model.addAttribute("invstRsltInfo", invstRsltInfo);
		
		//설문형 조사인경우 return html
		if("survey".equals(invstInfo.getExmnType().getType())) {
			resultPage = "views/invstmng/invstSurveySave";
		}
		
		return resultPage; 
	}

	/**
	  * @Method Name : invstSave
	  * @작성일 : 2024. 4. 11.
	  * @작성자 : NK.KIM
	  * @Method 설명 : 조사 결과 등록
	  * @param tlExmnRsltDetailSaveDTO
	  * @return
	  */
	@PostMapping
	public @ResponseBody CommonResponse<?> invstRsltSave(@RequestBody TlExmnRsltDetailSaveDTO tlExmnRsltDetailSaveDTO) {

		//교통량 조사 인 경우 같은 날의 조사방향에 취소사유가 있으면 return
		if("traffic".equals(tlExmnRsltDetailSaveDTO.getType())) {
			if(qTlTrfvlRsltRepository.existsInvstTrfvlInfoByLcchgRsnIsNotNull(tlExmnRsltDetailSaveDTO)){
				return CommonResponse.ResponseCodeAndMessage(ErrorCode.EXISTS_INVST_INFO.getStatus(), "선택하신 방향에 등록된 조사 불가 사유가 존재합니다. 관리자에게 문의하세요.");
			}
		}
		invstMngService.saveInvstTrfvlOrSrvyRsltInfo(tlExmnRsltDetailSaveDTO);
		//조사 불가 사유 입력 return
		if(!CommonUtils.isNull(tlExmnRsltDetailSaveDTO.getLcchgRsn())){
			return CommonResponse.ResponseCodeAndMessage(ErrorCode.EXISTS_INVST_INFO.getStatus(), "조사 불가 사유가 등록되었습니다.");
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("trfvlOrSrvyRsltId", tlExmnRsltDetailSaveDTO.getTrfvlOrSrvyId());
		return CommonResponse.successToData(result, "");
	}


	/**
	  * @Method Name : invstCountingSavePage
	  * @작성일 : 2024. 4. 9.
	  * @작성자 : NK.KIM
	  * @Method 설명 : 카운트 조사 화면
	  * @param trfvlmexmnId
	  * @param model
	  * @return
	  */
	@GetMapping("/{trfvlmexmnId}/counting")
	public String invstCountingSavePage(@PathVariable String trfvlmexmnId, Model model) {
		model.addAttribute("mvmnmeanTypeCd", MvmnmeanTypeCd.values());
		model.addAttribute("pageType", "COUNTING");
		model.addAttribute("trfvlmexmnId", trfvlmexmnId);
		return "views/invstmng/invstCountingSave";
	}
	
	/**
	  * @Method Name : invstCountingUpdatePage
	  * @작성일 : 2024. 4. 9.
	  * @작성자 : NK.KIM
	  * @Method 설명 : 카운트 수정 모달
	  * @param model
	  * @return
	  */
	@GetMapping("/counting/update")
	public String invstCountingUpdatePage(Model model) {
		return "views/invstmng/modal/invstCountingUpdate"; 
	}
	
	/**
	  * @Method Name : invstCountingHistoryPage
	  * @작성일 : 2024. 4. 16.
	  * @작성자 : NK.KIM
	  * @Method 설명 : TC 조사 이력 화면
	  * @param model
	  * @param tlExmnRsltHistoryDTO
	  * @return
	  */
	@GetMapping("/traffic/history")
	public String invstCountingHistoryPage(Model model,TlExmnRsltHistorySearchDTO tlExmnRsltHistorySearchDTO) {
		
		//방향 목록
		List<TmExmnDrct> exmnDrctList = tmExmnDrctRepository.findAllByExmnmngIdOrderByDrctSqnoAsc(tlExmnRsltHistorySearchDTO.getExmnmngId());
		model.addAttribute("exmnDrctList", exmnDrctList);
		
		//방향 파라미터가 존재하지않으면 목록의 첫번째 인덱스 값
		if(CommonUtils.isNull(tlExmnRsltHistorySearchDTO.getExmndrctId())) {
			tlExmnRsltHistorySearchDTO.setExmndrctId(exmnDrctList.get(0).getExmndrctId());
		}
		
		//시간 정보
		TlExmnRsltHistoryDTO tlExmnRsltHistoryDTO = invstMngService.getHistoryInfo(tlExmnRsltHistorySearchDTO);
		model.addAttribute("tlExmnRsltHistoryDTO", tlExmnRsltHistoryDTO);
		
		model.addAttribute("tlExmnRsltHistorySearchDTO", tlExmnRsltHistorySearchDTO);
		
		return "views/invstmng/invstTrafficHistory"; 
	}

	/**
	  * @Method Name : invstCountingSave
	  * @작성일 : 2024. 4. 15.
	  * @작성자 : NK.KIM
	  * @Method 설명 : 교통량 데이터 저장
	  * @param tlTrfvlInfoList
	  * @return
	  */
	@PostMapping("/counting")
	public @ResponseBody CommonResponse<?> invstCountingSave(@RequestBody List<TlTrfvlInfo> tlTrfvlInfoList) {
		try {
			tlTrfvlInfoRepository.saveAll(tlTrfvlInfoList);
		}catch (Exception e) {
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.INTERNAL_SERVER_ERROR, "DB 정보 등록에 실패했습니다. 관리자에게 문의해주세요.");
		}
		return CommonResponse.ResponseCodeAndMessage(HttpStatus.OK, "");
	}
	
	/**
	  * @Method Name : invstCountingSavePage
	  * @작성일 : 2024. 4. 9.
	  * @작성자 : NK.KIM
	  * @Method 설명 : 카운트 조사 화면
	  * @param trfvlmexmnId
	  * @param model
	  * @return
	  */
	@GetMapping("/{trfvlmexmnId}/question")
	public String invstQuestionSavePage(@PathVariable String trfvlmexmnId, Model model) {
		
		String exmnmngId = LoginUtils.getExmnmngId();
		
		//설문 정보 호출
		TmSrvySectDetailDTO tmSrvySectDetailDTO = invstMngService.getSrvySectInfo(exmnmngId);
		
		model.addAttribute("exmnMngSrvyList", tmSrvySectDetailDTO.getTmSrvySectList());
		model.addAttribute("pageType", "QUESTION");
		model.addAttribute("trfvlmexmnId", trfvlmexmnId);
		
		return "views/invstmng/invstQuestionSave";
	}
}
