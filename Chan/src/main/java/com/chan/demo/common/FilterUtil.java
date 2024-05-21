package com.chan.demo.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern; 
import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;
/**
 * 필터처리
 */

public class FilterUtil {   
   
    public static String unscript(String data,String ty) {
        if (data == null || data.trim().equals("")) {
            return "";
        }
        String ret = data;
        if("".equals(ty)){//저장시
        	
        	ret = ret.replaceAll("&" ,"&amp;");        	
        	ret = ret.replaceAll("[(]", "&#40;");
	        ret = ret.replaceAll("[)]", "&#41;");
	        ret = ret.replaceAll("<", "&lt;");
	        ret = ret.replaceAll(">", "&gt;");	 
	        ret = ret.replaceAll("\"","&quot;");
	        ret = ret.replaceAll("'", "&apos;"); 
		            
        }else{//출력시
        	ret = ret.replaceAll("&amp;","&");
        	
        	//추가
        	ret = ret.replace("&#61;", "=");
            ret = ret.replace("&#37;", "%");            
            ret = ret.replace("&#39;", "'");
            ret = ret.replace("&#35;", "#");
        	//end            
        	ret = ret.replaceAll("&#40;","(" );
	        ret = ret.replaceAll("&#41;",")");
        	ret = ret.replaceAll("&lt;","<");
            ret = ret.replaceAll("&gt;",">");
            ret = ret.replaceAll("&quot;","\"");
            ret = ret.replaceAll("&apos;","'");
            
        	//추가 start
            ret = ret.replace("[equal]", "=");
        	ret = ret.replace("&nbsp;", " ");
        	ret = ret.replace("<br>", "<br/>");
        	ret = ret.replace("<BR>", "<br/>");
        	//end 
        	           
        	
        }
        return ret;
    }

    // 4자 이상 연속 사용할 수 없습니다
    public static String strstreakchk(String str){
    	String ret="";	    			
			Pattern pat4 = Pattern.compile("(abcd)|(bcde)|(cdef)|(defg)|(ghij)|(hijk)|(ijkl)|(lmno)|(mnop)|(nopq)|(opqr)|(pqrs)|(qrst)|(rstu)|(stuv)|(tuvw)|(uvwx)|(vwxy)|(wxyz)");
			Matcher mat4 = pat4.matcher(str);
			if(mat4.find()){ret="0";}
	
			Pattern pat5 = Pattern.compile("(0123)|(1234)|(2345)|(3456)|(4567)|(5678)|(6789)|(7890)");
			Matcher mat5 = pat5.matcher(str);
			if(mat5.find()){ret="0";}
	
			Pattern pat6 = Pattern.compile("(qwer)|(wert)|(erty)|(rtyu)|(tyui)|(yuio)|(uiop)");
			Matcher mat6 = pat6.matcher(str);
			if(mat6.find()){ret="0";}
	
			Pattern pat7 = Pattern.compile("(asdf)|(sdfg)|(dfgh)|(fghj)|(ghjk)|(hjkl)");
			Matcher mat7 = pat7.matcher(str);
			if(mat7.find()){ret="0";}
	
			Pattern pat8 = Pattern.compile("(zxcv)|(xcvb)|(cvbn)|(vbnm)");
			Matcher mat8 = pat8.matcher(str);
			if(mat8.find()){ret="0";}
			String err="";
			if(ret.equals("0")){
				err="y";
			}
			return err;
	 }
    
	 //3자 이상 중복 사용할 수 없습니다.
	 public static String strsamechk(String str){
	 	String err="";
	 	if(str.length()-2>0){
	 		for(int i=0; i < str.length()-2; i++){
	 			char c = str.charAt(i);
	 			char cc1 = str.charAt(i+1);
	 			char cc2 = str.charAt(i+2);
	 			if(c==(cc1)&&c==(cc2)){
	 				err="y";
	 				break;
	 			}
	 		}
	 	}else{
	 		err="y";
	 	}
	 	return err;
	 }

	 //비밀번호는 영문소문자, 숫자,특수문자(!,@,#,$,%,^,&,*) 만 사용 가능합니다
	 public static String strpwchk(String str){
	 	String err="";
	 	if(str.length()>0){
	 		for(int i=0; i < str.length(); i++){
	 			char c = str.charAt(i);
	 			Pattern p1 = Pattern.compile("[a-z]");//영문소문자
		    		Matcher m1 = p1.matcher(""+c); 
		    		Pattern p3 = Pattern.compile("[0-9]");//숫자
		    		Matcher m3 = p3.matcher(""+c); 	    		
		    		Pattern p2 = Pattern.compile("[~,!,@,#,$,%,^,&,*]");//특수문자
		    		Matcher m2 = p2.matcher(""+c); 
		    		boolean ch1=m1.find();
		    		boolean ch2=m2.find();
		    		boolean ch3=m3.find();	    		
		    		if(ch1==true){
		    			
		    		}else if(ch2==true){
		    			
		    		}else if(ch3==true){
		    			
	 			}else{
	 				err="y";
	 				break;
	 			}
	 		}
	 	}else{
	 		err="y";
	 	}
	 	return err;
	 }
	 
	 /* 아이디 체크 메세지 리턴값이 "" 일경우만 정상 , 그외는 메세지임 */
	 public static  String idchkmsg(String str){ 
		String msg1="아이디는 6~20자리 만 사용 가능합니다.";
		String msg2="아이디는 특수문자를 사용 할 수 없습니다.";
		String msg3="아이디는 3자 이상 중복 사용할 수 없습니다.";
		String msg4="아이디는 4자 이상 연속 사용할 수 없습니다.";   		
		String chk="";
		if(str.length()<6||str.length()>20){
			chk=msg1;
		}else{
    		Pattern  pattern  =  Pattern.compile("^[a-z0-9]*$");
    		Matcher  match  =  pattern.matcher(str);	    		                              
    		if(!match.matches()){
    			chk=msg2;
    		}else{
    			String err = strsamechk(str);
    			if(err.equals("y")){
    				chk=msg3;
    			}else{
    				String err2 = strstreakchk(str);       				
    				if(err2.equals("y")){
        				chk=msg4;
        			}
    			}
    		}
    	}
		return chk;    	 
	}   	

	public static String encryptPassword(String data) throws Exception {

		if (data == null) {
		    return "";
		}
		
		byte[] plainText = null; // 평문
		byte[] hashValue = null; // 해쉬값
		plainText = data.getBytes();
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		hashValue = md.digest(plainText); 
		
		return new String(Base64.encodeBase64(hashValue));
    }
	    
}
