package com.sri.lanka.traffic.webapp.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sri.lanka.traffic.webapp.common.entity.TmSrvySect;

public interface TmSrvySectRepository extends JpaRepository<TmSrvySect, String>{
	
	/**
	  * @Method Name : findAllByExmnmngId
	  * @작성일 : 2024. 3. 25.
	  * @작성자 : NK.KIM
	  * @Method 설명 : 설문 부문 목록 조회
	  * @param exmnmngId
	  * @return
	  */
	List<TmSrvySect> findAllBySrvyIdOrderBySectSqnoAsc(String exmnmngId);
}
