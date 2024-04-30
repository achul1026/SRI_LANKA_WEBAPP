package com.sri.lanka.traffic.webapp.common.querydsl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sri.lanka.traffic.webapp.common.entity.QTlBbsInfo;
import com.sri.lanka.traffic.webapp.common.entity.TlBbsInfo;
import com.sri.lanka.traffic.webapp.common.util.CommonUtils;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QTlBbsInfoRepository {

	private final JPAQueryFactory queryFactory;

	private QTlBbsInfo tlBbsInfo = QTlBbsInfo.tlBbsInfo;

	/**
	  * @Method Name : getTlBbsInfoByUseYnIsYOrderByCrtDtDesc
	  * @작성일 : 2024. 3. 25.
	  * @작성자 : SM.KIM
	  * @Method 설명 : 갯수 제한있는 공지사항 목록 조회
	  * @return
	  */
	public List<TlBbsInfo> getTlBbsInfoList(Integer limit) {
		 JPAQuery<TlBbsInfo> result = queryFactory.select(Projections.bean(
															TlBbsInfo.class, 
															tlBbsInfo.bbsId, 
															tlBbsInfo.bbsTitle, 
															tlBbsInfo.registDt))
				.from(tlBbsInfo)
				.where(tlBbsInfo.dspyYn.eq("Y"))
				.orderBy(tlBbsInfo.registDt.desc());
				
		 		if (!CommonUtils.isNull(limit)) {
					result.limit(limit);
				}
		 
		return result.fetch();
	}
	
	/**
	  * @Method Name : getTlBbsInfoByUseYnIsYOrderByCrtDtDesc
	  * @작성일 : 2024. 3. 25.
	  * @작성자 : SM.KIM
	  * @Method 설명 : 갯수 제한 없는 공지사항 목록 조회
	  * @return
	  */
	public List<TlBbsInfo> getTlBbsInfoList() {
        return getTlBbsInfoList(null);
    }
	
//	/**
//	  * @Method Name : getTlBbsInfo
//	  * @작성일 : 2024. 3. 25.
//	  * @작성자 : SM.KIM
//	  * @Method 설명 : 공지사항 상세 조회
//	  * @param bbsId
//	  * @return
//	  */
//	public TlBbsInfo getTlBbsInfo(String bbsId) {
//		TlBbsInfo result = queryFactory.select(Projections.bean(
//													TlBbsInfo.class,
//													tlBbsInfo.bbsTitle, 
//													tlBbsInfo.bbsCnts, 
//													tlBbsInfo.registId, 
//													tlBbsInfo.registDt))
//				.from(tlBbsInfo)
//				.where(tlBbsInfo.bbsId.eq(bbsId))
//				.fetchOne();
//		
//		return result;
//	}

}
