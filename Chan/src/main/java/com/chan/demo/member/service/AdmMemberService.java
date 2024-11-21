package com.chan.demo.member.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chan.demo.common.FilterUtil;
import com.chan.demo.member.entity.DelMember;
import com.chan.demo.member.entity.Member;
import com.chan.demo.member.repo.AdmMemberRepo;
import com.chan.demo.member.repo.DelMemberRepo;
import com.chan.demo.member.repo.MemberRepo;

@Service
public class AdmMemberService {
	@Autowired
	private AdmMemberRepo admMemberRepo;
	
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
	
	
	// 로그인 - 아이디 비밀번호 체크
	public Member admLoginUser(Member member) {
		String id = member.getId();
		String pwd = member.getPwd();
		
		Member errMem = new Member();
		errMem.setId("1");
		
		try {
			pwd = FilterUtil.encryptPassword(pwd);
			
			Member chkUser = admMemberRepo.findByIdAndPwdAndLevel(id, pwd, "1");
			return chkUser;
		} catch (Exception e) {
			e.printStackTrace();
			return errMem;
		}
	}
	

}
