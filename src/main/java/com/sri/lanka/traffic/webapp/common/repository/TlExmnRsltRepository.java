package com.sri.lanka.traffic.webapp.common.repository;

import com.sri.lanka.traffic.webapp.common.entity.TlExmnRslt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

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
	  * @Method 설명 : 설문 데이터가 존재하는 시간 가져오기(메인화면)
	  * @param exmnmngId
	  * @return
	  */
	@Query(value =
			"SELECT "+
					"(ROW_NUMBER() OVER(order by DATA_RESULT.REGIST_DT asc) ) AS \"rownm\" , "+
					"TO_CHAR(DATA_RESULT.REGIST_DT,'yyyy-MM-dd') AS \"registDt\", "+
					"TO_CHAR(DATA_RESULT.REGIST_DT,'HH24:mi') AS \"registTime\", "+
					"DATA_RESULT.POLLSTER_NM AS \"pollsterNm\", "+
					"(CASE WHEN MAX(DATA_RESULT.ANS_CNTS) IS NULL THEN '-' ELSE MAX(DATA_RESULT.ANS_CNTS) END ) AS \"ansCnts\" "+
			"from ( "+
					"select "+
						"TSR.SRVYRSLT_ID, "+
						"TSR.REGIST_DT , "+
						"TSR.POLLSTER_NM , "+
						"(case when tsa.SRVY_METADATA_CD = :srvyMetadataCd then TSA.ANS_CNTS else null end ) AS  ANS_CNTS "+
					"from "+
						"srlk.TL_EXMN_RSLT TER "+
					"left join "+
						"srlk.TL_SRVY_RSLT TSR "+
						"on TER.EXMNRSLT_ID  = TSR.EXMNRSLT_ID "+
					"left join "+
						"srlk.TL_SRVY_ANS TSA "+
						"on TSR.SRVYRSLT_ID = TSA.SRVYRSLT_ID "+
					"where "+
						"TER.EXMNMNG_ID = :exmnmngId "+
					"AND TSA.SRVYANS_ID is not null "+
					"AND TO_CHAR(TSR.REGIST_DT , 'yyyy-MM-dd') = TO_CHAR(NOW() , 'yyyy-MM-dd') "+
					"AND TER.EXMN_TYPE = :exmnType "+
					"group by "+
						"TSR.SRVYRSLT_ID, "+
						"TSR.REGIST_DT , "+
						"TSR.POLLSTER_NM , "+
						"TSA.SRVY_METADATA_CD,TSA.ANS_CNTS "+
					"order by "+
						"TSR.REGIST_DT desc "+
			")DATA_RESULT "+
					" WHERE (:pollsterNm is null OR DATA_RESULT.POLLSTER_NM = cast(:pollsterNm AS TEXT)) "+
					"group by DATA_RESULT.SRVYRSLT_ID,DATA_RESULT.REGIST_DT,DATA_RESULT.POLLSTER_NM "+
					"order by DATA_RESULT.REGIST_DT desc ",
			nativeQuery = true)
	List<Map<String,Object>> getSrvyRsltListForMain(@Param("exmnmngId") String exmnmngId, @Param("exmnType") String exmnType
													, @Param("srvyMetadataCd") String srvyMetadataCd, @Param("pollsterNm") String pollsterNm);


	/**
	  * @Method Name : getTrfvlRsltListForMain
	  * @작성일 : 2024. 7. 17.
	  * @작성자 : SM.KIM
	  * @Method 설명 : 교통량 데이터가 존재하는 시간 가져오기(메인화면)
	  * @param exmnmngId
	  * @param code
	  * @return
	  */
	@Query(value = 
			 "SELECT "
		        + "TO_CHAR(result_data.regist_dt,'yyyy-MM-dd') AS \"registDt\", "
		        + "TO_CHAR(result_data.regist_dt,'HH24:mi') AS \"registTime\", "
		        + "result_data.lcchg_rsn AS \"lcchgRsn\", "
		        + "result_data.startlc_nm AS \"startlcNm\", "
		        + "result_data.endlc_nm AS \"endlcNm\" "
		        + "FROM ("
		        + "    SELECT "
		        + "    ttr.regist_dt, "
		        + "    COALESCE(ttr.lcchg_rsn,'-') AS lcchg_rsn, "
		        + "    ttr.startlc_nm, "
		        + "    ttr.endlc_nm "
		        + "    FROM "
		        + "    srlk.tl_exmn_rslt ter "
		        + "    LEFT JOIN srlk.tl_trfvl_rslt ttr ON ter.exmnrslt_id = ttr.exmnrslt_id "
		        + "    LEFT JOIN srlk.tl_trfvl_info tti ON ttr.trfvlmexmn_id = tti.trfvlmexmn_id AND TO_CHAR(tti.ftnminunit_time, 'yyyy-MM-dd') = TO_CHAR(NOW(), 'yyyy-MM-dd') "
		        + "    WHERE "
		        + "    ter.exmnmng_id = :exmnmngId "
		        + "    AND ter.exmn_type = :exmnType "
		        + "    GROUP BY "
		        + "    tti.ftnminunit_time, "
		        + "    ttr.startlc_nm, "
		        + "    ttr.endlc_nm, "
		        + "    ttr.trfvlmexmn_id, "
		        + "    ttr.regist_dt, "
		        + "    ttr.lcchg_rsn "
		        + "    ORDER BY ttr.regist_dt DESC "
		        + ") result_data "
		        +" WHERE (:startlcNm is null OR result_data.STARTLC_NM = cast(:startlcNm AS TEXT)) "
		        +" AND (:endlcNm is null OR result_data.ENDLC_NM = cast(:endlcNm AS TEXT)) "
		        + "LIMIT 5",
			nativeQuery = true)
	List<Map<String, Object>> getTrfvlRsltListForMain(@Param("exmnmngId") String exmnmngId, @Param("exmnType") String exmnType
			, @Param("startlcNm") String startlcNm, @Param("endlcNm") String endlcNm);
}
