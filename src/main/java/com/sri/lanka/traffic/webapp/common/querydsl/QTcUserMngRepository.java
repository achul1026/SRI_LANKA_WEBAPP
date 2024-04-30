package com.sri.lanka.traffic.webapp.common.querydsl;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sri.lanka.traffic.webapp.common.dto.common.LoginMngrDTO;
import com.sri.lanka.traffic.webapp.common.entity.QTcUserMng;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QTcUserMngRepository {

	private final JPAQueryFactory queryFactory;

	private QTcUserMng tcUserMng = QTcUserMng.tcUserMng;

	/**
	 * @Method Name : getTcUserInfoByUserId
	 * @작성일 : 2024. 2. 8.
	 * @작성자 : NK.KIM
	 * @Method 설명 : 관리자 정보 조회 by userId
	 * @param userId
	 * @return
	 */
	public LoginMngrDTO getTcUserInfoByUserId(String userId) {
		LoginMngrDTO result = queryFactory.select(Projections.bean(LoginMngrDTO.class, tcUserMng.usermngId, tcUserMng.authId,
				tcUserMng.userId, tcUserMng.userPswd, tcUserMng.userAuth, tcUserMng.userType,
				tcUserMng.athrztStts, tcUserMng.userNm)).from(tcUserMng).where(tcUserMng.userId.eq(userId))
				.fetchOne();
		return result;
	}

}
