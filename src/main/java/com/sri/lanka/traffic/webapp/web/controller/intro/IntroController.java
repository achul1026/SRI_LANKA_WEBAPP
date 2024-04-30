package com.sri.lanka.traffic.webapp.web.controller.intro;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IntroController {

	@GetMapping("/intro")
	public String introPage() {

		return "views/intro/intro";
	}
}
