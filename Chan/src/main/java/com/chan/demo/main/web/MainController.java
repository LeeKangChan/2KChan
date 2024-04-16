package com.chan.demo.main.web;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class MainController {

	
	@RequestMapping(value="/")
	public String Index(HttpServletRequest request, ModelMap model) {
			
			//LoginVO lVo = (LoginVO) request.getSession().getAttribute("Login");
			
//			String userChk = "N";
			
//			if(lVo != null) {
//				userChk = "Y";
//			}
			
//			model.addAttribute("userChk", userChk);
			return "index";
		}
	
	@RequestMapping(value="/main")
	public String Main(HttpServletRequest request, ModelMap model) {
			
//		LoginVO lVo = (LoginVO) request.getSession().getAttribute("Login");
			
			String userChk = "N";
			
//			if(lVo != null) {
//				userChk = "Y";
//			}
			
			model.addAttribute("userChk", userChk);
			return "main/main";
		}
}
