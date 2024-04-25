package com.chan.demo.member.web;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chan.demo.member.service.Member;
import com.chan.demo.member.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class MemberController {
	
	@Autowired
    private MemberService memberService;
	
	@GetMapping("/login")
	public String Login(HttpServletRequest request, ModelMap model) {
			
		Member member = (Member) request.getSession().getAttribute("Login");
			
		if(member != null) {
			model.addAttribute("msg", "이미 로그인 되었습니다");
			model.addAttribute("url", "/");
			model.addAttribute("type", "url");
			return "inc/alert";
		}
		
		String url = request.getHeader("Referer");
		 
		if(url == null || url.equals("")) {
			url = "/";
		} else {
			int index = url.indexOf("/", 10);
			
			url = url.substring(index);
		}
		
		model.addAttribute("url", url);

		return "member/login";
	}

	@PostMapping("/loginChk")
	public String LoginChk(HttpServletRequest request, ModelMap model) {
			
		Member member = (Member) request.getSession().getAttribute("Login");
			
		if(member != null) {
			model.addAttribute("msg", "이미 로그인 되었습니다");
			model.addAttribute("url", "/");
			model.addAttribute("type", "url");
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
			model.addAttribute("type", "script");
			return "inc/alert";
		}
		
		id = request.getParameter("id");
		pwd = request.getParameter("pwd");
		
		Member loginMem = new Member();
		
		loginMem.setId(id);
		loginMem.setPwd(pwd);
		
		
		Member chkMember = memberService.loginUser(loginMem);
		
		if(chkMember == null) {
			model.addAttribute("msg", "아이디 또는 비밀번호를 확인해 주세요.");
			model.addAttribute("url", "history.back()");
			model.addAttribute("type", "script");
			return "inc/alert";
		}
		request.getSession().setAttribute("Login", chkMember);
		
		String url = "/";
		
		if(request.getParameter("url") != null && !request.getParameter("url").equals("")) {
			url = request.getParameter("url");
		}
		
		return "redirect:"+url;
	}
	
	@GetMapping("/logout")
	public String Logout(HttpServletRequest request, ModelMap model) {
			
		Member member = (Member) request.getSession().getAttribute("Login");
		
		if(member == null) {
			model.addAttribute("msg", "잘못된 접근");
			model.addAttribute("url", "/");
			model.addAttribute("type", "url");
			return "inc/alert";
		}
		
		HttpSession session = request.getSession();
		session.removeAttribute("Login");
		
		String url = request.getHeader("Referer");
		 
		if(url == null || url.equals("")) {
			url = "/";
		} else {
			int index = url.indexOf("/", 10);
			
			url = url.substring(index);
		}
		
		model.addAttribute("msg", "로그아웃 되었습니다.");
		model.addAttribute("url", url);
		model.addAttribute("type", "url");
		return "inc/alert";
	}
	
	@GetMapping("/join")
	public String Join(HttpServletRequest request, ModelMap model) {
			
		Member member = (Member) request.getSession().getAttribute("Login");
			
		if(member != null) {
			model.addAttribute("msg", "이미 로그인 되었습니다");
			model.addAttribute("url", "/");
			model.addAttribute("type", "url");
			return "inc/alert";
		}

		return "member/join";
	}
	
	@ResponseBody
	@PostMapping("/idChk")
	public String IdChk(HttpServletRequest request, ModelMap model, @RequestParam("id") String id) {
			
		Member member = (Member) request.getSession().getAttribute("Login");
			
		if(member != null) {
			model.addAttribute("msg", "이미 로그인 되었습니다");
			model.addAttribute("url", "/");
			model.addAttribute("type", "url");
			return "inc/alert";
		}
		
		String chk = "N";
		
		if(id == null || id.equals("")) {
			return chk;
		}
		
		int cnt = 0;
		
		cnt = memberService.chkId(id);
		
		if(cnt == 0) {
			chk = "Y";
		}

		return chk;
	}
	
	@PostMapping("/joinChk")
	public String JoinChk(HttpServletRequest request, ModelMap model) {
			
		Member member = (Member) request.getSession().getAttribute("Login");
			
		if(member != null) {
			model.addAttribute("msg", "이미 로그인 되었습니다");
			model.addAttribute("url", "/");
			model.addAttribute("type", "url");
			return "inc/alert";
		} 
		
		String id = "";
		String pwd = "";
		String pwdChk = "";
		String name = "";
		int age = 0;
		String post = "";
		String tel = "";
		String tel1 = "";
		String tel2 = "";
		String tel3 = "";
		String sex = "";
		String birth = "";
		String email = "";
		String email1 = "";
		String email2 = "";
		
		
		String msg = "";
		
		if(request.getParameter("id") == null || request.getParameter("id").equals("")) {
			msg = "아이디를 입력해 주세요";
		}
		
		if(request.getParameter("pwd") == null || request.getParameter("pwd").equals("")) {
			msg = "비밀번호를 입력해 주세요";
		}
		
		if(request.getParameter("pwdChk") == null || request.getParameter("pwdChk").equals("")) {
			msg = "비밀번호확인을 입력해 주세요";
		}
		
		if(request.getParameter("name") == null || request.getParameter("name").equals("")) {
			msg = "이름을 입력해 주세요";
		}
		
		if(request.getParameter("age") == null || request.getParameter("age").equals("")) {
			msg = "나이를 입력해 주세요";
		}
		
		if(request.getParameter("post") == null || request.getParameter("post").equals("")) {
			msg = "주소를 입력해 주세요";
		}
		
		if(request.getParameter("tel1") == null || request.getParameter("tel1").equals("")) {
			msg = "전화번호를 입력해 주세요";
		}
		
		if(request.getParameter("tel2") == null || request.getParameter("tel2").equals("")) {
			msg = "전화번호를 입력해 주세요";
		}
		
		if(request.getParameter("tel3") == null || request.getParameter("tel3").equals("")) {
			msg = "전화번호를 입력해 주세요";
		}
		
		if(request.getParameter("sex") == null || request.getParameter("sex").equals("")) {
			msg = "성별을 선택해 주세요";
		}
		
		if(request.getParameter("birth") == null || request.getParameter("birth").equals("")) {
			msg = "생년월일를 입력해 주세요";
		}
		
		if(request.getParameter("email1") == null || request.getParameter("email1").equals("")) {
			msg = "이메일을 입력해 주세요";
		}
		
		if(request.getParameter("email2") == null || request.getParameter("email2").equals("")) {
			msg = "이메일을 입력해 주세요";
		}
		
		
		if(!msg.equals("")) {
			model.addAttribute("msg", msg);
			model.addAttribute("url", "history.back()");
			model.addAttribute("type", "script");
			return "inc/alert";
		}
		
		id = request.getParameter("id");
		pwd = request.getParameter("pwd");
		pwdChk = request.getParameter("pwdChk");
		name = request.getParameter("name");
		age = Integer.parseInt(request.getParameter("age"));
		post = request.getParameter("post");
		tel1 = request.getParameter("tel1");
		tel2 = request.getParameter("tel2");
		tel3 = request.getParameter("tel3");
		sex = request.getParameter("sex");
		birth = request.getParameter("birth");
		email1 = request.getParameter("email1");
		email2 = request.getParameter("email2");
		
		if(!pwd.equals(pwdChk)) {
			model.addAttribute("msg", "비밀번호가 서로 다릅니다. 다시 확인해 주세요");
			model.addAttribute("url", "history.back()");
			model.addAttribute("type", "script");
			return "inc/alert";
		}
		
		tel = tel1 + "-" + tel2 + "-" + tel3;
		email = email1 + "@" + email2;
		
		LocalDateTime now = LocalDateTime.now();
		
		String today = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		try {
			Member joinMem = new Member();
			
			joinMem.setNum(2);
			joinMem.setId(id);
			joinMem.setPwd(pwd);
			joinMem.setName(name);
			joinMem.setAge(age);
			joinMem.setPost(post);
			joinMem.setTel(tel);
			joinMem.setSex(sex);
			joinMem.setBirth(birth);
			joinMem.setEmail(email);
			joinMem.setLevel("10");
			joinMem.setReg_date(today);
			joinMem.setMod_id("");
			joinMem.setMod_date("");
			joinMem.setFail_cnt(0);
			joinMem.setFail_date("");
			memberService.joinUser(joinMem);

		} catch (Exception e) {
			e.printStackTrace();
			
			model.addAttribute("msg", "회원가입 오류!");
			model.addAttribute("url", "/");
			model.addAttribute("type", "url");
		}
		
		model.addAttribute("msg", "회원가입이 완료되었습니다.");
		model.addAttribute("url", "/login");
		model.addAttribute("type", "url");
		return "inc/alert";
	}

}
