package com.chan.demo.common;
 
import java.util.regex.Matcher;
import java.util.regex.Pattern; 

import java.math.BigDecimal;
import java.security.SecureRandom; 
 
/**
 * 스트링 관련 처리
 */

public class StringUtil {
    
	 
	
    
    public static final String parseString(Object value, String defaultValue) {
        
        
        if ( value==null || value.toString().trim().equals(""))
            return defaultValue;
        
                          
        return value.toString();        
        
    }
    
    
    
    public static final String parseString(Object value) {
        
        return parseString(value, "");
    }
    
    
    /*
     * Object를 int로 변환한다. null이거나 parsing에러시 파라미터로 넘겨진 디폴트 값을 리턴한다.
     */
    public static final int parseInt(Object value, int defaultNumber) {
        
        int result = defaultNumber;
        
        if ( value==null || value.equals(""))
            return defaultNumber;
        
        try {
            result = Integer.parseInt( value.toString() );            
        }
        catch ( Exception e ) {
            
            return defaultNumber;
        }
        
        
        return result;
    }
        

    
    public static final int parseInt(Object value) {
        
        return parseInt(value, 0);
    }
    
    /*
     * 숫자체크. 숫자이면 true 아니면 false를 리턴
     */
    public static final boolean chkInt(Object value) {    
        
    	boolean result = false;
        
        if ( value==null || value.equals(""))
            return result;
        
        try {
        	String val = value.toString();
        	char cM[]=new char[val.length()]; //
        	val.getChars(0,val.length()-1,cM,0);
    		//숫자인지체크
        	
            Pattern p1 = Pattern.compile("\\d");
            Matcher m1 = p1.matcher(val);            
            int tot=0;
			while(m1.find()){
				tot++;
			}
			if(val.length()==tot){
				result=true;
			}   
            
        }
        catch ( Exception e ) {
            
            return result;
        }
        
        return result;
    }

    /*
     * 숫자체크. 숫자이면 true 아니면 false를 리턴
     */
    public static final boolean chklong(Object value) {    
        
    	boolean result = false;
        
        if ( value==null || value.equals(""))
            return result;
        
        try {
        	String val = value.toString();
        	int lens=val.length()-1;
        	String my=val.substring(lens-1,lens);
        	
        	char cM[]=new char[lens]; //
        	val.getChars(0,lens-1,cM,0);
    		//숫자인지체크
        	
            Pattern p1 = Pattern.compile("\\d");
            Matcher m1 = p1.matcher(val);            
            int tot=0;
			while(m1.find()){
				tot++;
			}
			if(lens==tot && my.equals("L")){
				result=true;
			}   
            
        }
        catch ( Exception e ) {
            
            return result;
        }
        
        return result;
    }
    /*
     * Object를 int로 변환한다. null이거나 parsing에러시 파라미터로 넘겨진 디폴트 값을 리턴한다.
     */
    public static final float parseFloat(Object value, float defaultNumber) {
        
        float result = defaultNumber;
        
        if ( value==null || value.equals(""))
            return defaultNumber;
        
        try {
            result = Float.parseFloat( value.toString() );            
        }
        catch ( Exception e ) {
            
            return defaultNumber;
        }
        
        
        return result;
    }    
    

    public static final float parseFloat(Object value) {
        
        return parseFloat(value, 0);
    }
    
    
    /*
     * Object를 int로 변환한다. null이거나 parsing에러시 파라미터로 넘겨진 디폴트 값을 리턴한다.
     */
    public static final double parseDouble(Object value, float defaultNumber) {
        
        double result = defaultNumber;
        
        if ( value==null || value.equals(""))
            return defaultNumber;
        
        try {
            result = Double.parseDouble( value.toString() );            
        }
        catch ( Exception e ) {
            
            return defaultNumber;
        }
        
        
        return result;
    }    
    

    public static final double parseDouble(Object value) {
        
        return parseDouble(value, 0);
    }    
    
    
    
    /*
     * Object를 int로 변환한다. null이거나 parsing에러시 파라미터로 넘겨진 디폴트 값을 리턴한다.
     */
    public static final boolean parseBoolean(Object value, boolean defaultBool) {
        
        boolean result = defaultBool;
        
        if ( value==null || value.equals(""))
            return defaultBool;
        
        try {
            result = Boolean.valueOf( value.toString() ).booleanValue();            
        }
        catch ( Exception e ) {
            
            return defaultBool;
        }
        
        
        return result;
    }        
    
    
    
    
    
