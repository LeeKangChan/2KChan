package com.chan.demo.member.service.imple;

import org.springframework.stereotype.Service;

import com.chan.demo.member.service.LoginService;
import com.chan.demo.member.service.LoginVO;

import jakarta.annotation.Resource;

@Service
public class LoginServiceImple implements LoginService{

	@Resource(name="LoginDAO")
	private LoginDAO loginDAO;
	
	@Override
	public String loginChk(LoginVO vo) {
		
		loginDAO.loginChk(vo);
		return null;
	}
	
	
}
