package com.chan.demo.member.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chan.demo.member.service.Member;

@Repository
public interface MemberRepo extends JpaRepository<Member, String> {

	public Member findByIdAndPwd(String id, String pwd);
	
	public int countByIdAndPwd(String id, String pwd);
	
	public int countById(String id);
	
	public int countByTel(String tel);
	
	public Member findByNameAndBirthAndTelAndEmail(String name, String birth, String Tel, String Email);
	
	public Member findByIdAndNameAndBirthAndTelAndEmail(String id, String name, String birth, String Tel, String Email);
}
