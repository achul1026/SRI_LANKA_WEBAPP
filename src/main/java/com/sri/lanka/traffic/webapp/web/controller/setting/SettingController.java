package com.sri.lanka.traffic.webapp.web.controller.setting;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import groovyjarjarpicocli.CommandLine.Model;

@Controller
@RequestMapping("/setting")
public class SettingController {

	/**
	 * @Method Name : setting
	 * @작성일 : 2024. 2. 20
	 * @작성자 : NK.KIM
	 * @Method 설명 : settingPage
	 * @param model
	 * @return
	 */

	@GetMapping()
	public String setting(Model model) {
		return "views/setting/setting";
	}

	/**
	 * @Method Name : settingReportInquiry
	 * @작성일 : 2024. 2. 20
	 * @작성자 : NK.KIM
	 * @Method 설명 : settingReportInquiryPage
	 * @param model
	 * @return
	 */

	@GetMapping("/report/inquiry")
	public String settingReportInquiry(Model model) {
		return "views/setting/reportInquiry";
	}

}
