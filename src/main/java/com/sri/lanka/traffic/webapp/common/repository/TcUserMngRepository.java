package com.sri.lanka.traffic.webapp.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sri.lanka.traffic.webapp.common.entity.TcUserMng;

public interface TcUserMngRepository extends JpaRepository<TcUserMng, String>{
	
	/**
	  * @Method Name : findOneByUserId
	  * @작성일 : 2023. 12. 27.
	  * @작성자 : NK.KIM
	  * @Method 설명 : 관리자 조회 By userId
	  * @param userId
	  * @return
	  */
	TcUserMng findOneByUserId(String userId);

}
