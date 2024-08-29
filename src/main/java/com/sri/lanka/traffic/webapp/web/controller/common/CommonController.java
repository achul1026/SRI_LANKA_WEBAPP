package com.sri.lanka.traffic.webapp.web.controller.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sri.lanka.traffic.webapp.common.entity.TcGnMng;
import com.sri.lanka.traffic.webapp.common.querydsl.QTcGnMngRepository;
import com.sri.lanka.traffic.webapp.support.exception.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import groovyjarjarpicocli.CommandLine.Model;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("common")
public class CommonController {

	@Autowired
	QTcGnMngRepository qTcGnMngRepository;


	/**
	 * @Method Name : modalLoactionSearch
	 * @작성일 : 2024. 2. 26
	 * @작성자 : NK.KIM
	 * @Method 설명 : modalLoactionSearchPage
	 * @param model
	 * @return
	 */

	@GetMapping("/loaction")
	public String loborsideSurvey(Model model) {
		return "views/common/modal/loactionSearch";
	}

	/**
	 * @Method Name : dsdLocationInfo
	 * @작성일 : 2024. 5. 21.
	 * @작성자 : NK.KIM
	 * @Method 설명 : DSD 위치 정보
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
	@GetMapping("/dstrct/location")
	public @ResponseBody CommonResponse<?> dstrctLocationInfo(org.springframework.ui.Model model) throws JsonProcessingException {
		List<TcGnMng> tcDsdMngList = qTcGnMngRepository.getDsdLocationList();
		Map<String, Object> result = new HashMap<String, Object>();
		ObjectMapper objectMapper = new ObjectMapper();
		String tcDsdMngJsonList = objectMapper.writeValueAsString(tcDsdMngList);
		result.put("location", tcDsdMngJsonList);
		return CommonResponse.successToData(result, "");
	}
}
