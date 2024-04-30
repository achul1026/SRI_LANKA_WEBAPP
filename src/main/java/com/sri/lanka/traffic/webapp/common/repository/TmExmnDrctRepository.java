package com.sri.lanka.traffic.webapp.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sri.lanka.traffic.webapp.common.entity.TmExmnDrct;

public interface TmExmnDrctRepository extends JpaRepository<TmExmnDrct, String>{

	/**
	  * @Method Name : findAllByExmnmngId
	  * @작성일 : 2024. 4. 9.
	  * @작성자 : NK.KIM
	  * @Method 설명 : 목록 조회 
	  * @param exmnmngId
	  * @return
	  */
	List<TmExmnDrct> findAllByExmnmngIdOrderByDrctSqnoAsc(String exmnmngId);
}
