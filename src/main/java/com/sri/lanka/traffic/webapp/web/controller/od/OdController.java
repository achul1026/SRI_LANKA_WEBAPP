package com.sri.lanka.traffic.webapp.web.controller.od;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import groovyjarjarpicocli.CommandLine.Model;

@Controller
@RequestMapping("/od")
public class OdController {

	/**
	 * @Method Name : od
	 * @작성일 : 2024. 2. 26
	 * @작성자 : NK.KIM
	 * @Method 설명 : odMainPage
	 * @param model
	 * @return
	 */

	@GetMapping()
	public String od(Model model) {
		return "views/od/od";
	}

	/**
	 * @Method Name : odSurvey
	 * @작성일 : 2024. 3. 4
	 * @작성자 : NK.KIM
	 * @Method 설명 : odSurveyPage
	 * @param model
	 * @return
	 */

	@GetMapping("/survey")
	public String odSurvey(Model model) {
		return "views/od/odSurvey";
	}

	/**
	 * @Method Name : odHistory
	 * @작성일 : 2024. 2. 16
	 * @작성자 : NK.KIM
	 * @Method 설명 : OD조사 이력
	 * @param model
	 * @return
	 */
	@GetMapping("/history")
	public String odHistory(Model model) {
		return "views/od/odHistory";
	}
}
