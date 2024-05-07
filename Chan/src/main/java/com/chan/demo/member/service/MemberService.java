package com.chan.demo.member.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chan.demo.common.FilterUtil;

@Service
public class MemberService {
	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private DelMemberRepo delMemberRepo;
    
  //저장용
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
	
	public String joinUser(Member member) {
		
		String pwd = member.getPwd();
		String err = "";
		
		try {
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
	
	public int chkId(String id) {
		
		int cnt = 0;
		
		cnt = memberRepo.countById(id);
				
		return cnt;
	}
	
	public int chkTel(String tel) {
		
		int cnt = 0;
		
		cnt = memberRepo.countByTel(tel);
				
		return cnt;
	}
	
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
	
	public void deleteById(String id) {
		memberRepo.deleteById(id);
	}
	
	public Member findId(String name, String birth, String tel, String email) {
		Member findMem = new Member();
		
		findMem = memberRepo.findByNameAndBirthAndTelAndEmail(name, birth, tel, email);
		
		return findMem;
	}
	
	public Member findPwd(String id, String name, String birth, String tel, String email) {
		Member findMem = new Member();
		
		findMem = memberRepo.findByIdAndNameAndBirthAndTelAndEmail(id, name, birth, tel, email);
		
		return findMem;
	}
	
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
			member.setMod_id(member.getMod_id());
			member.setMod_date(chMember.getMod_date());			
			member.setFail_cnt(0);
			member.setFail_date("");
			memberRepo.save(member);		
		} catch (Exception e) {
			e.printStackTrace();
			return "y";
		}
		
		return "";
	}
	
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
		member.setMod_id(chMember.getMod_id());
		member.setMod_date(chMember.getMod_date());
		member.setFail_cnt(0);
		member.setFail_date("");
		memberRepo.save(member);		

		return "";
	}
	
	public void failId(String id) {
		
		Optional<Member> opMember  = memberRepo.findById(id);
		
		if(!opMember.isEmpty()) {
			int failCnt = 0;
			String today = "";
			
			Member member = new Member();
			
			member = opMember.get();

			failCnt = member.getFail_cnt() + 1;
			member.setFail_cnt(failCnt);
			
			if(failCnt < 5) {	
				memberRepo.save(member);
			}
			
			if(failCnt == 5) {
				LocalDateTime now = LocalDateTime.now();
				today = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				
				member.setFail_date(today);
				memberRepo.save(member);
			}
		}
	}
	
	public void resetFailCnt(String id) {
		Optional<Member> opMember  = memberRepo.findById(id);
		
		if(!opMember.isEmpty()) {			
			Member member = new Member();
			
			member = opMember.get();
			
			member.setFail_cnt(0);
			member.setFail_date("");

			memberRepo.save(member);
		}
	}
	
	public void delUserSave(DelMember delMem) {

		delMemberRepo.save(delMem);
	}
}
