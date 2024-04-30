package com.sri.lanka.traffic.webapp.common.querydsl;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sri.lanka.traffic.webapp.common.entity.QTmSrvyAns;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QTmSrvyAnsRepository {

	private final JPAQueryFactory queryFactory;
	
	private QTmSrvyAns tmSrvyAns = QTmSrvyAns.tmSrvyAns;

}
