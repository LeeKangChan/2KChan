package com.chan.demo.member.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chan.demo.common.FilterUtil;
import com.chan.demo.member.entity.DelMember;
import com.chan.demo.member.entity.Member;
import com.chan.demo.member.repo.DelMemberRepo;
import com.chan.demo.member.repo.MemberRepo;

@Service
public class MemberService {
	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private DelMemberRepo delMemberRepo;
    
	// 입력 정보 특수문자 제거
  	public Member enc(Member article) throws Exception {
  		
  		article.setId(FilterUtil.unscript(article.getId(),""));
  		article.setName(FilterUtil.unscript(article.getName(),"")); 
  		
  		article.setSex(FilterUtil.unscript(article.getSex(),""));
  		article.setBirth(FilterUtil.unscript(article.getBirth(),""));  		
  		article.setEmail(FilterUtil.unscript(article.getEmail(),""));
  		
  		article.setTel(FilterUtil.unscript(article.getTel(),""));
  		article.setPost(FilterUtil.unscript(article.getPost(),""));
  		
  		return article;
  	}
	
  	// 회원가입
	public String joinUser(Member member) {
		
		String pwd = member.getPwd();
		String err = "";
		
		try {
			// 비밀번호 암호화
			pwd = FilterUtil.encryptPassword(pwd);
			
			member.setPwd(pwd);
			
			member = enc(member);
			
			memberRepo.save(member);		
		} catch (Exception e) {
			e.printStackTrace();
			err = "y";
		}
		return err;
	}
	
	// 로그인 - 아이디 비밀번호 체크
	public Member loginUser(Member member) {
		String id = member.getId();
		String pwd = member.getPwd();
		
		Member errMem = new Member();
		errMem.setId("1");
		
		try {
			pwd = FilterUtil.encryptPassword(pwd);
			
			Member chkUser = memberRepo.findByIdAndPwd(id, pwd);
			return chkUser;
		} catch (Exception e) {
			e.printStackTrace();
			return errMem;
		}
	}
	
	// 아이디 count
	public int chkId(String id) {
		
		int cnt = 0;
		
		cnt = memberRepo.countById(id);
				
		return cnt;
	}
	
	// 전화번호 count
	public int chkTel(String tel) {
		
		int cnt = 0;
		
		cnt = memberRepo.countByTel(tel);
				
		return cnt;
	}
	
	// 아이디 비밀번호 count
	public int countByIdAndPwd(String id, String pwd) {
		
		int cnt = 0;

		try {
			
			pwd = FilterUtil.encryptPassword(pwd);
			
			cnt = memberRepo.countByIdAndPwd(id, pwd);
			
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
				
		return cnt;
	}
	
	// 회원 삭제
	public void deleteById(String id) {
		memberRepo.deleteById(id);
	}
	
	// 아이디 찾기 - 이름, 생년월일, 전화번호, 이메일로 회원 찾기
	public Member findId(String name, String birth, String tel, String email) {
		Member findMem = new Member();
		
		findMem = memberRepo.findByNameAndBirthAndTelAndEmail(name, birth, tel, email);
		
		return findMem;
	}
	
	// 비밀번호 찾기 - 아이디, 이름, 생년월일, 전화번호, 이메일로 회원 찾기
	public Member findPwd(String id, String name, String birth, String tel, String email) {
		Member findMem = new Member();
		
		findMem = memberRepo.findByIdAndNameAndBirthAndTelAndEmail(id, name, birth, tel, email);
		
		return findMem;
	}
	
	// 비밀번호 변경
	public String changePwd(Member chMember) {
		
		String id = chMember.getId();
		
		Optional<Member> opMember  = memberRepo.findById(id);
		
		if(opMember.isEmpty()) { 
			return "null";
		}
		
		Member member = new Member();
		
		member = opMember.get();
		
		String pwd = chMember.getPwd();
		
		try {
			pwd = FilterUtil.encryptPassword(pwd);
			
			member.setPwd(pwd);
			member.setModId(member.getModId());
			member.setModDate(chMember.getModDate());			
			member.setFailCnt(0);
			member.setFailDate("");
			memberRepo.save(member);		
		} catch (Exception e) {
			e.printStackTrace();
			return "y";
		}
		
		return "";
	}
	
	// 회원 정보 수정
	public String modUser(Member chMember) {
		
		String id = chMember.getId();
		Optional<Member> opMember  = memberRepo.findById(id);
		
		if(opMember.isEmpty()) { 
			return "null";
		}
		
		Member member = new Member();
		
		member = opMember.get();
		
		member.setName(chMember.getName());
		member.setAge(chMember.getAge());
		member.setPost(chMember.getPost());
		member.setTel(chMember.getTel());
		member.setSex(chMember.getSex());
		member.setBirth(chMember.getBirth());
		member.setEmail(chMember.getEmail());
		member.setModId(chMember.getModId());
		member.setModDate(chMember.getModDate());
		member.setFailCnt(0);
		member.setFailDate("");
		
		memberRepo.save(member);
		try {
			member = enc(member);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return "";
	}
	
	// 로그인 실패
	public void failId(String id) {
		
		Optional<Member> opMember  = memberRepo.findById(id);
		
		if(!opMember.isEmpty()) {
			int failCnt = 0;
			String today = "";
			
			Member member = new Member();
			
			member = opMember.get();

			failCnt = member.getFailCnt() + 1;
			member.setFailCnt(failCnt);
			
			// 로그인 실패 횟수 5회 미만이면 횟수 + 1
			if(failCnt < 5) {	
				memberRepo.save(member);
			}
			
			// 로그인 실패 횟수 5회면 계정 잠금
			if(failCnt == 5) {
				LocalDateTime now = LocalDateTime.now();
				today = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				
				member.setFailDate(today);
				memberRepo.save(member);
			}
		}
	}
	
	// 비밀번호 실패 카운트 초기화
	public void resetFailCnt(String id) {
		Optional<Member> opMember  = memberRepo.findById(id);
		
		if(!opMember.isEmpty()) {			
			Member member = new Member();
			
			member = opMember.get();
			
			member.setFailCnt(0);
			member.setFailDate("");

			memberRepo.save(member);
		}
	}
	
	// 탈퇴 회원 로그 저장
	public void delUserSave(DelMember delMem) {

		delMemberRepo.save(delMem);
	}
}
