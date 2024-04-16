package com.chan.demo.member.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.RequestToViewNameTranslator;

import com.chan.demo.member.service.LoginVO;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class MemberController {
	
	@PostMapping("/login")
	public String Login(HttpServletRequest request, ModelMap model) {
			
		LoginVO lVo = (LoginVO) request.getSession().getAttribute("Login");
			
		if(lVo != null) {
			model.addAttribute("msg", "이미 로그인 되었습니다");
			model.addAttribute("url", "/");
			
			return "inc/alert";
		}

		return "member/login";
	}

	@PostMapping("/loginChk")
	public String LoginChk(HttpServletRequest request, ModelMap model) {
			
		LoginVO lVo = (LoginVO) request.getSession().getAttribute("Login");
			
		if(lVo != null) {
			model.addAttribute("msg", "이미 로그인 되었습니다");
			model.addAttribute("url", "/");
			
			return "inc/alert";
		} 
		
		String id = "";
		String pwd = "";
		String msg = "";
		
		if(request.getParameter("id") == null || request.getParameter("id").equals("")) {
			msg = "아이디를 입력해 주세요";
		}
		
		if(request.getParameter("pwd") == null || request.getParameter("pwd").equals("")) {
			msg = "비밀번호를 입력해 주세요";
		}
		
		if(!msg.equals("")) {
			model.addAttribute("msg", msg);
			model.addAttribute("url", "history.back()");
		}
		
		id = request.getParameter("id");
		pwd = request.getParameter("pwd");
		
		LoginVO loginVO = new LoginVO();
		
		loginVO.setId(id);
		loginVO.setPwd(pwd);
		

		return "inc/alert";
	}
}
