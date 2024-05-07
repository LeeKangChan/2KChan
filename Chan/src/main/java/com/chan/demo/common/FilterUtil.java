package com.chan.demo.common;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern; 
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
/**
 * 필터처리
 */

public class FilterUtil {   
	 
    
    //타이틀 문자열 자르기
    public static String strCut(String str, int length, String mode){
	   byte[] byteString = str.getBytes();
	   String return_str = "";
	   
	   if(byteString.length <= length){
	    return str;
	   }
	   else{
	    int minusByteCount = 0;
	    
	    for (int i = 0; i < length; i++){
	     minusByteCount += (byteString[i] < 0) ? 1 : 0;
	    }
	    
	    if (minusByteCount % 2 != 0){
	     length--;
	    }
	    
	    if(mode.equals("1")){
	     return_str = new String(byteString, 0, length) + "...";
	    }
	    else if(mode.equals("2")){
	     return_str = new String(byteString, 0, length);
	    }
	    else if(mode.equals("3")){
	     return_str = new String(byteString, 0, length) + ". . .";
	    }
	    
	    return return_str;
	   }
	}
     
    /* 내부와 지정한 url을 제외한 a테그 제거 */
    public static String urlchange(String schStr){
    	if(schStr!=null){
    		String[] urls = { "yumc.ac.kr","yu.ac.kr","blog.naver.com/yumcyumc1979","facebook.com/영남대학교의료원-640945022638059","youtube.com/channel/UCx16kdGNupbxJYS15HL_WhQ","twitter.com/YUMedicalCenter"};//url 추가요청시 추가
    		int urls_max=urls.length;
	    	String newstr="";
	    	String regex="<(no)?(a|A)[^>]*>.*?</(no)?(a|A)>";
		    Pattern p = Pattern.compile(regex); 
		    Matcher m = p.matcher(schStr);
		    StringBuffer sb = new StringBuffer(); 
		    while (m.find()) {
		    	String sch=m.group();
		    	String chk="";
		    	if(sch.indexOf("http://")>-1||sch.indexOf("https://")>-1){
		    		for(int s=0;s<urls_max;s++){
			    		if(sch.indexOf(urls[s])>-1){
			    			chk="y";
			    			break;
			    		}
		    		}
		    		if(chk.equals("y")){
		    		}else{
		    			sch="";
		    		}
		    	}
		    	m.appendReplacement(sb, "" + sch + ""); 
		    }
		    m.appendTail(sb);
		    newstr=sb.toString();		
		 	return newstr;
		}else{
			return "";
		}
	
    }
    /*게시판 전용 :xss + 허용하지 않는 url제거 추가*/ 
    public static String bbs_unscript(String data) { 
    	String value =unscript(data); 
    	//value =  urlchange(value); //링크 체크
    	String ret=value;
    	return ret;
    }
    /*인풋박스전용 : xss + tag 삭제*/
    public static String untagscript(String data) {
    	String str = unscript(data);
    	str= removeTag(str);
    	return str;    	
    }
    
