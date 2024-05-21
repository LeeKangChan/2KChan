package com.chan.demo.member.web;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chan.demo.common.AlertUtil;
import com.chan.demo.common.FilterUtil;
import com.chan.demo.common.StringUtil;
import com.chan.demo.member.service.DelMember;
import com.chan.demo.member.service.Member;
import com.chan.demo.member.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class MemberController {
	
	@Autowired
    private MemberService memberService;
	
	// 로그인 페이지 이동
	@RequestMapping("/login")
	public String Login(HttpServletRequest request, ModelMap model) {
		
		
		// 로그인 세션 확인
		Member member = (Member) request.getSession().getAttribute("Login");
			
		if(member != null) {
			AlertUtil.returnAlert(model, "이미 로그인된 회원이 있습니다.", "/", "url");
			return "inc/alert";
		}

		return "member/login";
	}

	// 아이디 비밀번호 확인
	@RequestMapping("/loginChk")
	public String LoginChk(HttpServletRequest request, ModelMap model) {
			
		Member member = (Member) request.getSession().getAttribute("Login");
			
		if(member != null) {
			AlertUtil.returnAlert(model, "이미 로그인된 회원이 있습니다.", "/", "url");
			return "inc/alert";
		} 
		
		String id = "";
		String pwd = "";
		String msg = "";
		
		// 아이디 비밀번호 파라미터 확인
		if(request.getParameter("id") == null || request.getParameter("id").equals("")) {
			msg = "아이디를 입력해 주세요";
		}
		
		if(request.getParameter("pwd") == null || request.getParameter("pwd").equals("")) {
			msg = "비밀번호를 입력해 주세요";
		}
		
		if(!msg.equals("")) {
			AlertUtil.returnAlert(model, msg, "history.back()", "script");
			return "inc/alert";
		}
		
		id = request.getParameter("id");
		pwd = request.getParameter("pwd");
		
		Member loginMem = new Member();
		
		loginMem.setId(id);
		loginMem.setPwd(pwd);
		
		// 아이디 비밀번호 일치 확인
		Member chkMember = memberService.loginUser(loginMem);
		
		//로그인 실패
		if(chkMember == null) {			
			memberService.failId(id);
			AlertUtil.returnAlert(model, "아이디 또는 비밀번호를 확인해 주세요. 로그인 5회 실패시 계정이 정지됩니다.", "/login", "url");
			return "inc/alert";
		}
		
		// 로그인 오류
		if(chkMember.getId().equals("1")) {
			AlertUtil.returnAlert(model, "로그인 오류! 담당자에게 문의 바랍니다.", "/", "url");
			return "inc/alert";
		}
		
		//계정 정지
		if(chkMember.getFail_cnt() == 5 || !chkMember.getFail_date().equals("")) {
			AlertUtil.returnAlert(model, "로그인 5회 실패로 계정이 정지되었습니다. 비밀번호 찾기를 통해 본인인증 후 비밀번호를 변경해 주세요", "/findPwd", "url");
			return "inc/alert";
		}
		
		// 로그인 실패 카운트 +1 (아이디가 일치하면)
		memberService.resetFailCnt(id);
		
		chkMember.setPwd("");
		
		request.getSession().setAttribute("Login", chkMember);
		
		return "redirect:/";
	}
	
	// 로그아웃
	@RequestMapping("/logout")
	public String Logout(HttpServletRequest request, ModelMap model) {
			
		Member member = (Member) request.getSession().getAttribute("Login");
		
		if(member == null) {
			AlertUtil.returnAlert(model, "잘못된 접근", "/", "url");
			return "inc/alert";
		}
		
		// 로그인 세션 제거
		HttpSession session = request.getSession();
		session.removeAttribute("Login");
		session.removeAttribute("MyPageChk");
		
		AlertUtil.returnAlert(model, "로그아웃 되었습니다.", "/", "url");
		return "inc/alert";
	}
	
	// 회원가입 페이지 이동
	@RequestMapping("/join")
	public String Join(HttpServletRequest request, ModelMap model) {
			
		Member member = (Member) request.getSession().getAttribute("Login");
			
		if(member != null) {
			AlertUtil.returnAlert(model, "이미 로그인된 회원이 있습니다.", "/", "url");
			return "inc/alert";
		}

		return "member/join";
	}
	
	// 회원가입 아이디 체크 ajax
	@ResponseBody
	@RequestMapping("/idChk")
	public String IdChk(HttpServletRequest request, ModelMap model, @RequestParam("id") String id) {
		
		String chk = "Y";
		String msg = "";
		
		if(id == null || id.equals("")) {
			return chk;
		}
		
		int cnt = 0;
		
		// 아이디 count
		cnt = memberService.chkId(id);
		
		if(cnt != 0) {
			chk = "N";
			msg = "중복된 아이디 입니다.";
		} else if(!FilterUtil.idchkmsg(id).equals("")) { // 아이디 규칙 체크
			chk = "N";
			msg = FilterUtil.idchkmsg(id);
		}
		
		if(chk == "N") {
			return msg;
		}

		return chk;
	}
	
	// 회원가입 입력 정보 확인
	@RequestMapping("/joinChk")
	public String JoinChk(HttpServletRequest request, ModelMap model) {
			
		Member member = (Member) request.getSession().getAttribute("Login");
			
		if(member != null) {
			AlertUtil.returnAlert(model, "이미 로그인된 회원이 있습니다.", "/", "url");
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
		
		// 파라미터 확인
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
			AlertUtil.returnAlert(model, msg, "history.back()", "script");
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
			msg = "비밀번호가 서로 다릅니다. 다시 확인해 주세요";
		}
		
		if(FilterUtil.strpwchk(pwd).equals("y") || pwd.length() < 8 || pwd.length() > 16) {
			msg = "비밀번호는 영문, 숫자, 특수문자를 포함하여 8~16자로 입력해 주세요";
		}
		
		if(!StringUtil.chkInt(age)) {
			msg = "나이는 숫자만 입력 가능합니다.";
		}
		
		if(!StringUtil.chkInt(birth)) {
			msg = "생년월일은 숫자만 입력 가능합니다.";
		}
		
		if(birth.length() != 8) {
			msg = "생년월일은 8자로 입력해 주세요";
		}
		
		if(!StringUtil.chkInt(tel1) || !StringUtil.chkInt(tel2) || !StringUtil.chkInt(tel3)) {
			msg = "전화번호는 숫자만 입력 가능합니다.";
		}
		
		if(!msg.equals("")) {
			AlertUtil.returnAlert(model, msg, "history.back()", "script");
			return "inc/alert";
		}

		tel = tel1 + "-" + tel2 + "-" + tel3;
		email = email1 + "@" + email2;
		
		int cnt = 0;
		
		// 아이디 중복 확인
		cnt = memberService.chkId(id);
		
		if(cnt > 0) {
			msg = "중복된 아이디 입니다.";
		}
		
		// 전화번호 중복 확인
		cnt = memberService.chkTel(tel);
		
		if(cnt > 0) {
			msg = "중복된 전화번호 입니다.";
		}
		
		if(!msg.equals("")) {
			AlertUtil.returnAlert(model, msg, "history.back()", "script");
			return "inc/alert";
		}
		
		// 오늘날짜
		LocalDateTime now = LocalDateTime.now();
		String today = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		Member joinMem = new Member();

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
		
		// save
		String err = memberService.joinUser(joinMem);
		
		if(err.equals("y")) {
			AlertUtil.returnAlert(model, "회원가입 오류! 담당자에게 문의 바랍니다.", "/", "url");
			return "inc/alert";
		}
		
		AlertUtil.returnAlert(model, "회원가입이 완료되었습니다.", "/login", "url");
		return "inc/alert";
	}

	// 아이디 찾기 페이지 이동
	@RequestMapping("/findId")
	public String findId(HttpServletRequest request, ModelMap model) {
			
		Member member = (Member) request.getSession().getAttribute("Login");
			
		if(member != null) {
			AlertUtil.returnAlert(model, "이미 로그인된 회원이 있습니다.", "/", "url");
			return "inc/alert";
		}

		return "member/findId";
	}
	
	// 아이디 찾기 입력 정보 확인
	@RequestMapping("/findIdChk")
	public String FindIdChk(HttpServletRequest request, ModelMap model) {
			
		Member member = (Member) request.getSession().getAttribute("Login");
			
		if(member != null) {
			AlertUtil.returnAlert(model, "이미 로그인된 회원이 있습니다.", "/", "url");
			return "inc/alert";
		} 
		
		String name = "";
		String tel = "";
		String tel1 = "";
		String tel2 = "";
		String tel3 = "";
		String birth = "";
		String email = "";
		String email1 = "";
		String email2 = "";
		
		String msg = "";
		
		if(request.getParameter("name") == null || request.getParameter("name").equals("")) {
			msg = "이름을 입력해 주세요";
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
			AlertUtil.returnAlert(model, msg, "history.back()", "script");
			return "inc/alert";
		}
		
		name = request.getParameter("name");
		tel1 = request.getParameter("tel1");
		tel2 = request.getParameter("tel2");
		tel3 = request.getParameter("tel3");
		birth = request.getParameter("birth");
		email1 = request.getParameter("email1");
		email2 = request.getParameter("email2");

		if(!StringUtil.chkInt(birth)) {
			msg = "생년월일은 숫자만 입력 가능합니다.";
		}
		
		if(birth.length() != 8) {
			msg = "생년월일은 8자로 입력해 주세요";
		}
		
		if(!StringUtil.chkInt(tel1) || !StringUtil.chkInt(tel2) || !StringUtil.chkInt(tel3)) {
			msg = "전화번호는 숫자만 입력 가능합니다.";
		}
		
		if(!msg.equals("")) {
			AlertUtil.returnAlert(model, msg, "history.back()", "script");
			return "inc/alert";
		}

		tel = tel1 + "-" + tel2 + "-" + tel3;
		email = email1 + "@" + email2;
		
		Member findMem = new Member();
		findMem = memberService.findId(name, birth, tel, email);
		
		String id = "";
		String chk = "";
		
		// 입력 정보 일치 회원 없음
		if(findMem == null) {
			AlertUtil.returnAlert(model, "회원을 찾을 수 없습니다.", "/findId", "url");
			model.addAttribute("msg", "회원을 찾을 수 없습니다.");
			model.addAttribute("url", "/findId");
			model.addAttribute("type", "url");
			return "inc/alert";
		} else {
			id = findMem.getId();
			name = findMem.getName();
			
			model.addAttribute("id", id);
			model.addAttribute("name", name);
			model.addAttribute("chk", chk);
			return "member/findIdResult";
		}
	}
	
	// 비밀번호 찾기 페이지 이동
	@RequestMapping("/findPwd")
	public String findPwd(HttpServletRequest request, ModelMap model) {
			
		Member member = (Member) request.getSession().getAttribute("Login");
			
		if(member != null) {
			AlertUtil.returnAlert(model, "이미 로그인된 회원이 있습니다.", "/", "url");
			return "inc/alert";
		}

		return "member/findPwd";
	}
	
	// 비밀번호 찾기 입력 정보 확인
	@RequestMapping("/findPwdChk")
	public String FindPwdChk(HttpServletRequest request, ModelMap model) {
			
		Member member = (Member) request.getSession().getAttribute("Login");
			
		if(member != null) {
			AlertUtil.returnAlert(model, "이미 로그인된 회원이 있습니다.", "/", "url");
			return "inc/alert";
		} 
		
		String id = "";
		String name = "";
		String tel = "";
		String tel1 = "";
		String tel2 = "";
		String tel3 = "";
		String birth = "";
		String email = "";
		String email1 = "";
		String email2 = "";
		
		String msg = "";
		
		if(request.getParameter("id") == null || request.getParameter("id").equals("")) {
			msg = "아이디를 입력해 주세요";
		}
		
		if(request.getParameter("name") == null || request.getParameter("name").equals("")) {
			msg = "이름을 입력해 주세요";
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
			AlertUtil.returnAlert(model, msg, "history.back()", "script");
			return "inc/alert";
		}
		
		id = request.getParameter("id");
		name = request.getParameter("name");
		tel1 = request.getParameter("tel1");
		tel2 = request.getParameter("tel2");
		tel3 = request.getParameter("tel3");
		birth = request.getParameter("birth");
		email1 = request.getParameter("email1");
		email2 = request.getParameter("email2");

		if(!StringUtil.chkInt(birth)) {
			msg = "생년월일은 숫자만 입력 가능합니다.";
		}
		
		if(birth.length() != 8) {
			msg = "생년월일은 8자로 입력해 주세요";
		}
		
		if(!StringUtil.chkInt(tel1) || !StringUtil.chkInt(tel2) || !StringUtil.chkInt(tel3)) {
			msg = "전화번호는 숫자만 입력 가능합니다.";
		}
		
		if(!msg.equals("")) {
			AlertUtil.returnAlert(model, msg, "history.back()", "script");
			return "inc/alert";
		}

		tel = tel1 + "-" + tel2 + "-" + tel3;
		email = email1 + "@" + email2;
		
		// 입력 정보로 회원 찾기
		Member findMem = new Member();
		findMem = memberService.findPwd(id, name, birth, tel, email);
		
		if(findMem == null) {
			AlertUtil.returnAlert(model, "일치 하는 회원이 없습니다.", "/findPwd", "url");
			return "inc/alert";
		} else {
			// 비밀번호 변경 페이지 이동 (비밀번호는 SHA-256 단방향 암호화 사용, 암호화된 비밀번호 복호화 불가능)
			model.addAttribute("id", id);
			return "member/findPwdResult";
		}
	}
	
	// 비밀번호 변경 입력 정보 확인
	@RequestMapping("/changePwd")
	public String ChangePwd(HttpServletRequest request, ModelMap model) {
			
		Member member = (Member) request.getSession().getAttribute("Login");
			
		if(member != null) {
			AlertUtil.returnAlert(model, "이미 로그인된 회원이 있습니다.", "/", "url");
			return "inc/alert";
		} 
		
		String id = "";
		String pwd = "";
		String pwdChk = "";
		
		String msg = "";
		
		if(request.getParameter("pwd") == null || request.getParameter("pwd").equals("")) {
			msg = "비밀번호를 입력해 주세요";
		}
		
		if(request.getParameter("pwdChk") == null || request.getParameter("pwdChk").equals("")) {
			msg = "비밀번호확인을 입력해 주세요";
		}		
		
		if(!msg.equals("")) {
			AlertUtil.returnAlert(model, msg, "history.back()", "script");
			return "inc/alert";
		}
		
		id = request.getParameter("id");
		pwd = request.getParameter("pwd");
		pwdChk = request.getParameter("pwdChk");
		
		// 아이디는 입력정보가 아닌 자동으로 넘어오는 정보, 파라미터가 없으면 잘못된 접근경로
		if(id == null) {
			AlertUtil.returnAlert(model, "잘못된 접근1", "/findPwd", "url");
			return "inc/alert";
		}
		
		if(!pwd.equals(pwdChk)) {
			msg = "비밀번호가 서로 다릅니다. 다시 확인해 주세요";
		}
		
		if(FilterUtil.strpwchk(pwd).equals("y") || pwd.length() < 8 || pwd.length() > 16) {
			msg = "비밀번호는 영문, 숫자, 특수문자를 포함하여 8~16자로 입력해 주세요";
		}
		
		if(!msg.equals("")) {
			AlertUtil.returnAlert(model, msg, "history.back()", "script");
			return "inc/alert";
		}
		
		
		LocalDateTime now = LocalDateTime.now();
		String today = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

		Member changePwdMem = new Member();
		
		changePwdMem.setId(id);
		changePwdMem.setPwd(pwd);
		changePwdMem.setMod_id(id);
		changePwdMem.setMod_date(today);
		
		// 비밀번호 변경
		String err = memberService.changePwd(changePwdMem);
		
		// 아이디와 일치하는 회원 정보가 없음
		if(err.equals("null")) {
			AlertUtil.returnAlert(model, "잘못된 접근2", "/findPwd", "url");
			return "inc/alert";
		}
		
		// 찾기 오류
		if(err.equals("y")) {
			AlertUtil.returnAlert(model, "비밀번호 찾기 오류! 담당자에게 문의 바랍니다.", "/", "url");
			return "inc/alert";
		}
		
		AlertUtil.returnAlert(model, "비밀번호 변경이 완료되었습니다.", "/login", "url");
		return "inc/alert";
	}
	
	// 마이페이지 페이지 이동
	@RequestMapping("/myPageChk")
	public String MyPageChk(HttpServletRequest request, ModelMap model) {
			
		Member member = (Member) request.getSession().getAttribute("Login");
			
		if(member == null) {
			AlertUtil.returnAlert(model, "로그인 후 진행해 주세요.", "/login", "url");
			return "inc/alert";
		}

		return "member/myPageChk";
	}
	
	// 마이페이지 본인 확인 입력 정보
	@RequestMapping("/myPageChk1")
	public String MyPageChk1(HttpServletRequest request, ModelMap model) {
			
		Member member = (Member) request.getSession().getAttribute("Login");
			
		if(member == null) {
			AlertUtil.returnAlert(model, "로그인 후 진행해 주세요.", "/login", "url");
			return "inc/alert";
		}
		
		String id = "";
		String pwd = "";
		
		String chkId = member.getId();
		
		String msg = "";
		
		if(request.getParameter("id") == null || request.getParameter("pwd").equals("")) {
			msg = "아이디를 입력해 주세요";
		}
		
		if(request.getParameter("pwd") == null || request.getParameter("pwd").equals("")) {
			msg = "비밀번호를 입력해 주세요";
		}
		
		if(!msg.equals("")) {
			AlertUtil.returnAlert(model, msg, "history.back()", "script");
			return "inc/alert";
		}
		
		id = request.getParameter("id");
		pwd = request.getParameter("pwd");
		
		Member mem = new Member();
		
		mem.setId(id);
		mem.setPwd(pwd);
		
		Member chkMem = new Member();
		
		chkMem = memberService.loginUser(mem);
		
		
		if(!id.equals(chkId) || chkMem == null) {
			AlertUtil.returnAlert(model, "회원 정보가 일치하지 않습니다.", "history.back()", "script");
			return "inc/alert";
		}
		
		if(chkMem.getId().equals("1")) {
			AlertUtil.returnAlert(model, "회원체크 오류! 담당자에게 문의 바랍니다.", "/", "url");
			return "inc/alert";
		}
		
		// 본인 확인 완료 세션
		request.getSession().setAttribute("MyPageChk", "Y");
		return "redirect:/myPage";
	}
	
	// 마이페이지 이동
	@RequestMapping("/myPage")
	public String MyPage(HttpServletRequest request, ModelMap model) {
		Member member = (Member) request.getSession().getAttribute("Login");
		
		if(member == null) {
			AlertUtil.returnAlert(model, "로그인 후 진행해 주세요.", "/login", "url");
			return "inc/alert";
		}
		
		// 본인 확인 완료 세션 확인
		String myPageChk = (String) request.getSession().getAttribute("MyPageChk");
		
		if(myPageChk == null || !myPageChk.equals("Y")) {
			AlertUtil.returnAlert(model, "본인 확인 후 진행해 주세요", "/myPageChk", "url");
			return "inc/alert";
		}
		
		model.addAttribute("member", member);

		return "member/myPage";
	}
	
	// 회원 정보 수정 페이지 이동
	@RequestMapping("/modUser")
	public String ModUser(HttpServletRequest request, ModelMap model) {
			
		Member member = (Member) request.getSession().getAttribute("Login");		
		
		if(member == null) {
			AlertUtil.returnAlert(model, "로그인 후 진행해 주세요.", "/login", "url");
			return "inc/alert";
		}
		
		String myPageChk = (String) request.getSession().getAttribute("MyPageChk");
		
		if(myPageChk == null || !myPageChk.equals("Y")) {
			AlertUtil.returnAlert(model, "본인 확인 후 진행해 주세요", "/myPageChk", "url");
			return "inc/alert";
		}
		
		String tel1 = "";
		String tel2 = "";
		String tel3 = "";
		
		String email1 = "";
		String email2 = "";
		
		if(member.getTel() != null && !member.getTel().equals("")) {
			String tel[] = member.getTel().split("-");
			
			if(tel.length == 3) {
				tel1 = tel[0];
				tel2 = tel[1];
				tel3 = tel[2];
			}
		}
		
		if(member.getEmail() != null && !member.getEmail().equals("")) {
			String email[] = member.getEmail().split("@");
			
			if(email.length == 2) {
				email1 = email[0];
				email2 = email[1];
			}
		}
		
		model.addAttribute("tel1", tel1);
		model.addAttribute("tel2", tel2);
		model.addAttribute("tel3", tel3);
		model.addAttribute("email1", email1);
		model.addAttribute("email2", email2);
		model.addAttribute("member", member);
		
		return "member/modUser";
	}
	
	// 회원 정보 수정 입력 정보 확인
	@RequestMapping("/modUserChk")
	public String ModUserChk(HttpServletRequest request, ModelMap model) {
			
		Member member = (Member) request.getSession().getAttribute("Login");
			
		if(member == null) {
			AlertUtil.returnAlert(model, "로그인 후 진행해 주세요.", "/login", "url");
			return "inc/alert";
		}

		String myPageChk = (String) request.getSession().getAttribute("MyPageChk");
		
		if(myPageChk == null || !myPageChk.equals("Y")) {
			AlertUtil.returnAlert(model, "본인 확인 후 진행해 주세요", "/myPageChk", "url");
			return "inc/alert";
		}
		
		String id = "";
		String beTel = "";
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
			AlertUtil.returnAlert(model, "잘못된 접근", "/", "url");
			return "inc/alert";
		}
		
		if(request.getParameter("tel") == null || request.getParameter("tel").equals("")) {
			AlertUtil.returnAlert(model, "잘못된 접근2", "/", "url");
			return "inc/alert";
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
			AlertUtil.returnAlert(model, msg, "history.back()", "script");
			return "inc/alert";
		}
		
		id = request.getParameter("id");
		beTel = request.getParameter("tel");
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
		
		if(!StringUtil.chkInt(age)) {
			msg = "나이는 숫자만 입력 가능합니다.";
		}
		
		if(!StringUtil.chkInt(birth)) {
			msg = "생년월일은 숫자만 입력 가능합니다.";
		}
		
		if(birth.length() != 8) {
			msg = "생년월일은 8자로 입력해 주세요";
		}
		
		if(!StringUtil.chkInt(tel1) || !StringUtil.chkInt(tel2) || !StringUtil.chkInt(tel3)) {
			msg = "전화번호는 숫자만 입력 가능합니다.";
		}
		
		if(!msg.equals("")) {
			AlertUtil.returnAlert(model, msg, "history.back()", "script");
			return "inc/alert";
		}

		tel = tel1 + "-" + tel2 + "-" + tel3;
		email = email1 + "@" + email2;
		
		int cnt = 0;
		
		cnt = memberService.chkId(id);
		
		if(cnt != 1) {
			AlertUtil.returnAlert(model, "잘못된 접근3", "/", "url");
			return "inc/alert";
		}
		
		if(!tel.equals(beTel)) {
			cnt = memberService.chkTel(tel);
			
			if(cnt > 0) {
				AlertUtil.returnAlert(model, "중복된 전화번호 입니다.", "history.back()", "script");
				return "inc/alert";
			}
		}

		LocalDateTime now = LocalDateTime.now();
		
		String today = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		Member modUserMem = new Member();
		
		modUserMem.setId(id);
		modUserMem.setName(name);
		modUserMem.setAge(age);
		modUserMem.setPost(post);
		modUserMem.setTel(tel);
		modUserMem.setSex(sex);
		modUserMem.setBirth(birth);
		modUserMem.setEmail(email);
		modUserMem.setMod_id(id);
		modUserMem.setMod_date(today);
		modUserMem.setFail_cnt(0);
		modUserMem.setFail_date("");
		
		String err = memberService.modUser(modUserMem);
		
		if(err.equals("null")) {
			AlertUtil.returnAlert(model, "잘못된 접근4", "/", "url");
			return "inc/alert";
		}
		
		if(err.equals("y")) {
			AlertUtil.returnAlert(model, "회원 정보 수정 오류", "/", "url");
			return "inc/alert";
		}
		
		// 변경된 회원 정보로 세션 변경
		HttpSession session = request.getSession();
		session.removeAttribute("Login");
		
		request.getSession().setAttribute("Login", modUserMem);
		
		AlertUtil.returnAlert(model, "회원 정보가 수정되었습니다.", "/myPage", "url");
		return "inc/alert";
	}
	
	// 비밀번호 변경 페이지 이동
	@RequestMapping("/modPwd")
	public String ModPwd(HttpServletRequest request, ModelMap model) {
		Member member = (Member) request.getSession().getAttribute("Login");
		
		if(member == null) {
			AlertUtil.returnAlert(model, "로그인 후 진행해 주세요.", "/login", "url");
			return "inc/alert";
		}

		String myPageChk = (String) request.getSession().getAttribute("MyPageChk");
		
		if(myPageChk == null || !myPageChk.equals("Y")) {
			AlertUtil.returnAlert(model, "본인 확인 후 진행해 주세요", "/myPageChk", "url");
			return "inc/alert";
		}
		
		model.addAttribute("member", member);

		return "member/modPwd";
	}
	
	// 비밀번호 변경 입력 정보 확인
	@RequestMapping("/modPwdChk")
	public String ModPwdChk(HttpServletRequest request, ModelMap model) {
		Member member = (Member) request.getSession().getAttribute("Login");
		
		if(member == null) {
			AlertUtil.returnAlert(model, "로그인 후 진행해 주세요.", "/login", "url");
			return "inc/alert";
		}

		String myPageChk = (String) request.getSession().getAttribute("MyPageChk");
		
		if(myPageChk == null || !myPageChk.equals("Y")) {
			AlertUtil.returnAlert(model, "본인 확인 후 진행해 주세요", "/myPageChk", "url");
			return "inc/alert";
		}
		
		String id = "";
		String pwd = "";
		String newPwd = "";
		String newPwdChk = "";
		
		String msg = "";
		
		if(request.getParameter("id") == null || request.getParameter("id").equals("")) {
			AlertUtil.returnAlert(model, "잘못된 접근", "/", "url");
			return "inc/alert";
		}
		
		if(request.getParameter("pwd") == null || request.getParameter("pwd").equals("")) {
			msg = "기존 비밀번호를 입력해 주세요";
		}
		
		if(request.getParameter("newPwd") == null || request.getParameter("newPwd").equals("")) {
			msg = "새 비밀번호를 입력해 주세요";
		}
		
		if(request.getParameter("newPwdChk") == null || request.getParameter("newPwdChk").equals("")) {
			msg = "새 비밀번호 확인을 입력해 주세요";
		}
		
		if(!msg.equals("")) {
			AlertUtil.returnAlert(model, msg, "history.back()", "script");
			return "inc/alert";
		}
		
		id = request.getParameter("id");
		pwd = request.getParameter("pwd");
		newPwd = request.getParameter("newPwd");
		newPwdChk = request.getParameter("newPwdChk");
		
		int cnt = 0;
		
		cnt = memberService.countByIdAndPwd(id, pwd);
		
		if(cnt == -1) {
			AlertUtil.returnAlert(model, "비밀번호 확인 오류! 담당자에게 문의 바랍니다.", "/", "url");
			return "inc/alert";
		}
		
		if(cnt == 0) {
			msg = "기존 비밀번호가 틀렸습니다. 다시 확인해 주세요";
		}
		
		if(!newPwd.equals(newPwdChk)) {
			msg = "새 비밀번호가 서로 다릅니다. 다시 확인해 주세요";
		}
		
		if(FilterUtil.strpwchk(newPwd).equals("y") || newPwd.length() < 8 || newPwd.length() > 16) {
			msg = "비밀번호는 영문, 숫자, 특수문자를 포함하여 8~16자로 입력해 주세요";
		}
		
		if(!msg.equals("")) {
			AlertUtil.returnAlert(model, msg, "history.back()", "script");
			return "inc/alert";
		}
		
		LocalDateTime now = LocalDateTime.now();
		
		String today = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		Member changePwdMem = new Member();
		
		changePwdMem.setId(id);
		changePwdMem.setPwd(newPwd);
		changePwdMem.setMod_id(id);
		changePwdMem.setMod_date(today);
		
		String err = memberService.changePwd(changePwdMem);

		if(err.equals("null")) {
			AlertUtil.returnAlert(model, "잘못된 접근2", "/findPwd", "url");
			return "inc/alert";
		}
		
		if(err.equals("y")) {
			AlertUtil.returnAlert(model, "비밀번호 변경 오류! 담당자에게 문의 바랍니다.", "/", "url");
			return "inc/alert";
		}
		
		HttpSession session = request.getSession();
		session.removeAttribute("Login");
		session.removeAttribute("MyPageChk");
		
		AlertUtil.returnAlert(model, "비밀번호 변경이 완료되었습니다. 다시 로그인 해주세요", "/login", "url");
		return "inc/alert";
	}
	
	// 회원 탈퇴 페이지 이동
	@RequestMapping("/delUserChk")
	public String DelUserChk(HttpServletRequest request, ModelMap model) {
		Member member = (Member) request.getSession().getAttribute("Login");
		
		if(member == null) {
			AlertUtil.returnAlert(model, "로그인 후 진행해 주세요.", "/login", "url");
			return "inc/alert";
		}

		String myPageChk = (String) request.getSession().getAttribute("MyPageChk");
		
		if(myPageChk == null || !myPageChk.equals("Y")) {
			AlertUtil.returnAlert(model, "본인 확인 후 진행해 주세요", "/myPageChk", "url");
			return "inc/alert";
		}
		
		return "member/delUserChk";
	}
	
	// 회원 탈퇴 입력 정보 확인
	@RequestMapping("/delUser")
	public String DelUser(HttpServletRequest request, ModelMap model) {
		Member member = (Member) request.getSession().getAttribute("Login");
		
		if(member == null) {
			AlertUtil.returnAlert(model, "로그인 후 진행해 주세요.", "/login", "url");
			return "inc/alert";
		}

		String myPageChk = (String) request.getSession().getAttribute("MyPageChk");
		
		if(myPageChk == null || !myPageChk.equals("Y")) {
			AlertUtil.returnAlert(model, "본인 확인 후 진행해 주세요", "/myPageChk", "url");
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
			AlertUtil.returnAlert(model, msg, "history.back()", "script");
			return "inc/alert";
		}
		
		id = request.getParameter("id");
		pwd = request.getParameter("pwd");
		
		int cnt = 0;		
		cnt = memberService.countByIdAndPwd(id, pwd);
		
		if(cnt == -1) {
			AlertUtil.returnAlert(model, "비밀번호 확인 오류! 담당자에게 문의 바랍니다.", "/", "url");
			return "inc/alert";
		}
		
		if(!member.getId().equals(id) || cnt == 0) {
			AlertUtil.returnAlert(model, "아이디 또는 패스워드가 틀립니다. 다시 확인해 주세요", "/delUserChk", "url");
			return "inc/alert";
		}
		
		memberService.deleteById(id);

		LocalDateTime now = LocalDateTime.now();
		String today = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		DelMember delMem = new DelMember();
		
		delMem.setUser_id(member.getId());
		delMem.setName(member.getName());
		delMem.setAge(member.getAge());
		delMem.setSex(member.getSex());
		delMem.setBirth(member.getBirth());
		delMem.setTel(member.getTel());
		delMem.setPost(member.getPost());
		delMem.setEmail(member.getEmail());
		delMem.setDel_id(member.getId());
		delMem.setDel_date(today);
		
		// 탈퇴 회원 로그 저장
		memberService.delUserSave(delMem);
		
		HttpSession session = request.getSession();
		session.removeAttribute("Login");
		session.removeAttribute("MyPageChk");
		
		AlertUtil.returnAlert(model, "회원 탈퇴에 성공했습니다. 이용해 주셔서 감사합니다.", "/", "url");
		return "inc/alert";
	}
	
}
