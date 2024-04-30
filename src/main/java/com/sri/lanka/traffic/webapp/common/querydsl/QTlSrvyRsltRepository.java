package com.sri.lanka.traffic.webapp.common.querydsl;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sri.lanka.traffic.webapp.common.entity.QTlSrvyRslt;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QTlSrvyRsltRepository {

	private final JPAQueryFactory queryFactory;

	private QTlSrvyRslt tlSrvyRslt = QTlSrvyRslt.tlSrvyRslt;
	
}
