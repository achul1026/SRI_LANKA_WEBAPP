package com.sri.lanka.traffic.webapp.common.querydsl;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sri.lanka.traffic.webapp.common.entity.QTmSrvyQstn;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QTmSrvyQstnRepository {

	private final JPAQueryFactory queryFactory;

	private QTmSrvyQstn tmSrvyQstn = QTmSrvyQstn.tmSrvyQstn;
}
