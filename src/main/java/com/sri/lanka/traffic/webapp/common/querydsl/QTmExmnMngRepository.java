package com.sri.lanka.traffic.webapp.common.querydsl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sri.lanka.traffic.webapp.common.dto.common.LoginExmnDTO;
import com.sri.lanka.traffic.webapp.common.dto.invst.TmExmnMngDTO;
import com.sri.lanka.traffic.webapp.common.entity.QTcCdInfo;
import com.sri.lanka.traffic.webapp.common.entity.QTcUserMng;
import com.sri.lanka.traffic.webapp.common.entity.QTmExmnMng;
import com.sri.lanka.traffic.webapp.common.entity.QTmExmnPollster;
import com.sri.lanka.traffic.webapp.common.entity.TmExmnMng;
import com.sri.lanka.traffic.webapp.common.enums.code.ExmnSttsCd;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QTmExmnMngRepository {

	private final JPAQueryFactory queryFactory;

	private QTmExmnMng tmExmnMng 	= QTmExmnMng.tmExmnMng;
	private QTcCdInfo tcCdInfo 		= QTcCdInfo.tcCdInfo;
	private QTcUserMng tcUserMng 	= QTcUserMng.tcUserMng;
	private QTmExmnPollster tmExmnPollster = QTmExmnPollster.tmExmnPollster; 
	
	
	/**
	 * @Method Name : getInvstInfo
	 * @작성일 : 2024. 2. 5.
	 * @작성자 : NK.KIM
	 * @Method 설명 : 조사 목록 조회
	 * @param exmmngId
	 * @return
	 */
	public LoginExmnDTO getInvstInfoByPartcptCd(String partcptCd) {
		
		LoginExmnDTO result = queryFactory.select(Projections.bean(
																	LoginExmnDTO.class, 
																	tmExmnMng.exmnmngId, 
																	tmExmnMng.exmnNm, 
																	tmExmnMng.srvyId, 
																	tmExmnMng.partcptCd,
																	tmExmnMng.exmnType,
																	tmExmnMng.exmnDiv,
																	tmExmnMng.sttsCd,
																	tmExmnMng.exmnNop,
																	tmExmnMng.exmnLc,
																	tmExmnMng.goalCnt,
																	tmExmnMng.lat,
																	tmExmnMng.lon,
																	tmExmnMng.startDt,
																	tmExmnMng.endDt)
																	)
											.from(tmExmnMng)
											.where(tmExmnMng.partcptCd.eq(partcptCd)
											.and(queryFactory.select(tmExmnPollster.pollsterId.countDistinct()).from(tmExmnPollster).where(tmExmnPollster.exmnmngId.eq(tmExmnMng.exmnmngId)).eq(tmExmnMng.exmnNop.longValue()))
											.and(tmExmnMng.startDt.loe(LocalDateTime.now()).and(tmExmnMng.endDt.goe(LocalDateTime.now())))
											.and(compareExmnTime(tmExmnMng.startDt))
											.and(compareExmnTime(tmExmnMng.endDt))
											.and(tmExmnMng.sttsCd.ne(ExmnSttsCd.INVEST_WRITING)))
											.groupBy(tmExmnMng.exmnmngId)
											.fetchOne();
		
		return result;
	}
	
	/**
	 * @Method Name : getInvstInfo
	 * @작성일 : 2024. 2. 5.
	 * @작성자 : NK.KIM
	 * @Method 설명 : 조사 목록 조회
	 * @param exmmngId
	 * @return
	 */
	public TmExmnMng getInvstInfoByExmnmngId(String exmnmngId) {
		
		TmExmnMng result = queryFactory.selectFrom(tmExmnMng)
										.where(tmExmnMng.exmnmngId.eq(exmnmngId)
												.and(queryFactory.select(tmExmnPollster.pollsterId.countDistinct()).from(tmExmnPollster).where(tmExmnPollster.exmnmngId.eq(tmExmnMng.exmnmngId)).eq(tmExmnMng.exmnNop.longValue()))
												.and(tmExmnMng.startDt.loe(LocalDateTime.now()).and(tmExmnMng.endDt.goe(LocalDateTime.now())))
												.and(compareExmnTime(tmExmnMng.startDt))
												.and(compareExmnTime(tmExmnMng.endDt))
												.and(tmExmnMng.sttsCd.ne(ExmnSttsCd.INVEST_WRITING)))
										.groupBy(tmExmnMng.exmnmngId)
										.fetchOne();
		
		return result;
	}
	
	/**
	 * @Method Name : getInvstScheduleList
	 * @작성일 : 2024. 2. 5.
	 * @작성자 : NK.KIM
	 * @Method 설명 : 조사 스케쥴 목록 조회
	 * @param searchCommonDTO
	 * @param paging
	 * @return
	 */
	public List<TmExmnMngDTO> getInvstScheduleListByNotSttsCd(ExmnSttsCd exmnSttsCd,String exmnmngId) {
		
		List<TmExmnMngDTO> result = queryFactory.select(Projections.bean(
																		TmExmnMngDTO.class, 
																		tmExmnMng.exmnmngId, 
																		tmExmnMng.usermngId,
																		tcCdInfo.cdNm.as("mngrDept"), 
																		tmExmnMng.exmnpicId, 
																		tmExmnMng.exmnNm, 
																		tmExmnMng.exmnType,
																		tmExmnMng.colrCd,
																		tmExmnMng.sttsCd, 
																		tmExmnMng.exmnpicId, 
																		tmExmnMng.startDt, 
																		tmExmnMng.endDt,
																		tmExmnMng.registId,
																		tmExmnMng.exmnLc)
																		)
																		.from(tmExmnMng)
																		.leftJoin(tcUserMng).on(tmExmnMng.usermngId.eq(tcUserMng.usermngId))
																		.leftJoin(tcCdInfo).on(tcUserMng.userBffltd.eq(tcCdInfo.cd))
																		.where(tmExmnMng.sttsCd.ne(exmnSttsCd).and(tmExmnMng.exmnmngId.eq(exmnmngId)))
																		.orderBy(
														                        Expressions.numberTemplate(Integer.class, "age({0}, {1})",
														                                tmExmnMng.startDt, tmExmnMng.endDt).asc(),
														                        tmExmnMng.registDt.desc()
														                )
																		.fetch();
		
		return result;
	}
	
	
	/**
	  * @Method Name : compareExmnTime
	  * @작성일 : 2024. 4. 15.
	  * @작성자 : NK.KIM
	  * @Method 설명 : 조사 시간 체크
	  * @param dateTimePath
	  * @return
	  */
	private BooleanExpression compareExmnTime(DateTimePath<LocalDateTime> dateTimePath) {

		BooleanExpression result = null;
		
		// DateTimeFormatter 준비
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
		// 현재 시간 포맷
		String currentTimeFormat = LocalTime.now().format(timeFormat);
		
		StringTemplate exmnTimeFormat = Expressions.stringTemplate(
			    "TO_CHAR({0}, 'HH24:MI')",
			    dateTimePath
			);
		

		if (dateTimePath == tmExmnMng.startDt) {
			BooleanExpression startExpression = exmnTimeFormat.loe(currentTimeFormat);
			result = (result != null) ? result.and(startExpression) : startExpression;
		}

		if (dateTimePath == tmExmnMng.endDt) {
			BooleanExpression endExpression = exmnTimeFormat.goe(currentTimeFormat);
			result = (result != null) ? result.and(endExpression) : endExpression;
		}
		return result;
	}
}
