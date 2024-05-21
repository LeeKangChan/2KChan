package com.chan.demo.member.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chan.demo.member.service.DelMember;

@Repository
public interface DelMemberRepo extends JpaRepository<DelMember, Long> {

}
