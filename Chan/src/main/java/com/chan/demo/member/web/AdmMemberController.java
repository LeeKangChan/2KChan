package com.chan.demo.member.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.chan.demo.common.AlertUtil;
import com.chan.demo.member.entity.Member;
import com.chan.demo.member.service.MemberService;
import com.chan.demo.member.service.AdmMemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdmMemberController {
	
	@Autowired
    private AdmMemberService admMemberService;
	
	@Autowired
    private MemberService memberService;
	
	// 로그인 페이지 이동
	@RequestMapping("/adChan/login")
	public String Login(HttpServletRequest request, ModelMap model) {
		
		// 로그인 세션 확인
		Member member = (Member) request.getSession().getAttribute("Login");
			
		if(member != null) {
			return "redirect:/adChan/main";
		}

		return "adm/member/login";
	}

	// 아이디 비밀번호 확인
	@RequestMapping("/adChn/loginChk")
	public String LoginChk(HttpServletRequest request, ModelMap model) {
			
		Member member = (Member) request.getSession().getAttribute("Login");
			
		if(member != null) {
			return "redirect:/adChan/main";
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
		Member chkMember = admMemberService.admLoginUser(loginMem);
		
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
		if(chkMember.getFailCnt() == 5 || !chkMember.getFailDate().equals("")) {
			AlertUtil.returnAlert(model, "로그인 5회 실패로 계정이 정지되었습니다. 비밀번호 찾기를 통해 본인인증 후 비밀번호를 변경해 주세요", "/adChan/findPwd", "url");
			return "inc/alert";
		}
		
		// 로그인 실패 카운트 +1 (아이디가 일치하면)
		memberService.resetFailCnt(id);
		
		chkMember.setPwd("");
		
		request.getSession().setAttribute("Login", chkMember);
		
		return "redirect:/";
	}
	
	// 로그아웃
	@RequestMapping("/adChan/logout")
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
	
}
