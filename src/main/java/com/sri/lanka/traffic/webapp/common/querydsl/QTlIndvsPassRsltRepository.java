package com.sri.lanka.traffic.webapp.common.querydsl;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sri.lanka.traffic.webapp.common.entity.QTlIndvsPassRslt;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QTlIndvsPassRsltRepository {

	private final JPAQueryFactory queryFactory;

	private QTlIndvsPassRslt tlIndvsPassRslt = QTlIndvsPassRslt.tlIndvsPassRslt;
	
}
