package com.sri.lanka.traffic.webapp.common.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sri.lanka.traffic.webapp.common.entity.TlSrvyRslt;

public interface TlSrvyRsltRepository extends JpaRepository<TlSrvyRslt, String>{
	
	
	/**
	  * @Method Name : findOneByExmnrsltIdAndExmnstartDtAndExmnendDt
	  * @작성일 : 2024. 5. 8.
	  * @작성자 : NK.KIM
	  * @Method 설명 : 당일 조사 결과 테이블 조회 
	  * @param exmnrsltId
	  * @param exmnstartDt
	  * @param exmnendDt
	  * @return
	  */
	Optional<TlSrvyRslt> findOneByExmnrsltIdAndExmnstartDtAndExmnendDt(String exmnrsltId,LocalDateTime exmnstartDt, LocalDateTime exmnendDt);
}
