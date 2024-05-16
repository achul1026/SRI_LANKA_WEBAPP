package com.sri.lanka.traffic.webapp.web.service.invstmng;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sri.lanka.traffic.webapp.common.dto.common.LoginExmnDTO;
import com.sri.lanka.traffic.webapp.common.dto.invst.TlExmnRsltDetailSaveDTO;
import com.sri.lanka.traffic.webapp.common.dto.invst.TlExmnRsltHistoryDTO;
import com.sri.lanka.traffic.webapp.common.dto.invst.TlExmnRsltHistorySearchDTO;
import com.sri.lanka.traffic.webapp.common.dto.invst.TmSrvySectDetailDTO;
import com.sri.lanka.traffic.webapp.common.dto.invst.TmSrvySectDetailDTO.TmSrvySectInfo;
import com.sri.lanka.traffic.webapp.common.dto.invst.TmSrvySectDetailDTO.TmSrvySectInfo.TmSrvyQstnInfo;
import com.sri.lanka.traffic.webapp.common.entity.TlExmnRslt;
import com.sri.lanka.traffic.webapp.common.entity.TlSrvyAns;
import com.sri.lanka.traffic.webapp.common.entity.TlSrvyRslt;
import com.sri.lanka.traffic.webapp.common.entity.TlTrfvlInfo;
import com.sri.lanka.traffic.webapp.common.entity.TlTrfvlRslt;
import com.sri.lanka.traffic.webapp.common.entity.TmExmnDrct;
import com.sri.lanka.traffic.webapp.common.entity.TmExmnMng;
import com.sri.lanka.traffic.webapp.common.entity.TmSrvyAns;
import com.sri.lanka.traffic.webapp.common.entity.TmSrvyQstn;
import com.sri.lanka.traffic.webapp.common.entity.TmSrvySect;
import com.sri.lanka.traffic.webapp.common.enums.code.ExmnSttsCd;
import com.sri.lanka.traffic.webapp.common.enums.code.QstnTypeCd;
import com.sri.lanka.traffic.webapp.common.repository.TlExmnRsltRepository;
import com.sri.lanka.traffic.webapp.common.repository.TlSrvyAnsRepository;
import com.sri.lanka.traffic.webapp.common.repository.TlSrvyRsltRepository;
import com.sri.lanka.traffic.webapp.common.repository.TlTrfvlInfoRepository;
import com.sri.lanka.traffic.webapp.common.repository.TlTrfvlRsltRepository;
import com.sri.lanka.traffic.webapp.common.repository.TmExmnDrctRepository;
import com.sri.lanka.traffic.webapp.common.repository.TmExmnMngRepository;
import com.sri.lanka.traffic.webapp.common.repository.TmSrvyAnsRepository;
import com.sri.lanka.traffic.webapp.common.repository.TmSrvyQstnRepository;
import com.sri.lanka.traffic.webapp.common.repository.TmSrvySectRepository;
import com.sri.lanka.traffic.webapp.common.util.CommonUtils;
import com.sri.lanka.traffic.webapp.common.util.LoginExmnUtils;
import com.sri.lanka.traffic.webapp.common.util.LoginUtils;
import com.sri.lanka.traffic.webapp.support.exception.CommonException;
import com.sri.lanka.traffic.webapp.support.exception.ErrorCode;

/**
  * @FileName : InvstMngService.java
  * @Project : sri_lanka_admin
  * @Date : 2024. 3. 28. 
  * @작성자 : NK.KIM
  * @프로그램 설명 :
  */
@Service
public class InvstMngService {

	@Autowired
	TmExmnMngRepository tmExmnMngRepository;
	
	@Autowired
	TlExmnRsltRepository tlExmnRsltRepository;

	@Autowired
	TlTrfvlRsltRepository tlTrfvlRsltRepository;

	@Autowired
	TlSrvyRsltRepository tlSrvyRsltRepository;
	
	@Autowired
	TmExmnDrctRepository tmExmnDrctRepository;

	@Autowired
	TmSrvySectRepository tmSrvySectRepository;

	@Autowired
	TmSrvyQstnRepository tmSrvyQstnRepository;
	
	@Autowired
	TmSrvyAnsRepository tmSrvyAnsRepository;
	
	@Autowired
	TlSrvyAnsRepository tlSrvyAnsRepository;
	
	@Autowired
	TlTrfvlInfoRepository tlTrfvlInfoRepository;
	
    /**
      * @Method Name : saveInvstRsltInfo
      * @작성일 : 2024. 4. 11.
      * @작성자 : NK.KIM
      * @Method 설명 : 결과 테이블 저장
      * @param tmExmnMng
      */
    public TlExmnRslt saveInvstRsltInfo(TmExmnMng tmExmnMng){
    	
    	TlExmnRslt result = null;
        
        try {
        	
        	result = tlExmnRsltRepository.findByExmnmngId(tmExmnMng.getExmnmngId());
        	if(CommonUtils.isNull(result)) {
        		result = new TlExmnRslt();
        		BeanUtils.copyProperties(tmExmnMng, result);
        		String exmnrsltId = CommonUtils.getUuid();
        		result.setExmnrsltId(exmnrsltId);
        		result.setExmnLat(tmExmnMng.getLat());
        		result.setExmnLon(tmExmnMng.getLon());
        		result.setRegistDt(LocalDateTime.now());
        		
        		tlExmnRsltRepository.save(result);
        	}
            
        } catch (Exception e) {
            throw new CommonException(ErrorCode.ENTITY_COPY_FAIL); 
        }

        return result;
    }
    
