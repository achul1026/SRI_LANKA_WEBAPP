package com.sri.lanka.traffic.webapp.common.querydsl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sri.lanka.traffic.webapp.common.dto.common.LoginExmnDTO;
import com.sri.lanka.traffic.webapp.common.entity.QTcUserMng;
import com.sri.lanka.traffic.webapp.common.entity.QTlBbsInfo;
import com.sri.lanka.traffic.webapp.common.entity.TlBbsInfo;
import com.sri.lanka.traffic.webapp.common.enums.code.BbsTypeCd;
import com.sri.lanka.traffic.webapp.common.util.CommonUtils;
import com.sri.lanka.traffic.webapp.common.util.LoginExmnUtils;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QTlBbsInfoRepository {

	private final JPAQueryFactory queryFactory;

	private QTlBbsInfo tlBbsInfo = QTlBbsInfo.tlBbsInfo;
	
	private QTcUserMng bbsUser = QTcUserMng.tcUserMng;
	
	private QTcUserMng exmnUser = new QTcUserMng("exmnUser");

	/**
	  * @Method Name : getTlBbsInfoList
	  * @작성일 : 2024. 3. 25.
	  * @작성자 : SM.KIM
	  * @Method 설명 : 갯수 제한있는 공지사항 목록 조회
	  * @return
	  */
	public List<TlBbsInfo> getTlBbsInfoList(Integer limit) {
		
		LoginExmnDTO loginUser = LoginExmnUtils.getTmExmnMngInfo();
		
		// 기본 조건
//	    BooleanExpression baseCondition = tlBbsInfo.dspyYn.eq("Y")
//	            .and(tlBbsInfo.bbsType.eq(BbsTypeCd.ALARM.getCode()));
	    
	    // RDA가 아닐 경우 해당 소속 알림만 출력
//	    BooleanBuilder whereClause = new BooleanBuilder(baseCondition);
//	    if(exmnUser.userBffltd.ne("BFC001") != null) whereClause.and((bbsUser.userBffltd.eq(exmnUser.userBffltd)));
		
		JPAQuery<TlBbsInfo> result = queryFactory.select(Projections.bean(
															TlBbsInfo.class, 
															tlBbsInfo.bbsId, 
															tlBbsInfo.bbsTitle, 
															tlBbsInfo.bbsCnts, 
															tlBbsInfo.registDt))
				.from(tlBbsInfo)
				.join(bbsUser).on(bbsUser.userId.eq(tlBbsInfo.registId))
				.join(exmnUser).on(exmnUser.userId.eq(loginUser.getRegistId()))
				.where(tlBbsInfo.dspyYn.eq("Y")
			            .and(tlBbsInfo.bbsType.eq(BbsTypeCd.ALARM.getCode()))
						.and(bbsUser.userBffltd.eq(exmnUser.userBffltd)))
				.orderBy(tlBbsInfo.registDt.desc());
				
		 		if (!CommonUtils.isNull(limit)) {
					result.limit(limit);
				}
		
		return result.fetch();
	}
	
	/**
	  * @Method Name : getTlBbsInfoList
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
