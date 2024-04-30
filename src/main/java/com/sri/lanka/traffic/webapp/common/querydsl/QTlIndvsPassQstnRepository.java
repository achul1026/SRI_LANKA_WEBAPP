package com.sri.lanka.traffic.webapp.common.querydsl;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sri.lanka.traffic.webapp.common.entity.QTlIndvsPassQstn;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QTlIndvsPassQstnRepository {

	private final JPAQueryFactory queryFactory;

	private QTlIndvsPassQstn tlIndvsPassQstn = QTlIndvsPassQstn.tlIndvsPassQstn;
	
}
