package com.chan.demo.main.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.chan.demo.member.entity.Member;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AdmMainController {
	


	
	@RequestMapping("/adChan/main")
	public String Main(HttpServletRequest request, ModelMap model) {
		
		Member member = (Member) request.getSession().getAttribute("Login");
		String level = "";
		
		// 로그인 체크
		if(member == null) {
			return "redirect:/adChan/login";
		}
		
		
		// 레벨 체크
		level = member.getLevel();
		
		// 1 : 총관리자 / 2 : 부관리자
		if(!level.equals("1") && !level.equals("2")) {
			return "redirect:/";
		}
		
		return "adm/main/main";
	}
}
