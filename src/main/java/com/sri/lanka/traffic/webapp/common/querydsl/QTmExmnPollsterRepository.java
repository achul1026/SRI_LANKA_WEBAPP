package com.sri.lanka.traffic.webapp.common.querydsl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sri.lanka.traffic.webapp.common.dto.invst.TmExmnPollsterDTO;
import com.sri.lanka.traffic.webapp.common.entity.QTmExmnPollster;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QTmExmnPollsterRepository {

	private final JPAQueryFactory queryFactory;

	private QTmExmnPollster tmExmnPollster = QTmExmnPollster.tmExmnPollster;
	
	/**
	  * @Method Name : getPollsterInfo
	  * @작성일 : 2024. 4. 9.
	  * @작성자 : SM.KIM
	  * @Method 설명 : 조사원 정보 조회
	  * @param exmnmngId
	  * @return
	  */
	public List<TmExmnPollsterDTO> getPollsterInfo(String exmnmngId) {
		List<TmExmnPollsterDTO> result = queryFactory.select(Projections.bean(
																	TmExmnPollsterDTO.class,
																	tmExmnPollster.pollsterNm, tmExmnPollster.pollsterEmail, tmExmnPollster.pollsterTel,
																	tmExmnPollster.pollsterType)
																	)
								.from(tmExmnPollster)
								.where(tmExmnPollster.exmnmngId.eq(exmnmngId))
								.orderBy(tmExmnPollster.pollsterType.desc(),tmExmnPollster.registDt.desc())
								.fetch();
		return result;
	}
}
