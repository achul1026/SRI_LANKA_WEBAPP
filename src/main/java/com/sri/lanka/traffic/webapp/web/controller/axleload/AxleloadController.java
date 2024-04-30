package com.sri.lanka.traffic.webapp.web.controller.axleload;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import groovyjarjarpicocli.CommandLine.Model;

@Controller
@RequestMapping("/axleload")
public class AxleloadController {

	/**
	 * @Method Name : axleload
	 * @작성일 : 2024. 2. 26
	 * @작성자 : NK.KIM
	 * @Method 설명 : axleloadMainPage
	 * @param model
	 * @return
	 */

	@GetMapping()
	public String axleload(Model model) {
		return "views/axleload/axleload";
	}

	/**
	 * @Method Name : axleloadSurvey
	 * @작성일 : 2024. 2. 26
	 * @작성자 : NK.KIM
	 * @Method 설명 : axleloadSurveyPage
	 * @param model
	 * @return
	 */

	@GetMapping("/survey")
	public String axleloadCounting(Model model) {
		return "views/axleload/axleloadSurvey";
	}

	/**
	 * @Method Name : modalAxleloadSurveyType
	 * @작성일 : 2024. 3. 14
	 * @작성자 : TY.LEE
	 * @Method 설명 : modalAxleloadTypePage
	 * @param model
	 * @return
	 */

	@GetMapping("/type")
	public String axleloadType(Model model) {
		return "views/axleload/modal/axleloadType";
	}

}
