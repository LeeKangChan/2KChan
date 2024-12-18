package com.chan.demo.main.web;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chan.demo.member.entity.Member;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class AdmLayoutController {

	
	@RequestMapping("/adChan/header")
	public String Header(HttpServletRequest request, ModelMap model) {
			
		Member member = (Member) request.getSession().getAttribute("Login");
		
		String userChk = "N";
			
		if(member != null) {
			userChk = "Y";
		}
			
		model.addAttribute("userChk", userChk);
		
		return "adm/inc/header";
	}
	
	@RequestMapping("/adChan/footer")
	public String Footer(HttpServletRequest request, ModelMap model) {
		
		return "adm/inc/footer";
	}
}
