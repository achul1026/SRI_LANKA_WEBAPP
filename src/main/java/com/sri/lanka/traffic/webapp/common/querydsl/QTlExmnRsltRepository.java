package com.sri.lanka.traffic.webapp.common.querydsl;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sri.lanka.traffic.webapp.common.entity.QTlExmnRslt;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QTlExmnRsltRepository {

	private final JPAQueryFactory queryFactory;

	private QTlExmnRslt tlExmnRslt = QTlExmnRslt.tlExmnRslt;
}
