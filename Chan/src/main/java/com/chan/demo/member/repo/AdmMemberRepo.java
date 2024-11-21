package com.chan.demo.member.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chan.demo.member.entity.Member;

@Repository
public interface AdmMemberRepo extends JpaRepository<Member, String> {

	public Member findByIdAndPwdAndLevel(String id, String pwd, String level);

}
