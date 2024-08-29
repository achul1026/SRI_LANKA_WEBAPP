package com.sri.lanka.traffic.webapp.web.service.invstmng;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sri.lanka.traffic.webapp.common.dto.invst.TlExmnRsltDetailSaveDTO;
import com.sri.lanka.traffic.webapp.common.dto.invst.TlSrvyAnsSaveDTO;
import com.sri.lanka.traffic.webapp.common.dto.invst.TlSrvyRsltPollsterInfoSaveDTO;
import com.sri.lanka.traffic.webapp.common.dto.invst.TmSrvySectDetailDTO;
import com.sri.lanka.traffic.webapp.common.dto.invst.TmSrvySectDetailDTO.TmSrvySectInfo;
import com.sri.lanka.traffic.webapp.common.dto.invst.TmSrvySectDetailDTO.TmSrvySectInfo.TmSrvyQstnInfo;
import com.sri.lanka.traffic.webapp.common.entity.TlExmnRslt;
import com.sri.lanka.traffic.webapp.common.entity.TlTrfvlInfo;
import com.sri.lanka.traffic.webapp.common.entity.TlTrfvlRslt;
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
				if(CommonUtils.isNull(result)){
					result = new TlExmnRslt();
					String exmnrsltId = CommonUtils.getUuid();
					result.setExmnrsltId(exmnrsltId);
        			result.setRegistDt(LocalDateTime.now());
				}
        		result.setExmnLat(tmExmnMng.getLat());
        		result.setExmnLon(tmExmnMng.getLon());

				//조사 상태 값 진행중으로 변경
//				tmExmnMng.setSttsCd(ExmnSttsCd.INVEST_PROGRESS);
				tmExmnMngRepository.save(tmExmnMng);

				BeanUtils.copyProperties(tmExmnMng, result);
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
    public void saveInvstTrfvlRsltInfo(TlExmnRsltDetailSaveDTO tlExmnRsltDetailSaveDTO){
    	
    	Optional<TmExmnMng> invstInfo = tmExmnMngRepository.findById(tlExmnRsltDetailSaveDTO.getExmnmngId());
    	//조사 테이블
		if(!invstInfo.isPresent()) {
			throw new CommonException(ErrorCode.NOT_FOUNT_INVST_INFO);
		}
		
		//조사 결과 테이블 
		Optional<TlExmnRslt> tlExmnRslt = tlExmnRsltRepository.findById(tlExmnRsltDetailSaveDTO.getExmnrsltId());
		if(!tlExmnRslt.isPresent()) {
			throw new CommonException(ErrorCode.NOT_FOUNT_INVST_INFO);
		}
		
		try{
			//교통량 결과 db 저장
			TlTrfvlRslt tlTrfvlRslt = tlExmnRsltDetailSaveDTO.toTlTrfvlRslt();
			tlTrfvlRsltRepository.save(tlTrfvlRslt);

			//조사 상태 값 진행중으로 변경
			if("notYetProgress".equals(invstInfo.get().getSttsCd().getStatus())) {
				invstInfo.get().setSttsCd(ExmnSttsCd.INVEST_PROGRESS);
				tlExmnRslt.get().setSttsCd(ExmnSttsCd.INVEST_PROGRESS);
				tmExmnMngRepository.save(invstInfo.get());
				tlExmnRsltRepository.save(tlExmnRslt.get());
			}
		}catch (Exception e) {
			throw new CommonException(ErrorCode.ENTITY_SAVE_FAILED);
		}

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
	  * @Method 설명 : 설문 답변 등록
	  * @param tlSrvyAnsSaveDTO
	  * @param tlSrvyRsltPollsterInfoSaveDTO
	  */
	@Transactional
	public void saveInvstQuestion(TlSrvyAnsSaveDTO tlSrvyAnsSaveDTO, TlSrvyRsltPollsterInfoSaveDTO tlSrvyRsltPollsterInfoSaveDTO) {
		Optional<TmExmnMng> invstInfo = tmExmnMngRepository.findById(LoginExmnUtils.getExmnmngId());
    	//조사 테이블
		if(!invstInfo.isPresent()) {
			throw new CommonException(ErrorCode.NOT_FOUNT_INVST_INFO);
		}
		try {
			if(CommonUtils.isNull(tlSrvyAnsSaveDTO.getTlSrvyRslt())) {
				throw new CommonException(ErrorCode.NOT_FOUNT_INVST_INFO);
			}
			BeanUtils.copyProperties(tlSrvyRsltPollsterInfoSaveDTO,tlSrvyAnsSaveDTO.getTlSrvyRslt());
			tlSrvyRsltRepository.save(tlSrvyAnsSaveDTO.getTlSrvyRslt());
			
			if(CommonUtils.isListNull(tlSrvyAnsSaveDTO.getTlSrvyAnsList())) {
				throw new CommonException(ErrorCode.NOT_FOUNT_INVST_INFO);
			}
			tlSrvyAnsRepository.saveAll(tlSrvyAnsSaveDTO.getTlSrvyAnsList());
			
			//조사 상태 값 진행중으로 변경 
			if("notYetProgress".equals(invstInfo.get().getSttsCd().getStatus())) {
				invstInfo.get().setSttsCd(ExmnSttsCd.INVEST_PROGRESS);
				tmExmnMngRepository.save(invstInfo.get());
				
				//조사 결과 테이블 
				Optional<TlExmnRslt> tlExmnRslt = tlExmnRsltRepository.findById(tlSrvyAnsSaveDTO.getTlSrvyRslt().getExmnrsltId());
				if(!tlExmnRslt.isPresent()) {
					throw new CommonException(ErrorCode.NOT_FOUNT_INVST_INFO);
				}
				tlExmnRslt.get().setSttsCd(ExmnSttsCd.INVEST_PROGRESS);
				tlExmnRsltRepository.save(tlExmnRslt.get());
			}

		}catch (Exception e) {
			throw new CommonException(ErrorCode.ENTITY_SAVE_FAILED);
		}
		
	}

	/**
	  * @Method Name : saveInvstCounting
	  * @작성일 : 2024. 5. 14.
	  * @작성자 : SM.KIM
	  * @Method 설명 : 교통 조사 등록
	  * @param tlTrfvlInfoList
	  */
	@Transactional
	public void saveInvstCounting(List<TlTrfvlInfo> tlTrfvlInfoList) {
		try {
			tlTrfvlInfoRepository.saveAll(tlTrfvlInfoList);
		}catch (Exception e) {
			throw new CommonException(ErrorCode.ENTITY_SAVE_FAILED);
		}
	}
	
}
