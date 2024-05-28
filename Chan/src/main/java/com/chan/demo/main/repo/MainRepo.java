package com.chan.demo.main.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chan.demo.member.entity.DelMember;

@Repository
public interface MainRepo extends JpaRepository<DelMember, Long> {

}
