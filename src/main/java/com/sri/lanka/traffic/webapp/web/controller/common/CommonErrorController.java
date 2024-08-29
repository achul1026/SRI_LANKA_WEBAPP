package com.sri.lanka.traffic.webapp.web.controller.common;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonErrorController implements ErrorController {

	/**
	 * @Method Name : errorPage
	 * @작성일 : 2024. 2. 26
	 * @작성자 : NK.KIM
	 * @Method 설명 : errorPage
	 * @param model
	 * @return
	 */

	@GetMapping("/error")
	public String errorPage() {
		return "views/common/errorPage";
	}



}
