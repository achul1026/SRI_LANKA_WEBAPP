package com.sri.lanka.traffic.webapp.common.querydsl;

import com.querydsl.core.types.Projections;
import com.sri.lanka.traffic.webapp.common.dto.invst.TlExmnRsltDetailSaveDTO;
import com.sri.lanka.traffic.webapp.common.entity.TlTrfvlRslt;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sri.lanka.traffic.webapp.common.entity.QTlTrfvlRslt;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QTlTrfvlRsltRepository {

	private final JPAQueryFactory queryFactory;

	private QTlTrfvlRslt tlTrfvlRslt = QTlTrfvlRslt.tlTrfvlRslt;

	public boolean existsInvstTrfvlInfoByLcchgRsnIsNotNull(TlExmnRsltDetailSaveDTO tlExmnRsltDetailSaveDTO){

		Long count = queryFactory.select(tlTrfvlRslt.countDistinct())
										.from(tlTrfvlRslt)
										.where(tlTrfvlRslt.exmnrsltId.eq(tlExmnRsltDetailSaveDTO.getExmnrsltId())
												.and(tlTrfvlRslt.exmnstartDt.eq(tlExmnRsltDetailSaveDTO.getExmnstartDt()))
												.and(tlTrfvlRslt.startlcNm.eq(tlExmnRsltDetailSaveDTO.getStartlcNm()))
												.and(tlTrfvlRslt.endlcNm.eq(tlExmnRsltDetailSaveDTO.getEndlcNm()))
												.and(tlTrfvlRslt.lcchgRsn.isNotNull()))
										.fetchOne();

		return count == 0 ? false : true;
	}
}
