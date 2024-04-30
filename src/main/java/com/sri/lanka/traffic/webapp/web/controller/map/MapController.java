package com.sri.lanka.traffic.webapp.web.controller.map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import groovyjarjarpicocli.CommandLine.Model;

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
		return "views/map/map";
	}
}