    /*xss*/
    public static String unscript(String data) {
    	if(data == null){return "";}
    	if(data.length() == 0){return "";}
        String ret = data;
		Pattern SCRIPTS = Pattern.compile("<(no)?script[^>]*>.*?</(no)?script>", 32);
        Pattern STYLE = Pattern.compile("<style[^>]*>.*</style>", 32);
        Pattern Title = Pattern.compile("<title[^>]*>.*</title>", 32);         
        Pattern patternDocumentCookie=Pattern.compile("(?i)document\\.cookie");        
        Matcher m = SCRIPTS.matcher(ret);
        ret = m.replaceAll("");
        m = STYLE.matcher(ret);
        ret = m.replaceAll("");
        m = Title.matcher(ret);
        ret = m.replaceAll("");        
        m = patternDocumentCookie.matcher(ret);
        ret = m.replaceAll("");    
       
		ret = ret.replaceAll("<(/)?(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)([^>]+)?(/)?>", "");
		ret = ret.replaceAll("<(/)?(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)([^>]+)?(/)?>", "");
		ret = ret.replaceAll("<(/)?(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)([^>]+)?(/)?>", "");
		ret = ret.replaceAll("<(/)?(E|e)(M|m)(B|b)(E|e)(D|d)([^>]+)?(/)?>", "");
		ret = ret.replaceAll("<(/)?(F|f)(O|o)(R|r)(M|m)([^>]+)?(/)?>", ""); 
 		ret = ret.replaceAll("<(/)?(M|m)(E|e)(T|t)(A|a)([^>]+)?(/)?>", ""); 
 		ret = ret.replaceAll("<(/)?(M|m)(E|e)(T|t)(A|a)", "");		
        String value=ret;
        String ch = "onabort|onactivate|onafterprint|onafterupdate|onbeforeactivate|onbeforecopy|onbeforecut|onbeforedeactivate|onbeforeeditfocus|onbeforepaste|onbeforeprint|onbeforeunload|onbeforeupdate|onbegin|onblur|onbounce|oncellchange|onchange|onclick|oncontentready|oncontentsave|oncontextmenu|oncontrolselect|oncopy|oncut|ondataavailable|ondatasetchanged|ondatasetcomplete|ondblclick|ondeactivate|ondetach|ondocumentready|ondrag|ondragdrop|ondragend|ondragenter|ondragleave|ondragover|ondragstart|ondrop|onend|onerror|onerrorupdate|onfilterchange|onfinish|onfocus|onfocusin|onfocusout|onhelp|onhide|onkeydown|onkeypress|onkeyup|onlayoutcomplete|onload|onlosecapture|onmediacomplete|onmediaerror|onmedialoadfailed|onmousedown|onmouseenter|onmouseleave|onmousemove|onmouseout|onmouseover|onmouseup|onmousewheel|onmove|onmoveend|onmovestart|onopenstatechange|onoutofsync|onpaste|onpause|onplaystatechange|onpropertychange|onreadystatechange|onrepeat|onreset|onresize|onresizeend|onresizestart|onresume|onreverse|onrowclick|onrowenter|onrowexit|onrowout|onrowover|onrowsdelete|onrowsinserted|onsave|onscroll|onseek|onselect|onselectionchange|onselectstart|onshow|onstart|onstop|onsubmit|onsyncrestored|ontimeerror|ontrackchange|onunload|onurlflip|cookie|document|javascript|alert|script|meta|prompt|prompt|http-equiv|javascript|eval|onactive|expression|charset|applet|onafteripudate|meta|string|xml|create|blink|append|ondbclick|link|binding|alert|ondatasetchaged|msgbox|cnbeforeprint|embed|refresh|cnbeforepaste|onmouseend|object|void|iframe|onbeforeuload|onuload|frame|frameset|ilayer|layer|bgsound|base|vbscript|onbefore|onfilterchage|oncontrolselected";
        String[] ch_arr=ch.split("[|]"); 
        int	c_int=-1;
        int c_len=0;
        int ch_max=ch_arr.length;
        for(int s=0;s<ch_max;s++){
        	String[] cc_int=value.toLowerCase().split(ch_arr[s]);
        	if(cc_int.length>0){
        		for(int ti=0;ti<cc_int.length;ti++){
        			c_int=value.toLowerCase().indexOf(ch_arr[s]);
                	c_len=ch_arr[s].length();
                	if(c_int>-1 && c_len>0){
                		value=value.replaceAll(value.substring(c_int,c_int+c_len),"");
                		c_int=-1;
                		c_len=0;
                	}
                			
        		}
        	}
        }         
        ret=value;
        return ret;
    }
 public  static String removeTag(String html)  {
    	
    	html = html.replaceAll("&nbsp;"," ");
    	html = html.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
    	html = html.replaceAll("<[^>]*>", " ");
		return html;
	}
    

