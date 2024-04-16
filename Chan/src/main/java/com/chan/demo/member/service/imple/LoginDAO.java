package com.chan.demo.member.service.imple;

import java.util.HashMap;
import org.springframework.stereotype.Repository;
import com.chan.demo.member.service.LoginVO;



@Repository("LoginDAO")
public class LoginDAO {

	public String loginChk(LoginVO vo) {
		
		int chk = 0;
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", vo.getId());
		map.put("pwd", vo.getPwd());
		
//		chk= (Integer)selectOne("TLoginDAO.actionLoginChk", map);
		
		return "";
	}

}
