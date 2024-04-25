package com.chan.demo.main.web;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.chan.demo.member.service.Member;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class MainController {

	
	@GetMapping("/")
	public String Index(HttpServletRequest request, ModelMap model) {
		
			return "index";
		}
	
	@GetMapping("/main")
	public String Main(HttpServletRequest request, ModelMap model) {
			
		Member member = (Member) request.getSession().getAttribute("Login");
		
		String userChk = "N";
			
		if(member != null) {
			userChk = "Y";
		}
			
		model.addAttribute("userChk", userChk);
		return "main/main";
	}
}
