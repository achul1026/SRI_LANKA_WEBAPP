package com.sri.lanka.traffic.webapp.web.controller.roadside;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import groovyjarjarpicocli.CommandLine.Model;

@Controller
@RequestMapping("/roadside")
public class RoadsideController {

	/**
	 * @Method Name : laborSide
	 * @작성일 : 2024. 2. 26
	 * @작성자 : NK.KIM
	 * @Method 설명 : laborsideMainPage
	 * @param model
	 * @return
	 */

	@GetMapping()
	public String loborside(Model model) {
		return "views/roadside/roadside";
	}

	/**
	 * @Method Name : laborSideSurvey
	 * @작성일 : 2024. 2. 26
	 * @작성자 : NK.KIM
	 * @Method 설명 : laborsideSurveyPage
	 * @param model
	 * @return
	 */

	@GetMapping("/survey")
	public String loborsideSurvey(Model model) {
		return "views/roadside/roadsideSurvey";
	}
}
