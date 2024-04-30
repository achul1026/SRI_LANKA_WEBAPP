package com.sri.lanka.traffic.webapp.web.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/login")
public class LoginController {
	
	/**
	 * @Method Name : loginCode
	 * @작성일 : 2024. 2. 14
	 * @작성자 : NK.KIM
	 * @Method 설명 : 로그인 조사코드 로그인 페이지
	 * @param model
	 * @return
	 */
	@GetMapping
	public String loginSurveyorCode(Model model) {
		return "views/login/loginSurveyorCode";
	}
	
	/**
	 * @Method Name : login
	 * @작성일 : 2024. 2. 14
	 * @작성자 : NK.KIM
	 * @Method 설명 : 로그인페이지
	 * @param model
	 * @return
	 */
	
//	@GetMapping
//	public String login(Model model) {
//		return "views/login/login";
//	}

	/**
	 * @Method Name : loginIdFindInfo
	 * @작성일 : 2024. 2. 14
	 * @작성자 : NK.KIM
	 * @Method 설명 : 로그인 아이디찾기 페이지
	 * @param model
	 * @return
	 */
	@GetMapping("/idfind")
	public String loginIdFindInfo(Model model) {
		return "views/login/loginIdFindInfo";
	}
	
	/**
	 * @Method Name : loginIdFindResult
	 * @작성일 : 2024. 2. 14
	 * @작성자 : NK.KIM
	 * @Method 설명 : 로그인 아이디찾기 결과 페이지
	 * @param model
	 * @return
	 */
	@GetMapping("/idfind/result")
	public String loginIdFindResult(Model model) {
		return "views/login/loginIdFindResult";
	}

	/**
	 * @Method Name : loginPwFindInfo
	 * @작성일 : 2024. 2. 14
	 * @작성자 : NK.KIM
	 * @Method 설명 : 로그인 비밀번호 찾기 페이지
	 * @param model
	 * @return
	 */
	@GetMapping("/pwfind")
	public String loginPwFindInfo(Model model) {
		return "views/login/loginPwFindInfo";
	}

	/**
	 * @Method Name : loginPwFindChange
	 * @작성일 : 2024. 2. 14
	 * @작성자 : NK.KIM
	 * @Method 설명 : 로그인 비밀번호 찾기 페이지
	 * @param model
	 * @return
	 */
	@GetMapping("/pwchange")
	public String loginPwChange(Model model) {
		return "views/login/loginPwChange";
	}

}