    public static String onlyBbsId(String data) {
    	if(data == null){return "";}
    	if(data.length() == 0){return "";}
    	Pattern  pattern  =  Pattern.compile("^[a-zA-Z0-9_]*$");
    	Matcher  match  =  pattern.matcher(data);   		
   		String ret=data;
   		if(!match.matches()){
   			ret="";
   		}
    	return ret;    	
    }
    public static String onlyalpanum(String data) {
    	if(data == null){return "";}
    	if(data.length() == 0){return "";}
    	Pattern  pattern  =  Pattern.compile("^[a-z0-9]*$");
    	Pattern  pattern2  =  Pattern.compile("^[A-Z0-9]*$");
    	Matcher  match  =  pattern.matcher(data);
   		Matcher  match2  =  pattern2.matcher(data);   		
   		String ret=data;
   		if(!match.matches()&&!match2.matches()){
   			ret="";
   		}
    	return ret;    	
    }
    public static String onlyalpa(String data) {
    	if(data == null){return "";}
    	if(data.length() == 0){return "";}
    	Pattern  pattern  =  Pattern.compile("^[a-z]*$");
    	Matcher  match  =  pattern.matcher(data);   		   		
   		String ret=data;
   		if(!match.matches()){
   			ret="";
   		}
    	return ret;    	
    }
    /**/
    public static boolean  onlyfullfilename(String orginFileName) {
    	String[] filesource =  {"jpg","png","gif","jpeg","mp4","wmv","avi","mp3","mpg","csv","xls","xlsx","ppt","pptx","doc","docx","show","cell","hwp","pdf","ozd","txt","zip"};
    	
    	int index = orginFileName.lastIndexOf(".");
	    String fileExt = orginFileName.substring(index + 1); 
	    fileExt=fileExt.toLowerCase();
	    String data=orginFileName.substring(0,index);
		Arrays.sort(filesource);	

	    int idxNone = Arrays.binarySearch( filesource, fileExt);
    	if(idxNone==-1){return false;}    	
    	if(data == null){return false;}
    	if(data.length() == 0){return false;} 
    	
    	Pattern  pattern  =  Pattern.compile("^[a-z]*$");
    	Pattern  pattern2  =  Pattern.compile("^[0-9]*$");
    	
 
		String err="";
		for(int i=0; i < data.length(); i++){
			char c = data.charAt(i);
			String cstr=""+c;
			Matcher  cmatch  =  pattern.matcher(cstr);
	    	Matcher  cmatch2  =  pattern2.matcher(cstr);
	    	if(!cstr.equals("_")&&!cmatch.matches()&&!cmatch2.matches()) {
	    		err="1";
	    		break;
	    	} 
		}
		if(err.equals("1")){
			return false;
   		}else {
   			return true;
   		}
    }
    public static String onlyfilename(String data) {
    	if(data == null){return "";}
    	if(data.length() == 0){return "";}
 
    	Pattern  pattern  =  Pattern.compile("^[a-z]*$");
    	Pattern  pattern2  =  Pattern.compile("^[0-9]*$");
    	String ret=data;
 
		String err="";
		for(int i=0; i < data.length(); i++){
			char c = data.charAt(i);
			String cstr=""+c;
			Matcher  cmatch  =  pattern.matcher(cstr);
	    	Matcher  cmatch2  =  pattern2.matcher(cstr);
	    	if(!cstr.equals("_")&&!cmatch.matches()&&!cmatch2.matches()) {
	    		err="1";
	    		break;
	    	} 
		}
		if(err.equals("1")){
			return "";
   		}else {
   			return ret;
   		}
    }
    
