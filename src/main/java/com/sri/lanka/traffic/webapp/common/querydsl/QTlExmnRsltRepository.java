package com.sri.lanka.traffic.webapp.common.querydsl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sri.lanka.traffic.webapp.common.entity.QTlExmnRslt;
import com.sri.lanka.traffic.webapp.common.entity.QTlSrvyAns;
import com.sri.lanka.traffic.webapp.common.entity.QTlSrvyRslt;
import com.sri.lanka.traffic.webapp.common.entity.QTlTrfvlInfo;
import com.sri.lanka.traffic.webapp.common.entity.QTlTrfvlRslt;
import com.sri.lanka.traffic.webapp.common.entity.TlTrfvlRslt;
import com.sri.lanka.traffic.webapp.common.enums.code.ExmnTypeCd;
import com.sri.lanka.traffic.webapp.common.util.CommonUtils;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QTlExmnRsltRepository {

	private final JPAQueryFactory queryFactory;

	private QTlExmnRslt tlExmnRslt = QTlExmnRslt.tlExmnRslt;
	private QTlSrvyRslt tlSrvyRslt = QTlSrvyRslt.tlSrvyRslt;
	private QTlSrvyAns tlSrvyAns = QTlSrvyAns.tlSrvyAns;
	private QTlTrfvlRslt tlTrfvlRslt = QTlTrfvlRslt.tlTrfvlRslt;
	private QTlTrfvlInfo tlTrfvlInfo = QTlTrfvlInfo.tlTrfvlInfo;


	public List<String> getSrvyRsltPollsterListForMain(String exmnmngId, ExmnTypeCd exmnType){
		StringTemplate regDate = Expressions.stringTemplate(
				"to_char({0}, {1})",
				tlSrvyRslt.registDt,
				Expressions.constant("yyyy-MM-dd")
		);

		List<String> result = queryFactory.select(tlSrvyRslt.pollsterNm)
											.from(tlExmnRslt)
											.leftJoin(tlSrvyRslt).on(tlExmnRslt.exmnrsltId.eq(tlSrvyRslt.exmnrsltId))
											.leftJoin(tlSrvyAns).on(tlSrvyRslt.srvyrsltId.eq(tlSrvyAns.srvyrsltId))
											.where(tlExmnRslt.exmnmngId.eq(exmnmngId)
											.and(regDate.eq(CommonUtils.formatLocalDateTime(LocalDateTime.now(),"yyyy-MM-dd")))
											.and(tlSrvyAns.isNotNull())
											.and(tlExmnRslt.exmnType.eq(exmnType)))
											.groupBy(tlSrvyRslt.pollsterNm)
											.orderBy(tlSrvyRslt.pollsterNm.asc())
											.fetch();
		return result;
	}


	public List<TlTrfvlRslt> getTrfvlRsltPollsterListForMain(String exmnmngId, ExmnTypeCd exmnType) {
		StringTemplate regDate = Expressions.stringTemplate(
				"to_char({0}, {1})",
				tlTrfvlRslt.registDt,
				Expressions.constant("yyyy-MM-dd")
		);

		List<TlTrfvlRslt> result = queryFactory.select(Projections.bean(TlTrfvlRslt.class,tlTrfvlRslt.startlcNm,tlTrfvlRslt.endlcNm))
											.from(tlExmnRslt)
											.leftJoin(tlTrfvlRslt).on(tlTrfvlRslt.exmnrsltId.eq(tlTrfvlRslt.exmnrsltId))
											.leftJoin(tlTrfvlInfo).on(tlTrfvlRslt.trfvlmexmnId.eq(tlTrfvlInfo.trfvlmexmnId))
											.where(tlExmnRslt.exmnmngId.eq(exmnmngId)
											.and(regDate.eq(CommonUtils.formatLocalDateTime(LocalDateTime.now(),"yyyy-MM-dd")))
											.and(tlTrfvlInfo.isNotNull())
											.and(tlExmnRslt.exmnType.eq(exmnType)))
											.groupBy(tlTrfvlRslt.startlcNm)
											.groupBy(tlTrfvlRslt.endlcNm)
											.orderBy(tlTrfvlRslt.startlcNm.asc())
											.fetch();
		return result;
	}
}