    public static final boolean parseBoolean(Object value) {
        
        return parseBoolean(value, false);
    }
    
    
   
        
   
    
    
    
    /**
     * 문자를 숫자로 변환
     * 설문조사 숫자포맷에만 한정적으로 이용되므로 20까지만 정의하였다.
     * @param str
     * @return
     */
    public static int parseCharInt(String ch) {
        
        if ( ch.length() != 1)
            return 0;

        final String[] charArr    = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i","j","k","l","m","n" };   
        
        for( int i=0; i<=20; i++) {
            
            if ( ch.equalsIgnoreCase( charArr[i]) )
                return i;
        }
        
        return 0;
    }
    
    
    public static final String parseKrString(Object value, String defaultValue)
    {                
        String str = null; 
            
        try
        {
            str = new String(parseString(value, defaultValue).getBytes("ISO-8859-1"),"EUC-KR");
        }
        catch ( Exception e)
        {
            str = defaultValue;
        }
        
        return str ;        
    }
    
    public static final String parseKrString(Object value)
    {
        return parseKrString(value, "");
    }    
    
    
    /**
	 * 객체가 null인지 확인하고 null인 경우 "" 로 바꾸는 메서드
	 * @param object 원본 객체
	 * @return resultVal 문자열
	 */
	public static String isNullToString(Object object) {
		String string = "";

		if (object != null) {
			string = object.toString().trim();
		}

		return string;
	}
	/**
	 *<pre>
	 * 인자로 받은 String이 null일 경우 &quot;&quot;로 리턴한다.
	 * &#064;param src null값일 가능성이 있는 String 값.
	 * &#064;return 만약 String이 null 값일 경우 &quot;&quot;로 바꾼 String 값.
	 *</pre>
	 */
	public static String nullConvert(Object src) {
		//if (src != null && src.getClass().getName().equals("java.math.BigDecimal")) {
		if (src != null && src instanceof java.math.BigDecimal) {
			return ((BigDecimal) src).toString();
		}

		if (src == null || src.equals("null")) {
			return "";
		} else {
			return ((String) src).trim();
		}
	}
	
	/**
	 *<pre>
	 * 인자로 받은 String이 null일 경우 &quot;&quot;로 리턴한다.
	 * &#064;param src null값일 가능성이 있는 String 값.
	 * &#064;return 만약 String이 null 값일 경우 &quot;&quot;로 바꾼 String 값.
	 *</pre>
	 */
	public static String nullConvertInt(Object src) {
		//if (src != null && src.getClass().getName().equals("java.math.BigDecimal")) {
		if (src != null && src instanceof java.math.BigDecimal) {
			return ((BigDecimal) src).toString();
		}

		if (src == null || src.equals("null")) {
			return "0";
		} else {
			return ((String) src).trim();
		}
	}



	public static int getRandomNum(int startNum, int endNum) {
	int randomNum = 0;
	
	try {
	    // 랜덤 객체 생성
	    SecureRandom rnd = new SecureRandom();
	
	    do {
		// 종료숫자내에서 랜덤 숫자를 발생시킨다.
		randomNum = rnd.nextInt(endNum + 1);
	    } while (randomNum < startNum); // 랜덤 숫자가 시작숫자보다 작을경우 다시 랜덤숫자를 발생시킨다.
	} catch (Exception e) {
	    //e.printStackTrace();
	    throw new RuntimeException(e);	// 2011.10.10 보안점검 후속조치
	}
	
	return randomNum;
	}



	public static String getRandomStr(char startChr, char endChr) {
	
	int randomInt;
	String randomStr = null;
	
	// 시작문자 및 종료문자를 아스키숫자로 변환한다.
	int startInt = Integer.valueOf(startChr);
	int endInt = Integer.valueOf(endChr);
	
	// 시작문자열이 종료문자열보가 클경우
	if (startInt > endInt) {
	    throw new IllegalArgumentException("Start String: " + startChr + " End String: " + endChr);
	}
	
	try {
	    // 랜덤 객체 생성
	    SecureRandom rnd = new SecureRandom();
	
	    do {
		// 시작문자 및 종료문자 중에서 랜덤 숫자를 발생시킨다.
		randomInt = rnd.nextInt(endInt + 1);
	    } while (randomInt < startInt); // 입력받은 문자 'A'(65)보다 작으면 다시 랜덤 숫자 발생.
	
	    // 랜덤 숫자를 문자로 변환 후 스트링으로 다시 변환
	    randomStr = (char)randomInt + "";
	} catch (Exception e) {
	    //e.printStackTrace();
	    throw new RuntimeException(e);	// 2011.10.10 보안점검 후속조치
	}
	
	// 랜덤문자열를 리턴
	return randomStr;
	}
}
