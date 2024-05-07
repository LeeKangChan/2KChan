package com.chan.demo.member.service;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DelMemberRepo extends JpaRepository<DelMember, Long> {

}
