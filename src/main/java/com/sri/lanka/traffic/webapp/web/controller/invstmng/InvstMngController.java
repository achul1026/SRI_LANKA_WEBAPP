package com.sri.lanka.traffic.webapp.web.controller.invstmng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

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

import com.sri.lanka.traffic.webapp.common.dto.common.LoginExmnDTO;
import com.sri.lanka.traffic.webapp.common.dto.invst.TcGnMngDTO;
import com.sri.lanka.traffic.webapp.common.dto.invst.TlExmnRsltDetailSaveDTO;
import com.sri.lanka.traffic.webapp.common.dto.invst.TlSrvyAnsSaveDTO;
import com.sri.lanka.traffic.webapp.common.dto.invst.TlSrvyRsltPollsterInfoSaveDTO;
import com.sri.lanka.traffic.webapp.common.dto.invst.TmExmnPollsterDTO;
import com.sri.lanka.traffic.webapp.common.dto.invst.TmSrvySectDetailDTO;
import com.sri.lanka.traffic.webapp.common.entity.TlExmnRslt;
import com.sri.lanka.traffic.webapp.common.entity.TlTrfvlInfo;
import com.sri.lanka.traffic.webapp.common.entity.TmExmnDrct;
import com.sri.lanka.traffic.webapp.common.entity.TmExmnMng;
import com.sri.lanka.traffic.webapp.common.entity.TmExmnPollster;
import com.sri.lanka.traffic.webapp.common.enums.code.MvmnmeanTypeCd;
import com.sri.lanka.traffic.webapp.common.enums.code.SectTypeCd;
import com.sri.lanka.traffic.webapp.common.querydsl.QTcGnMngRepository;
import com.sri.lanka.traffic.webapp.common.querydsl.QTlTrfvlRsltRepository;
import com.sri.lanka.traffic.webapp.common.querydsl.QTmExmnMngRepository;
import com.sri.lanka.traffic.webapp.common.querydsl.QTmExmnPollsterRepository;
import com.sri.lanka.traffic.webapp.common.repository.TlSrvyAnsRepository;
import com.sri.lanka.traffic.webapp.common.repository.TlTrfvlInfoRepository;
import com.sri.lanka.traffic.webapp.common.repository.TlTrfvlRsltRepository;
import com.sri.lanka.traffic.webapp.common.repository.TmExmnDrctRepository;
import com.sri.lanka.traffic.webapp.common.repository.TmExmnPollsterRepository;
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
	QTcGnMngRepository qTcGnMngRepository;
	
	@Autowired
	TlTrfvlRsltRepository tlTrfvlRsltRepository;
	
	@Autowired
	TlTrfvlInfoRepository tlTrfvlInfoRepository;
	
	@Autowired
	TlSrvyAnsRepository tlSrvyAnsRepository;
	
	@Autowired
	InvstMngService invstMngService;

	@Autowired
	QTmExmnPollsterRepository qTmExmnPollsterRepository;
	
	@Autowired
	TmExmnPollsterRepository tmExmnPollsterRepository;

	/**
	  * @Method Name : invstSavePage
	  * @작성일 : 2024. 4. 8.
	  * @작성자 : NK.KIM
	  * @Method 설명 : 조사 등록 화면
	  * @param exmnmngId
	  * @return
	  */
	@GetMapping("/{exmnmngId}")
	public String invstSavePage(@PathVariable String exmnmngId, Model model, HttpSession httpSession) {
		//default return page
		String resultPage = "views/invstmng/invstTrafficSave";
		LoginExmnDTO exmnInfo = LoginUtils.getInvstInfo();
		TmExmnMng invstInfo = qtmExmnMngRepository.getInvstInfoByExmnmngId(exmnmngId,exmnInfo.getExmnType());
		if(CommonUtils.isNull(invstInfo)) {
			String resMsg = CommonUtils.getMessage("invest.invstSavePage.notExist");
			throw new CommonException(ErrorCode.NOT_FOUNT_INVST_INFO, resMsg);
		}

		TlExmnRslt invstRsltInfo = invstMngService.saveInvstRsltInfo(invstInfo);

		List<TmExmnDrct> exmnDrctList = tmExmnDrctRepository.findAllByExmnmngIdOrderByDrctSqnoAsc(exmnmngId);
		model.addAttribute("exmnDrctList", exmnDrctList);
		model.addAttribute("invstRsltInfo", invstRsltInfo);
		List<TmExmnPollsterDTO> pollsterList = qTmExmnPollsterRepository.getPollsterList(exmnmngId);
		model.addAttribute("pollsterList", pollsterList);
		if("survey".equals(invstInfo.getExmnType().getType())) {
			TcGnMngDTO locationInfo = qTcGnMngRepository.getGnLocationList(invstRsltInfo);
			model.addAttribute("locationInfo", locationInfo);
			model.addAttribute("exmnInfo", exmnInfo);
			resultPage = "views/invstmng/pollsterSave";
		} else if ("traffic".equals(invstInfo.getExmnType().getType())) {
			model.addAttribute("roadDescr", invstInfo.getRoadDescr());
		}

		//조사원 세션값 지우기
		TlSrvyRsltPollsterInfoSaveDTO tlSrvyRsltPollsterInfoSaveDTO = (TlSrvyRsltPollsterInfoSaveDTO) httpSession.getAttribute("pollsterInfo");
		if(!CommonUtils.isNull(tlSrvyRsltPollsterInfoSaveDTO)) httpSession.removeAttribute("pollsterInfo");
		if(!CommonUtils.isNull(tlSrvyRsltPollsterInfoSaveDTO)) httpSession.removeAttribute("tlTrfvlRsltInfo");
		
		return resultPage;
	}

	@GetMapping("/{exmnmngId}/survey")
	public String invstSurveySavePage(@PathVariable String exmnmngId, Model model) {

		//default return page 설문형 인데 방향이 있는 경우(EX AXLEROAD)
		String resultPage = "views/invstmng/invstSurveySave";
		LoginExmnDTO exmnInfo = LoginUtils.getInvstInfo();
		TmExmnMng invstInfo = qtmExmnMngRepository.getInvstInfoByExmnmngId(exmnmngId,exmnInfo.getExmnType());
		if(CommonUtils.isNull(invstInfo)) {
			String resMsg = CommonUtils.getMessage("invest.invstSurveySavePage.not.able.time");
			throw new CommonException(ErrorCode.NOT_FOUNT_INVST_INFO, resMsg);
		}
		TlExmnRslt invstRsltInfo = invstMngService.saveInvstRsltInfo(invstInfo);
		List<TmExmnDrct> exmnDrctList = tmExmnDrctRepository.findAllByExmnmngIdOrderByDrctSqnoAsc(exmnmngId);
		model.addAttribute("exmnDrctList", exmnDrctList);

		if("false".equals(invstInfo.getExmnType().getHasDrct())) {
			//설문형 이고 방향 정보가 없는 조사 인경우 바로 설문 화면으로
			resultPage = "redirect:/invstmng/"+invstRsltInfo.getExmnrsltId()+"/question";
		}
		model.addAttribute("invstRsltInfo", invstRsltInfo);

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
	public @ResponseBody CommonResponse<?> invstRsltSave(@RequestBody TlExmnRsltDetailSaveDTO tlExmnRsltDetailSaveDTO, HttpSession httpSession) {

		String resMsg;
		//교통량 조사 인 경우 같은 날의 조사방향에 취소사유가 있으면 return
		if("traffic".equals(tlExmnRsltDetailSaveDTO.getType())) {
			if(qTlTrfvlRsltRepository.existsInvstTrfvlInfoByLcchgRsnIsNotNull(tlExmnRsltDetailSaveDTO)){
				resMsg = CommonUtils.getMessage("invest.invstRsltSave.exist.not.able.reason");
				return CommonResponse.ResponseCodeAndMessage(ErrorCode.EXISTS_INVST_INFO.getStatus(), resMsg);
			}
		}
		//조사 불가 사유 입력 return
		if(!CommonUtils.isNull(tlExmnRsltDetailSaveDTO.getLcchgRsn())){
			invstMngService.saveInvstTrfvlRsltInfo(tlExmnRsltDetailSaveDTO);
			resMsg = CommonUtils.getMessage("invest.invstRsltSave.regist.not.able.reason");
			return CommonResponse.ResponseCodeAndMessage(ErrorCode.EXISTS_INVST_INFO.getStatus(), resMsg);
		}
		httpSession.setAttribute("tlTrfvlRsltInfo", tlExmnRsltDetailSaveDTO);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("trfvlOrSrvyRsltId", tlExmnRsltDetailSaveDTO.getTrfvlOrSrvyId());
		return CommonResponse.successToData(result, "");
	}

	/**
	 * @Method Name : invstSave
	 * @작성일 : 2024. 4. 11.
	 * @작성자 : NK.KIM
	 * @Method 설명 : 설문형 조사 조사원 정보 세션 저장
	 * @param tlSrvyRsltPollsterInfoSaveDTO
	 * @return
	 */
	@PostMapping("/pollster")
	public @ResponseBody CommonResponse<?> invstPollsterSave(@RequestBody TlSrvyRsltPollsterInfoSaveDTO tlSrvyRsltPollsterInfoSaveDTO, HttpSession httpSession) {
		TmExmnPollster tmExmnPollster = tmExmnPollsterRepository.findOneByPollsterIdAndPollsterTel(tlSrvyRsltPollsterInfoSaveDTO.getPollsterId(),tlSrvyRsltPollsterInfoSaveDTO.getPollsterTel());
		if(CommonUtils.isNull(tmExmnPollster)){
			String resMsg = CommonUtils.getMessage("invest.invstPollsterSave.notExist");
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.NOT_FOUND, resMsg);
		}
		tlSrvyRsltPollsterInfoSaveDTO.setPollsterNm(tmExmnPollster.getPollsterNm());
		tlSrvyRsltPollsterInfoSaveDTO.setPollsterEmail(tmExmnPollster.getPollsterEmail());
		LoginUtils.getInvstInfo().setPollsterTel(tlSrvyRsltPollsterInfoSaveDTO.getPollsterTel());
		LoginUtils.getInvstInfo().setPollsterId(tmExmnPollster.getPollsterId());
		httpSession.setAttribute("pollsterInfo", tlSrvyRsltPollsterInfoSaveDTO);
		Map<String, Object> result = new HashMap<String, Object>();
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
	public String invstCountingSavePage(@PathVariable String trfvlmexmnId, Model model, HttpSession httpSession) {
		
		TlExmnRsltDetailSaveDTO tlExmnRsltDetailSaveDTO = (TlExmnRsltDetailSaveDTO) httpSession.getAttribute("tlTrfvlRsltInfo");
//				tlTrfvlRsltRepository.findById(trfvlmexmnId);
//		if(!tlTrfvlRslt.isPresent()) {
			//exception
//		}
		model.addAttribute("mvmnmeanTypeCd", MvmnmeanTypeCd.values());
		model.addAttribute("pageType", "COUNTING");
		model.addAttribute("trfvlmexmnId", trfvlmexmnId);
		model.addAttribute("tlTrfvlRslt", tlExmnRsltDetailSaveDTO.toTlTrfvlRslt());
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
	  * @Method Name : invstCountingSave
	  * @작성일 : 2024. 4. 15.
	  * @작성자 : NK.KIM
	  * @Method 설명 : 교통량 데이터 저장
	  * @param tlTrfvlInfoList
	  * @return
	  */
	@PostMapping("/counting")
	public @ResponseBody CommonResponse<?> invstCountingSave(@RequestBody List<TlTrfvlInfo> tlTrfvlInfoList, HttpSession httpSession) {

		String resMsg;
		TlExmnRsltDetailSaveDTO tlExmnRsltDetailSaveDTO = (TlExmnRsltDetailSaveDTO) httpSession.getAttribute("tlTrfvlRsltInfo");
		try {
			invstMngService.saveInvstTrfvlRsltInfo(tlExmnRsltDetailSaveDTO);
			invstMngService.saveInvstCounting(tlTrfvlInfoList);
		}catch (Exception e) {
			resMsg = CommonUtils.getMessage("invest.invstCountingSave.fail");
			if(!CommonUtils.isNull(tlExmnRsltDetailSaveDTO)) httpSession.removeAttribute("tlTrfvlRsltInfo");
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.INTERNAL_SERVER_ERROR, resMsg);
		}
		if(!CommonUtils.isNull(tlExmnRsltDetailSaveDTO)) httpSession.removeAttribute("tlTrfvlRsltInfo");
		LoginExmnDTO exmnInfo = LoginUtils.getInvstInfo();
		resMsg = CommonUtils.getMessage("invest.invstCountingSave.complete");
		return CommonResponse.ResponseCodeAndMessage(HttpStatus.OK, exmnInfo.getExmnType().getName() + resMsg);
	}
	
	/**
	 * @Method Name : invstQuestionSave
	 * @작성일 : 2024. 4. 15.
	 * @작성자 : NK.KIM
	 * @Method 설명 : 설문형 데이터 저장
	 * @param tlSrvyAnsList
	 * @return
	 */
	@PostMapping("/question")
	public @ResponseBody CommonResponse<?> invstQuestionSave(@RequestBody TlSrvyAnsSaveDTO tlSrvyAnsSaveDTO, HttpSession httpSession) {

		String resMsg;
		TlSrvyRsltPollsterInfoSaveDTO tlSrvyRsltPollsterInfoSaveDTO = (TlSrvyRsltPollsterInfoSaveDTO) httpSession.getAttribute("pollsterInfo");
		if(CommonUtils.isNull(tlSrvyRsltPollsterInfoSaveDTO)){
			resMsg = CommonUtils.getMessage("invest.invstPollsterSave.notExist");
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.INTERNAL_SERVER_ERROR, resMsg);
		}
		try {
			invstMngService.saveInvstQuestion(tlSrvyAnsSaveDTO,tlSrvyRsltPollsterInfoSaveDTO);
		}catch (Exception e) {
			resMsg = CommonUtils.getMessage("invest.invstCountingSave.fail");
			if(!CommonUtils.isNull(tlSrvyRsltPollsterInfoSaveDTO)) httpSession.removeAttribute("pollsterInfo");
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.INTERNAL_SERVER_ERROR, resMsg);
		}

		//조사원 세션값 지우기
		if(!CommonUtils.isNull(tlSrvyRsltPollsterInfoSaveDTO)) httpSession.removeAttribute("pollsterInfo");

		LoginExmnDTO exmnInfo = LoginUtils.getInvstInfo();
		resMsg = CommonUtils.getMessage("invest.invstCountingSave.complete");
		return CommonResponse.ResponseCodeAndMessage(HttpStatus.OK, exmnInfo.getExmnType().getName() + resMsg);
	}
	
	/**
	  * @Method Name : invstCountingSavePage
	  * @작성일 : 2024. 4. 9.
	  * @작성자 : NK.KIM
	  * @Method 설명 : 설문 조사 화면
	  * @param exmnrsltId
	 * 	@param exmndrctId
	  * @param model
	  * @return
	  */
	@GetMapping("/{exmnrsltId}/question")
	public String invstQuestionSavePage(Model model, @PathVariable String exmnrsltId, String exmndrctId, HttpSession httpSession) {
		
		LoginExmnDTO exmnInfo = LoginUtils.getInvstInfo();

		List<String> optionalMetadatas = new ArrayList<>();
		for(int i = 27; i < 47;i++){
			/*Transfer input optional*/
			optionalMetadatas.add("SMD0"+String.valueOf(i));
		}
		// optionalMetadatas.add("SMD054");
		optionalMetadatas.add("SMD055"); 
		optionalMetadatas.add("SMD056");
		optionalMetadatas.add("SMD057");
		optionalMetadatas.add("SMD058");
		optionalMetadatas.add("SMD059");
		optionalMetadatas.add("SMD060");
		// optionalMetadatas.add("SMD061");
		model.addAttribute("optionalMetadatas", optionalMetadatas);
		String[] transferArrays = {
				"SMD027","SMD028","SMD029","SMD030","SMD056",
				"SMD031","SMD032","SMD033","SMD034","SMD057",
				"SMD035","SMD036","SMD037","SMD038","SMD058",
				"SMD039","SMD040","SMD041","SMD042","SMD059",
				"SMD043","SMD044","SMD045","SMD046","SMD060"
		};
		//설문 정보 호출
		TmSrvySectDetailDTO tmSrvySectDetailDTO = invstMngService.getSrvySectInfo(exmnInfo.getSrvyId());
		if(!CommonUtils.isNull(exmndrctId)) {
			Optional<TmExmnDrct> tmExmnDrct = tmExmnDrctRepository.findById(exmndrctId);
			tmExmnDrct.ifPresent(exmnDrct -> model.addAttribute("tmExmnDrct", exmnDrct));
		}
		TlSrvyRsltPollsterInfoSaveDTO tlSrvyRsltPollsterInfoSaveDTO = (TlSrvyRsltPollsterInfoSaveDTO) httpSession.getAttribute("pollsterInfo");
		List<TmSrvySectDetailDTO.TmSrvySectInfo> exmnMngSrvyList = tmSrvySectDetailDTO.getTmSrvySectList();
		boolean hasTrafficBool = exmnMngSrvyList.stream().filter(x -> x.getSectType().equals(SectTypeCd.TRAFFIC)).findFirst().isPresent();
		model.addAttribute("exmnMngSrvyList", exmnMngSrvyList);
		model.addAttribute("hasTrafficBool", hasTrafficBool);
		model.addAttribute("pageType", "QUESTION");
		model.addAttribute("srvyrsltId", CommonUtils.getUuid());
		model.addAttribute("exmnrsltId", exmnrsltId);
		model.addAttribute("transferArrays", transferArrays);
		model.addAttribute("tlSrvyRsltPollsterInfoSaveDTO", tlSrvyRsltPollsterInfoSaveDTO);
		
		return "views/invstmng/invstQuestionSave";
	}
	
	
	/**
	 * @Method Name : location
	 * @작성일 : 2024. 5. 2
	 * @작성자 : TY.LEE
	 * @Method 설명 : locationSave
	 * @param model
	 * @return
	 */

	@GetMapping("/location/save")
	public String locationSave(Model model) {
		return "views/invstmng/modal/invstLocationSave";
	}	
	
	/**
	  * @Method Name : invstCountingUpdatePage
	  * @작성일 : 2024. 6. 11.
	  * @작성자 : TY.LEE
	  * @Method 설명 : GPS 좌표 설정
	  * @param model
	  * @return
	  */
	@GetMapping("/gps/save")
	public String gpsSave(Model model) {
		return "views/invstmng/modal/invstGpsSave"; 
	}	
	
}
