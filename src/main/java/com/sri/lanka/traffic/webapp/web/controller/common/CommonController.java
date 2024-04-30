package com.sri.lanka.traffic.webapp.web.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import groovyjarjarpicocli.CommandLine.Model;

@Controller
@RequestMapping("common")
public class CommonController {

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
}
