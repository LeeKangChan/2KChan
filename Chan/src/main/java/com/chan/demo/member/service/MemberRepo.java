package com.chan.demo.member.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepo extends JpaRepository<Member, String> {

	public Member findByIdAndPwd(String id, String pwd);

	public int countById(String id);
}