    /**
      * @Method Name : saveInvstTrfvlOrSrvyRsltInfo
      * @작성일 : 2024. 4. 12.
      * @작성자 : NK.KIM 
      * @Method 설명 : 교통량 / 설문 결과 테이블 정보 저장
      * @param tlExmnRsltDetailSaveDTO
      */
    public void saveInvstTrfvlOrSrvyRsltInfo(TlExmnRsltDetailSaveDTO tlExmnRsltDetailSaveDTO){
    	
    	Optional<TmExmnMng> invstInfo = tmExmnMngRepository.findById(tlExmnRsltDetailSaveDTO.getExmnmngId());
    	//조사 테이블
		if(!invstInfo.isPresent()) {
			
		}
		
		//조사 결과 테이블 
		Optional<TlExmnRslt> tlExmnRslt = tlExmnRsltRepository.findById(tlExmnRsltDetailSaveDTO.getExmnrsltId());
		if(!tlExmnRslt.isPresent()) {
			
		}
		
		try{
			
			//교통량 결과 db 저장
			if("traffic".equals(invstInfo.get().getExmnType().getType())) {
				TlTrfvlRslt tlTrfvlRslt = tlExmnRsltDetailSaveDTO.toTlTrfvlRslt();
				tlTrfvlRsltRepository.save(tlTrfvlRslt);

			}else {
			//설문형 결과 db 저장
			//당일 데이터는 당일에 1개씩만 insert 
				String exmnrsltId = tlExmnRsltDetailSaveDTO.getExmnrsltId();
				LocalDateTime exmnstartDt = tlExmnRsltDetailSaveDTO.getExmnstartDt();
				LocalDateTime exmnendDt = tlExmnRsltDetailSaveDTO.getExmnendDt();
				
				Optional<TlSrvyRslt> dbTlSrvyRslt = tlSrvyRsltRepository.findOneByExmnrsltIdAndExmnstartDtAndExmnendDt(exmnrsltId,exmnstartDt,exmnendDt);
				//당일 데이터가 없을때만 insert
				if(!dbTlSrvyRslt.isPresent()) {
					TlSrvyRslt tlSrvyRslt = tlExmnRsltDetailSaveDTO.toTlSrvyRslt();
					tlSrvyRsltRepository.save(tlSrvyRslt);
				}else {
					//존재할경우 SET srvyrsltId
					tlExmnRsltDetailSaveDTO.setTrfvlOrSrvyId(dbTlSrvyRslt.get().getSrvyrsltId());
				}
			}
			
			//조사 상태 값 진행중으로 변경 
			if("notYetProgress".equals(invstInfo.get().getSttsCd().getStatus())) {
				invstInfo.get().setSttsCd(ExmnSttsCd.INVEST_PROGRESS);
				tmExmnMngRepository.save(invstInfo.get());
			}
		}catch (Exception e) {
		}

    }
    
    
    /**
	  * @Method Name : getHourList
	  * @작성일 : 2024. 4. 9.
	  * @작성자 : SM.KIM
	  * @Method 설명 : 조사 시간 및 방향에 따른 시간 목록 생성
	  * @param exmnInfo
	  * @return
	  */
	public TlExmnRsltHistoryDTO getHistoryInfo(TlExmnRsltHistorySearchDTO tlExmnRsltHistorySearchDTO){
		TlExmnRsltHistoryDTO tlExmnRsltHistoryDTO = new TlExmnRsltHistoryDTO();
		List<Map<String,Object>> hourList = new ArrayList<>();
	    
		int startHour = Integer.parseInt(CommonUtils.formatLocalDateTime(tlExmnRsltHistorySearchDTO.getStartDt(), "HH"));
	    int endHour = Integer.parseInt(CommonUtils.formatLocalDateTime(tlExmnRsltHistorySearchDTO.getEndDt(), "HH"));
	    
	    LocalDate paramDate = null;
	    if(CommonUtils.isNull(tlExmnRsltHistorySearchDTO.getParamDate())){
	    	paramDate = LocalDate.now();
	    }else {
	    	paramDate = LocalDate.parse(tlExmnRsltHistorySearchDTO.getParamDate(), DateTimeFormatter.ISO_DATE); 
	    }
	    
	    //방향 정보
	    Optional<TmExmnDrct> exmnDrctInfo = tmExmnDrctRepository.findById(tlExmnRsltHistorySearchDTO.getExmndrctId());
	    
	    List<Map<String,Object>> dataInfoList = tlExmnRsltRepository.getTimeListForHistory(tlExmnRsltHistorySearchDTO.getExmnmngId(), paramDate
	    																					, exmnDrctInfo.get().getStartlcNm(), exmnDrctInfo.get().getEndlcNm());
		List<String> dataTimeList = dataInfoList.stream()
												.filter(x -> x.containsKey("dataHour") && CommonUtils.isNull(x.get("lcchgRsn")))
												.map(m -> m.get("dataHour").toString())
												.collect(Collectors.toList());
		int invstTotalCnt = 0;
		int invstCompletedCnt = 0;
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
	        invstTotalCnt++;
	    }
	    
