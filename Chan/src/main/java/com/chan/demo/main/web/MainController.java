package com.chan.demo.main.web;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class MainController {

	
	@RequestMapping("/")
	public String Index(HttpServletRequest request, ModelMap model) {
		
			return "index";
		}
	
	@RequestMapping("/main")
	public String Main(HttpServletRequest request, ModelMap model) {
		
		
		return "main/main";
	}
}
