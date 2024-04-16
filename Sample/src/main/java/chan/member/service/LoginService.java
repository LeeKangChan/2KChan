package chan.member.service;
 
public interface  LoginService { 
	/**
	 * 일반 로그인을 처리한다 
	 */ 
    public LoginVO actionLoginStep1(LoginVO vo) throws Exception;
	public LoginVO reSetLogin(LoginVO vo)throws Exception;
    public String actionLoginOK(LoginVO vo ) throws Exception; 
    public int actionLoginTChk(LoginVO vo)throws Exception; 
    public int actionLoginStopChk(LoginVO vo)throws Exception;
    public void updateMemberStop(String user_id) throws Exception;
    
}
