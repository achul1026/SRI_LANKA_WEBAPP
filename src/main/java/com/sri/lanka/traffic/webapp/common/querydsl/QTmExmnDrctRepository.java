package com.sri.lanka.traffic.webapp.common.querydsl;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sri.lanka.traffic.webapp.common.entity.QTmExmnDrct;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QTmExmnDrctRepository {

	private final JPAQueryFactory queryFactory;
	
	private QTmExmnDrct tmExmnDrct = QTmExmnDrct.tmExmnDrct;

}
