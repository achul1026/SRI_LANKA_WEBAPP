package com.sri.lanka.traffic.webapp.common.querydsl;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sri.lanka.traffic.webapp.common.entity.QTmSrvySect;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QTmSrvySectRepository {

	private final JPAQueryFactory queryFactory;

	private QTmSrvySect tmSrvySect = QTmSrvySect.tmSrvySect;
}
