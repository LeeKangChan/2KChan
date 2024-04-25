package com.chan.demo.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	@Autowired
	private MemberRepo memberRepo;
	
	public void joinUser(Member member) {
		memberRepo.save(member);
	}
	
	public Member loginUser(Member member) {
		String id = member.getId();
		String pwd = member.getPwd();
		
		Member chkUser = memberRepo.findByIdAndPwd(id, pwd);
		return chkUser;
	}
	
	public int chkId(String id) {
		
		int cnt = 0;
		
		cnt = memberRepo.countById(id);
				
		return cnt;
	}
}
