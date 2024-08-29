package com.sri.lanka.traffic.webapp.web.controller.map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sri.lanka.traffic.webapp.common.dto.common.LoginExmnDTO;
import com.sri.lanka.traffic.webapp.common.util.LoginUtils;

@Controller
@RequestMapping("/map")
public class MapController {

	/**
	 * @Method Name : map
	 * @작성일 : 2024. 2. 16
	 * @작성자 : NK.KIM
	 * @Method 설명 : 맵 페이지
	 * @param model
	 * @return
	 */

	@GetMapping()
	public String map(Model model) {
		LoginExmnDTO loginExmnDTO = LoginUtils.getInvstInfo();
		model.addAttribute("loginExmnDTO",loginExmnDTO);
		return "views/map/map";
	}
}
