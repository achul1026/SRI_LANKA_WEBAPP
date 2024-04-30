package com.sri.lanka.traffic.webapp.common.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sri.lanka.traffic.webapp.common.entity.TlExmnRslt;

public interface TlExmnRsltRepository extends JpaRepository<TlExmnRslt, String>{
	
	
	
	/**
	  * @Method Name : findByExmnmngId
	  * @작성일 : 2024. 4. 11.
	  * @작성자 : NK.KIM
	  * @Method 설명 : 조가 결과 정보 조회
	  * @param exmnmngId
	  * @return
	  */
	TlExmnRslt findByExmnmngId(String exmnmngId);
	
	
	/**
	  * @Method Name : getScheduleStatistics
	  * @작성일 : 2024. 4. 15.
	  * @작성자 : NK.KIM
	  * @Method 설명 : 데이터가 존재하는 시간 가져오기(메인화면)
	  * @param exmnmngId
	  * @return
	  */
	@Query(value =
			"SELECT " +
					"DATA_RESULT.DATA_HOUR as \"dataHour\" , " +
					"DATA_RESULT.LCCHG_RSN as \"lcchgRsn\" " +
			"FROM (" +
					"SELECT " +
						"CASE WHEN TTR.LCCHG_RSN IS NOT NULL OR TTI.TRFVLMRSLT_ID IS NULL THEN CONCAT(TO_CHAR(TTR.REGIST_DT ,'HH24'),':00') " +
						"ELSE CONCAT(TO_CHAR(TTI.FTNMINUNIT_TIME,'HH24'),':00') " +
						"END AS DATA_HOUR, " +
						"CASE WHEN TTR.LCCHG_RSN IS NULL AND TTI.TRFVLMRSLT_ID IS NULL THEN \'NO_DATA\' " +
						"WHEN TTI.FTNMINUNIT_TIME IS NOT NULL THEN NULL " +
						"ELSE TTR.LCCHG_RSN END AS LCCHG_RSN " +
					"FROM srlk.TM_EXMN_MNG TEM " +
					"INNER JOIN srlk.TL_EXMN_RSLT TER ON TEM.EXMNMNG_ID = TER.EXMNMNG_ID " +
					"LEFT JOIN srlk.TL_TRFVL_RSLT TTR ON TER.EXMNRSLT_ID = TTR.EXMNRSLT_ID " +
					"LEFT JOIN srlk.TL_TRFVL_INFO TTI ON TTR.TRFVLMEXMN_ID = TTI.TRFVLMEXMN_ID " +
					"WHERE TEM.EXMNMNG_ID = :exmnmngId " +
					"AND DATE_TRUNC('DAY', TTR.REGIST_DT) = :paramDate " +
					"GROUP BY TER.EXMNRSLT_ID, TO_CHAR(TTI.FTNMINUNIT_TIME,'HH24'), TO_CHAR(TTR.REGIST_DT ,'HH24'), TTR.LCCHG_RSN, TTI.TRFVLMRSLT_ID " +
			") DATA_RESULT " +
			"GROUP BY DATA_RESULT.DATA_HOUR, DATA_RESULT.LCCHG_RSN " +
			"ORDER BY DATA_RESULT.DATA_HOUR ASC ",
			nativeQuery = true)
	List<Map<String,Object>> getTimeListForMain(@Param("exmnmngId") String exmnmngId,@Param("paramDate") LocalDate paramDate);
	
	/**
	 * @Method Name : getScheduleStatistics
	 * @작성일 : 2024. 4. 15.
	 * @작성자 : NK.KIM
	 * @Method 설명 : 데이터가 존재하는 시간 가져오기(이력화면)
	 * @param exmnmngId
	 * @return
	 */
	@Query(value =
			"SELECT " +
					"DATA_RESULT.DATA_HOUR as \"dataHour\" , " +
					"DATA_RESULT.LCCHG_RSN as \"lcchgRsn\" " +
			"FROM (" +
					"SELECT " +
						"CASE WHEN TTR.LCCHG_RSN IS NOT NULL OR TTI.TRFVLMRSLT_ID IS NULL THEN CONCAT(TO_CHAR(TTR.REGIST_DT ,'HH24'),':00') " +
						"ELSE CONCAT(TO_CHAR(TTI.FTNMINUNIT_TIME,'HH24'),':00') " +
						"END AS DATA_HOUR, " +
						"CASE WHEN TTR.LCCHG_RSN IS NULL AND TTI.TRFVLMRSLT_ID IS NULL THEN \'NO_DATA\' " +
						"WHEN TTI.FTNMINUNIT_TIME IS NOT NULL THEN NULL " +
						"ELSE TTR.LCCHG_RSN END AS LCCHG_RSN " +
					"FROM srlk.TM_EXMN_MNG TEM " +
					"INNER JOIN srlk.TL_EXMN_RSLT TER ON TEM.EXMNMNG_ID = TER.EXMNMNG_ID " +
					"LEFT JOIN srlk.TL_TRFVL_RSLT TTR ON TER.EXMNRSLT_ID = TTR.EXMNRSLT_ID " +
					"LEFT JOIN srlk.TL_TRFVL_INFO TTI ON TTR.TRFVLMEXMN_ID = TTI.TRFVLMEXMN_ID " +
					"WHERE TEM.EXMNMNG_ID = :exmnmngId " +
					"AND DATE_TRUNC('DAY', TTR.REGIST_DT) = :paramDate " +
					"AND TTR.STARTLC_NM = :startlcNm " +
					"AND TTR.ENDLC_NM = :endlcNm " +
					"GROUP BY TER.EXMNRSLT_ID, TO_CHAR(TTI.FTNMINUNIT_TIME,'HH24'), TO_CHAR(TTR.REGIST_DT ,'HH24'), TTR.LCCHG_RSN, TTI.TRFVLMRSLT_ID " +
			") DATA_RESULT " +
			"GROUP BY DATA_RESULT.DATA_HOUR, DATA_RESULT.LCCHG_RSN " +
			"ORDER BY DATA_RESULT.DATA_HOUR ASC ",
					nativeQuery = true)
	List<Map<String,Object>> getTimeListForHistory(@Param("exmnmngId") String exmnmngId,@Param("paramDate") LocalDate paramDate
													,@Param("startlcNm") String startlcNm,@Param("endlcNm") String endlcNm);
	
}
