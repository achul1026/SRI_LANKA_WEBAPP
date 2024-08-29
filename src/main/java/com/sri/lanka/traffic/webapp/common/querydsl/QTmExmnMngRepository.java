package com.sri.lanka.traffic.webapp.common.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sri.lanka.traffic.webapp.common.dto.common.LoginExmnDTO;
import com.sri.lanka.traffic.webapp.common.dto.invst.TmExmnMngDTO;
import com.sri.lanka.traffic.webapp.common.entity.*;
import com.sri.lanka.traffic.webapp.common.enums.code.ExmnSttsCd;
import com.sri.lanka.traffic.webapp.common.enums.code.ExmnTypeCd;
import com.sri.lanka.traffic.webapp.common.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
		LocalDateTime startOfToday = CommonUtils.getStartOfDay(LocalDate.now());
		LocalDateTime endDt =  CommonUtils.getStartOfDay(LocalDate.now());

		TmExmnMng tmExmnMngInfo = queryFactory.selectFrom(tmExmnMng).where(tmExmnMng.partcptCd.eq(partcptCd)).fetchOne();
		if(CommonUtils.isNull(tmExmnMngInfo)) throw new UsernameNotFoundException("Please check the participation code information and the survey schedule.");

		if("traffic".equals(tmExmnMngInfo.getExmnType().getType())) {
			startOfToday = LocalDateTime.now().plusMinutes(10);
			endDt = LocalDateTime.now().minusMinutes(1);
		}

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
																	tmExmnMng.exmnRange,
																	tmExmnMng.registId,
																	tmExmnMng.startDt,
																	tmExmnMng.endDt)
																	)
											.from(tmExmnMng)
											.where(tmExmnMng.exmnmngId.eq(tmExmnMngInfo.getExmnmngId())
											.and(queryFactory.select(tmExmnPollster.pollsterId.countDistinct()).from(tmExmnPollster).where(tmExmnPollster.exmnmngId.eq(tmExmnMng.exmnmngId)).gt(0L))
													//.and(queryFactory.select(tmExmnPollster.pollsterId.countDistinct()).from(tmExmnPollster).where(tmExmnPollster.exmnmngId.eq(tmExmnMng.exmnmngId)).eq(tmExmnMng.exmnNop.longValue()))
											.and(tmExmnMng.startDt.loe(startOfToday).and(tmExmnMng.endDt.goe(endDt)))
											.and(compareExmnTime(tmExmnMng.startDt,tmExmnMng.endDt,tmExmnMngInfo.getExmnType()))
											.and(tmExmnMng.sttsCd.notIn(ExmnSttsCd.INVEST_WRITING,ExmnSttsCd.INVEST_COMPLETE)))
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
	public TmExmnMng getInvstInfoByExmnmngId(String exmnmngId,ExmnTypeCd exmnTypeCd) {

		LocalDateTime startOfToday = CommonUtils.getStartOfDay(LocalDate.now());
		LocalDateTime endDt =  CommonUtils.getStartOfDay(LocalDate.now());

		if("traffic".equals(exmnTypeCd.getType())) {
			startOfToday = LocalDateTime.now().plusMinutes(10);
			endDt = LocalDateTime.now().minusMinutes(1);
		}

		TmExmnMng result = queryFactory.selectFrom(tmExmnMng)
										.where(tmExmnMng.exmnmngId.eq(exmnmngId)
												.and(queryFactory.select(tmExmnPollster.pollsterId.countDistinct()).from(tmExmnPollster).where(tmExmnPollster.exmnmngId.eq(tmExmnMng.exmnmngId)).gt(0L))
//												.and(queryFactory.select(tmExmnPollster.pollsterId.countDistinct()).from(tmExmnPollster).where(tmExmnPollster.exmnmngId.eq(tmExmnMng.exmnmngId)).eq(tmExmnMng.exmnNop.longValue()))
												.and(tmExmnMng.startDt.loe(startOfToday).and(tmExmnMng.endDt.goe(endDt)))
												.and(compareExmnTime(tmExmnMng.startDt,tmExmnMng.endDt,exmnTypeCd))
												.and(tmExmnMng.sttsCd.notIn(ExmnSttsCd.INVEST_WRITING,ExmnSttsCd.INVEST_COMPLETE)))
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
																		QRepositorySupport.getCodeInfoNamePath(tcCdInfo).as("mngrDept"), 
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
	 * @param startDt
	 * @param endDt
	 * @param exmnType
	 * @return
	 * @Method Name : compareExmnTime
	 * @작성일 : 2024. 4. 15.
	 * @작성자 : NK.KIM
	 * @Method 설명 : 조사 시간 체크
	 */
	private BooleanExpression compareExmnTime(DateTimePath<LocalDateTime> startDt, DateTimePath<LocalDateTime> endDt, ExmnTypeCd exmnType) {

		BooleanExpression result = null;

		if(exmnType.equals(ExmnTypeCd.TM) || exmnType.equals(ExmnTypeCd.MCC)){
			// DateTimeFormatter 준비
			DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
			
			// 현재 시간 포맷
			String currentTimeFormat = LocalTime.now().format(timeFormat);
			// 시작 10분 전 로그인 가능
			String startTimeFormat = LocalTime.now().plusMinutes(10).format(timeFormat);
			if (!CommonUtils.isNull(startDt)) {
				StringTemplate exmnStarTimeFormat = Expressions.stringTemplate(
						"TO_CHAR({0}, 'HH24:MI')",
						startDt
				);
				//tmExmnMng.exmnType.eq(ExmnTypeCd.TM).isTrue().
				BooleanExpression startExpression = exmnStarTimeFormat.loe(startTimeFormat);
				result = (result != null) ? result.and(startExpression) : startExpression ;
			}

			if (!CommonUtils.isNull(endDt)) {
				StringTemplate exmnEndTimeFormat = Expressions.stringTemplate(
						"TO_CHAR({0}, 'HH24:MI')",
						endDt
				);
				BooleanExpression endExpression = exmnEndTimeFormat.goe(currentTimeFormat);
				result = (result != null) ? result.and(endExpression) : endExpression;
			}
		}
		return result;
	}


}
