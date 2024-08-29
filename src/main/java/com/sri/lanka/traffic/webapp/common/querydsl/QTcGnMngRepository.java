package com.sri.lanka.traffic.webapp.common.querydsl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sri.lanka.traffic.webapp.common.dto.invst.TcGnMngDTO;
import com.sri.lanka.traffic.webapp.common.entity.QTcGnMng;
import com.sri.lanka.traffic.webapp.common.entity.TcGnMng;
import com.sri.lanka.traffic.webapp.common.entity.TlExmnRslt;
import com.sri.lanka.traffic.webapp.common.util.CommonUtils;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QTcGnMngRepository {

	private final JPAQueryFactory queryFactory;

	private QTcGnMng tcGnMng = QTcGnMng.tcGnMng;


	public List<TcGnMng> getDsdLocationList(){

		List<TcGnMng> result = queryFactory.select(Projections.bean(
																	TcGnMng.class,
																	tcGnMng.gnId,
																	tcGnMng.provinCd,
																	tcGnMng.provinNm,
																	tcGnMng.districtCd,
																	tcGnMng.districtNm,
																	tcGnMng.dsdCd,
																	tcGnMng.dsdNm,
																	tcGnMng.gnCd,
																	tcGnMng.gnNm,
																	tcGnMng.useYn
																	)
													)
										.from(tcGnMng)
										.where(tcGnMng.useYn.eq("Y"))
										.orderBy(tcGnMng.provinCd.asc(),tcGnMng.districtCd.asc(),tcGnMng.dsdCd.asc())
										.fetch();

		return result;
	}


	public TcGnMngDTO getGnLocationList(TlExmnRslt tlExmnRslt){
		//검색 코드 dsd (gn이 없을수 있으므로)
		String searchCode = tlExmnRslt.getDsdCd();
		int repalceNumber = 5;
		if(!CommonUtils.isNull(tlExmnRslt.getGnCd())){
			searchCode = tlExmnRslt.getGnCd();
			repalceNumber = 8;
		}
		StringTemplate stringTemplate = Expressions.stringTemplate("substring({0}, {1}, {2})", tcGnMng.gnId, 1, repalceNumber);

		TcGnMngDTO result = queryFactory.select(Projections.bean(
																	TcGnMngDTO.class,
																	tcGnMng.provinNm,
																	tcGnMng.provinCd,
																	tcGnMng.districtNm,
																	tcGnMng.districtCd,
																	tcGnMng.dsdNm,
																	tcGnMng.dsdCd,
																	Expressions.stringTemplate(
																			"jsonb_agg(" +
																									"jsonb_build_object(" +
																											"'gnNm', {0}, 'gnCd', {1} )" +
																								 ")",
																											tcGnMng.gnNm,tcGnMng.gnCd
																								).as("jsonGnInfoList")
																))
				.from(tcGnMng)
				.where(stringTemplate.eq(searchCode))
				.groupBy(stringTemplate,
						tcGnMng.provinNm,
						tcGnMng.provinCd,
						tcGnMng.districtNm,
						tcGnMng.districtCd,
						tcGnMng.dsdNm,
						tcGnMng.dsdCd)
				.fetchOne();

		return result;
	}
}