	    tlExmnRsltHistoryDTO.setHourList(hourList);
	    
	    LoginExmnDTO exmnInfo = LoginUtils.getInvstInfo();
	    tlExmnRsltHistoryDTO.setStartDt(exmnInfo.getStartDt());
	    tlExmnRsltHistoryDTO.setEndDt(exmnInfo.getEndDt());
	    tlExmnRsltHistoryDTO.setExmnDiv(exmnInfo.getExmnDiv());
	    tlExmnRsltHistoryDTO.setInvstTotalCnt(invstTotalCnt);
	    
	    if(!CommonUtils.isListNull(dataTimeList)) invstCompletedCnt = dataTimeList.size();
	    tlExmnRsltHistoryDTO.setInvstCompletedCnt(invstCompletedCnt);
	    
	    int invstNotCompletedCnt = invstTotalCnt - invstCompletedCnt;
	    tlExmnRsltHistoryDTO.setInvstNotCompletedCnt(invstNotCompletedCnt);
	    
	    return tlExmnRsltHistoryDTO;
	}
	
	/**
	  * @Method Name : getSrvySectList
	  * @작성일 : 2024. 3. 25.
	  * @작성자 : NK.KIM
	  * @Method 설명 : 설문지 목록 조회
	  * @param exmnmngId
	  * @return
	  */
	public TmSrvySectDetailDTO getSrvySectInfo(String srvyId){
		TmSrvySectDetailDTO tmSrvySectDetailDTO = new TmSrvySectDetailDTO();
		List<TmSrvySect> dbTmSrvySectList = tmSrvySectRepository.findAllBySrvyIdOrderBySectSqnoAsc(srvyId);
		//설문 항목 목록 호출
		if(!CommonUtils.isNull(dbTmSrvySectList)) {
			
			//Entity -> set tmExmnMngSrvySectDTO 
			tmSrvySectDetailDTO.setTmSrvySectList(dbTmSrvySectList);
			
			//DTO에 설문 저장된 목록 For
			for(TmSrvySectInfo tmSrvySectInfo : tmSrvySectDetailDTO.getTmSrvySectList()) {
				
				//설문 질문 목록 호출
				List<TmSrvyQstn> dbTmSrvyQstnList = tmSrvyQstnRepository.findAllBySectIdOrderByQstnSqnoAsc(tmSrvySectInfo.getSectId());
				
				//Entity -> set TmSrvySectInfo				
				tmSrvySectInfo.setTmSrvyQstnList(dbTmSrvyQstnList);
				
				if(!CommonUtils.isNull(dbTmSrvyQstnList)) {
					
					//DTO에 질문 저장된 목록 For
					for(TmSrvyQstnInfo tmSrvyQstnInfo : tmSrvySectInfo.getTmSrvyQstnList()) {
						QstnTypeCd qstnTypeCd = tmSrvyQstnInfo.getQstnTypeCd();
						//답변은 RADIO or CHECKBOX
						if(QstnTypeCd.RADIO.equals(qstnTypeCd) || QstnTypeCd.CHECKBOX.equals(qstnTypeCd) || QstnTypeCd.SELECTBOX.equals(qstnTypeCd)) {
							//설문 답변 목록 호출
							List<TmSrvyAns> dbTmSrvyAnsList = tmSrvyAnsRepository.findAllByQstnIdOrderByAnsSqnoAsc(tmSrvyQstnInfo.getQstnId());
							if(!CommonUtils.isNull(dbTmSrvyAnsList)) {
								tmSrvyQstnInfo.setTmSrvyAnsList(dbTmSrvyAnsList);
							}
						}
					}
				}
			}
		}
		return tmSrvySectDetailDTO;
	}

	/**
	  * @Method Name : saveInvstQuestion
	  * @작성일 : 2024. 5. 14.
	  * @작성자 : SM.KIM
	  * @Method 설명 : 설문 답변 저장
	  * @param tlSrvyAnsList
	  */
	@Transactional
	public void saveInvstCountingOrQuestion(List<TlTrfvlInfo> tlTrfvlInfoList, List<TlSrvyAns> tlSrvyAnsList, String type) {
		if (type == "traffic") {
			tlTrfvlInfoRepository.saveAll(tlTrfvlInfoList);
		} else {
			tlSrvyAnsRepository.saveAll(tlSrvyAnsList);
		}
		
		String exmnmngId = LoginExmnUtils.getExmnmngId();
		TmExmnMng tmExmnMng = tmExmnMngRepository.findById(exmnmngId).get();
		
		tmExmnMng.setSttsCd(ExmnSttsCd.INVEST_COMPLETE);
		tmExmnMngRepository.save(tmExmnMng);
	}
	
}