    public static String onlynum(String data) {
    	if(data == null){return "0";}
    	if(data.length() == 0){return "0";}
    	Pattern  pattern  =  Pattern.compile("^[0-9]*$");
    	Matcher  match  =  pattern.matcher(data);	
   		String ret=data;
   		if(!match.matches()){
   			ret="0";
   		}
    	return ret;    	
    }
   
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
    public static String descript(String data,String ty) {
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
        	
        	 
        	//end            
        	ret = ret.replaceAll("&#40;","(" );
	        ret = ret.replaceAll("&#41;",")");
        	ret = ret.replaceAll("&lt;","<");
            ret = ret.replaceAll("&gt;",">");
            ret = ret.replaceAll("&quot;","\"");
            ret = ret.replaceAll("&apos;","'");
            
            ret = ret.replaceAll("&amp;","&");
        }
        return ret;
    }
    public  static 	String  Replace_Html_Char(String shtml_char)  {
    	if(shtml_char == null){return "";}
    	if(shtml_char.length() == 0){return "";}
		String dhtml_char=null;
		StringBuffer sb = new StringBuffer();
	
		for(int i=0; i < shtml_char.length(); i++){
			char c = shtml_char.charAt(i);
			switch (c){
				case '<' : 
					sb.append("&lt;");
					break;
				case '>' : 
					sb.append("&gt;");
					break;
				case '&' :
					sb.append("&amp;");
					break;
				case '"' :
					sb.append("&quot;");
					break;
				case '\'' :
					sb.append("&apos;");
					break;
				default:
					sb.append(c);
			} 
		} 


		dhtml_char = sb.toString();
		dhtml_char = dhtml_char.replaceAll("&lt;br/&gt;","<br/>");
		return dhtml_char;
	}  
    public  static 	String  Replace_Char_Html(String shtml_char)	  {
    	if(shtml_char == null){return "";}
    	if(shtml_char.length() == 0){return "";}
		String dhtml_char=null;
		dhtml_char = shtml_char;
		dhtml_char = dhtml_char.replaceAll("&lt;","<");
		dhtml_char = dhtml_char.replaceAll("&gt;",">");		
		dhtml_char = dhtml_char.replaceAll("&quot;","\"");
		dhtml_char = dhtml_char.replaceAll("&apos;","'");			
		dhtml_char = dhtml_char.replaceAll("&amp;","&");
		
		 //Matcher.quoteReplacement(File.separator);

		
		return dhtml_char;
	}  
     
	
	/**
     * 한글 넓이는 2칸으로 적용하여 자른다.
     * @param s
     * @param len
     * @param tail
     * @return
     */
    public static String truncateWebStyle(String s, int len, String tail) 
    {    
      String str = StringUtil.parseString(s);
                        
      int width = 0;
      int i = 0;
      
      for( i=0; i < str.length() && i<len && width<=len; i++)
      {
        width += CharUtil.isHangul( str.charAt(i) ) ? 2 : 1; // 한글일 경우 width를 2를 더한다.        
      }           
      
      StringBuffer buf = new StringBuffer(str.substring(0,i));
      
      if ( str.length() > i )
      {
        buf.append(tail);    
      }
      
      return buf.toString();    
    }
         
    
    public static int lengthWebStyle(String s) 
    {    
      String str = StringUtil.parseString(s);
                        
      int width = 0;
      
      for( int i=0; i < str.length(); i++)
      {
        width += CharUtil.isHangul( str.charAt(i) ) ? 2 : 1; // 한글일 경우 width를 2를 더한다.        
      }           
            
      return width;    
    }    
    
    
    public static final String substr(Object value, int start, int end, String defaultValue)
    {
        String str = StringUtil.parseString(value, defaultValue);
        
        int strlen = str.length();
        
        if ( strlen < start )
        {
            return "";
        }
        
        if ( end > strlen )
        {
            return str.substring(start, strlen );
        }
        
        return str.substring(start, end);
    }
    
    
    public static final String substr(Object value, int start, int end)
    {
        return substr(value, start, end, "");       
    }
    
    
    public static String stripHTML(String htmlStr) {
        Pattern p = Pattern.compile("<(?:.|\\s)*?>");
        Matcher m = p.matcher(htmlStr);
        boolean matchFound = m.find();
        String newstr =  htmlStr;
        if (matchFound) {
			for (int i = 0; i <= m.groupCount(); i++) {
				newstr = newstr.replace(m.group(i),m.group(i).toLowerCase());        
			}
		}
        return newstr+"sch";
    }  

     
    /**
     * 데이터를 암호화하는 기능
     *
     * @param byte[] data 암호화할 데이터
     * @return String result 암호화된 데이터
     * @exception Exception
     */ 
    public static String encodeBinary(String str) throws Exception {
    	byte[] data=str.getBytes();
		if (data == null) {
		    return "";
		}
		String enc=new String(Base64.encodeBase64(data));
		
		return enc;
    }
 
    /**
     * 데이터를 복호화하는 기능
     *
     * @param String data 복호화할 데이터
     * @return String result 복호화된 데이터
     * @exception Exception
     */ 
    public static String decodeBinary(String data) throws Exception {
    	String d =data.trim().replace(" ", "+");
    	String dec=new String(Base64.decodeBase64(d.getBytes()));
    	
    	return dec;
    }
    public static String getText(String content)
    {
        Pattern SCRIPTS = Pattern.compile("<(no)?script[^>]*>.*?</(no)?script>", 32);
        Pattern STYLE = Pattern.compile("<style[^>]*>.*</style>", 32);
        Pattern Title = Pattern.compile("<title[^>]*>.*</title>", 32);
        Pattern TAGS = Pattern.compile("<(\"[^\"]*\"|'[^']*'|[^'\">])*>");
        Pattern ENTITY_REFS = Pattern.compile("&[^;]+;");
        Pattern WHITESPACE = Pattern.compile("\\s\\s+");
        Matcher m = SCRIPTS.matcher(content);
        content = m.replaceAll("");
        m = STYLE.matcher(content);
        content = m.replaceAll("");
        m = Title.matcher(content);
        content = m.replaceAll("");
        m = TAGS.matcher(content);
        content = m.replaceAll("");
        m = ENTITY_REFS.matcher(content);
        content = m.replaceAll("");
        m = WHITESPACE.matcher(content);
        content = m.replaceAll("");
        content = content.replace("\n", "");
        content = content.replace("\r\n", "");
        content = content.replace("{$", "{ $");
        
        content = content.replace("{$", "{ $");
        return content;
    }
    public static String getNoTag(String ret)
    {
    	ret = ret.replaceAll("&" ,"&amp;");        	
    	ret = ret.replaceAll("[(]", "&#40;");
        ret = ret.replaceAll("[)]", "&#41;");
        ret = ret.replaceAll("<", "&lt;");
        ret = ret.replaceAll(">", "&gt;");	 
        ret = ret.replaceAll("\"","&quot;");
        ret = ret.replaceAll("'", "&apos;");
        return ret;
    	
    }
    public static String getEnWTag(String ret)
    {
    	ret = ret.replaceAll("&" ,"&amp;");        	
    	ret = ret.replaceAll("[(]", "&#40;");
        ret = ret.replaceAll("[)]", "&#41;");
        ret = ret.replaceAll("<", "&lt;");
        ret = ret.replaceAll(">", "&gt;");	 
        ret = ret.replaceAll("\"","&quot;");
        ret = ret.replaceAll("'", "&apos;");
        return ret;
    	
    }
    public static String getDeWTag(String ret)
    {
    	ret = ret.replaceAll("&amp;","&");        	
    	ret = ret.replaceAll( "&#40;","(");
        ret = ret.replaceAll( "&#41;",")");
        ret = ret.replaceAll("&lt;","<");
        ret = ret.replaceAll("&gt;",">");	 
        ret = ret.replaceAll("&quot;","\"");
        ret = ret.replaceAll("&apos;","'" );
        return ret;
    	
    }
    public static String setReplaceAll(String ret,String to)
    {
    	if ( ret == null) {
    		ret = "";
    	}
    	String[] arr=ret.split("[|]");    	
    	int cnt=arr.length;
    	String str="";
    	int k=0;
    	for(int s=0;s<cnt;s++){
    		String st=arr[s];
    		if(st!=null&&!st.equals("")){
    			st=st.trim();    		
	    		if(!st.equals("")){
	    			if(k>0){str=str+to;	}
	    			str=str+st;
	    			k++;
	    		}
    		}    		
    	}
        return str;
    	
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
	/* 비밀번호 체크 메세지 리턴값이 "" 일경우만 정상 , 그외는 메세지 임 */   	
	public static String pwchkmsg(String str){ 
			String chk="";
			String msg1="비밀번호는 9~20자리 만 사용 가능합니다.";
			String msg2="비밀번호는 영문소문자로 시작하여야 합니다.";
			String msg3="비밀번호는 영문소문자, 숫자,특수문자(!,@,#,$,%,^,&,*) 만 사용 가능합니다.";
			String msg4="비밀번호는 3자 이상 중복 사용할 수 없습니다.";
			String msg5="비밀번호는 4자 이상 연속 사용할 수 없습니다.";
			if(str.length()<9||str.length()>20){
				chk=msg1;
			}else{
	    		Pattern p0 = Pattern.compile("[a-z]");//영숫자
	    		Matcher m0 = p0.matcher(str.substring(0,1)); 
	    		boolean ch0=m0.find();
	    		if(ch0==false){
	    			chk=msg2;	    			
	    		}else{
	    			String err3 = strpwchk(str);
	    			if(err3.equals("y")){	       				
	    				chk=msg3;
		    		}else{
		    			String err4 = strsamechk(str);
		       			if(err4.equals("y")){
		       				chk=msg4;
		       			}else{
		       				String err5 = strstreakchk(str);       				
		       				if(err5.equals("y")){
		           				chk=msg5;
		           			}
		       			}
		    		}
	    		}
	    	}	    			 
	    	return chk;    	 
	    }
    public static String strEncode(String outBuffer){
		String data = "";
		if(outBuffer != null){
			data = outBuffer.toString();
			if(data!=null &&!data.equals("")){
				try {
					String all_str="";
					String[] arr=data.split("&");
					int t=0;
					int mx=arr.length;
					if(mx>0){
						for(int s=0;s<arr.length;s++){
							if(arr[s]!=null&&!arr[s].equals("")){
								String st=arr[s].trim();
								String[] arr2=st.split("=");
								if(t>0){
									all_str=all_str+"&";
								}
								if(arr2.length==2){							
									all_str=all_str+arr2[0]+"="+URLEncoder.encode(arr2[1], "utf-8");
									t++;
								}						
							}					
						}
					}
					if(data.substring(0,1).equals("&")){
						data="&"+all_str;
					}else{
						data=all_str;
					}
				} catch (Exception e) {
					//e.printStackTrace();
				}
			}
		}
		return data;
	}
    
    
    public static String clearXSSMinimum(String value) {
		if (value == null || value.trim().equals("")) {
			return "";
		}

		String returnValue = value;

		returnValue = returnValue.replaceAll("&", "&amp;");
		returnValue = returnValue.replaceAll("<", "&lt;");
		returnValue = returnValue.replaceAll(">", "&gt;");
		returnValue = returnValue.replaceAll("\"", "&#34;");
		returnValue = returnValue.replaceAll("\'", "&#39;");
		returnValue = returnValue.replaceAll("\\.", "&#46;");
		returnValue = returnValue.replaceAll("%2E", "&#46;");
		returnValue = returnValue.replaceAll("%2F", "&#47;");
		return returnValue;
	}

	public static String clearXSSMaximum(String value) {
		String returnValue = value;
		returnValue = clearXSSMinimum(returnValue);

		returnValue = returnValue.replaceAll("%00", null);

		returnValue = returnValue.replaceAll("%", "&#37;");

		// \\. => .

		returnValue = returnValue.replaceAll("\\.\\./", ""); // ../
		returnValue = returnValue.replaceAll("\\.\\.\\\\", ""); // ..\
		returnValue = returnValue.replaceAll("\\./", ""); // ./
		returnValue = returnValue.replaceAll("%2F", "");

		return returnValue;
	}

	public static String filePathBlackList(String value) {
		String returnValue = value;
		if (returnValue == null || returnValue.trim().equals("")) {
			return "";
		}

		returnValue = returnValue.replaceAll("\\.\\.", "");

		return returnValue;
	}

	/**
	 * 행안부 보안취약점 점검 조치 방안.
	 *
	 * @param value
	 * @return
	 */
	public static String filePathReplaceAll(String value) {
		String returnValue = value;
		if (returnValue == null || returnValue.trim().equals("")) {
			return "";
		}

		returnValue = returnValue.replaceAll("/", "");
		returnValue = returnValue.replaceAll("\\", "");
		returnValue = returnValue.replaceAll("\\.\\.", ""); // ..
		returnValue = returnValue.replaceAll("&", "");

		return returnValue;
	}
	
	public static String fileInjectPathReplaceAll(String value) {
		String returnValue = value;
		if (returnValue == null || returnValue.trim().equals("")) {
			return "";
		}

		
		returnValue = returnValue.replaceAll("/", "");
		returnValue = returnValue.replaceAll("\\..", ""); // ..
		returnValue = returnValue.replaceAll("\\\\", "");// \
		returnValue = returnValue.replaceAll("&", "");

		return returnValue;
	}

	public static String filePathWhiteList(String value) {
		return value;
	}

	public static boolean isIPAddress(String str) {
		Pattern ipPattern = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");

		return ipPattern.matcher(str).matches();
    }

	public static String removeCRLF(String parameter) {
		return parameter.replaceAll("\r", "").replaceAll("\n", "");
	}

	public static String removeSQLInjectionRisk(String parameter) {
		return parameter.replaceAll("\\p{Space}", "").replaceAll("\\*", "").replaceAll("%", "").replaceAll(";", "").replaceAll("-", "").replaceAll("\\+", "").replaceAll(",", "");
	}

	public static String removeOSCmdRisk(String parameter) {
		return parameter.replaceAll("\\p{Space}", "").replaceAll("\\*", "").replaceAll("|", "").replaceAll(";", "");
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
	  public static final String setDecrypt(String str) {
	 		AES256Cipher a256 = new AES256Cipher();
	 		String my="";
	 		try {
	 			my = a256.decrypt(str);
	 		} catch (NoSuchAlgorithmException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		} catch (UnsupportedEncodingException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		} catch (GeneralSecurityException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		}
	     	return  my;
	     } 
	    
}
