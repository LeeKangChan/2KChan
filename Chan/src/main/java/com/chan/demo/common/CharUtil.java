package com.chan.demo.common;

public final class CharUtil 
{
    
    
    /// 한글을 포함하고 있는지?
    public static boolean containsHangul(String str)
    {
        char c;
        
        for ( int i=0; i<str.length(); i++)
        {
            c = str.charAt(i);
            
            if ( isHangul(c) )
            {
                return true;
            }
        }
        
        return false;
    }    
    
    
    /// 한글을 몇글자 포함하고 있는지?
    public static int howManyHangul(String str)
    {
        char c;
        int count = 0;
        
        for ( int i=0; i<str.length(); i++)
        {
            c = str.charAt(i);
            
            if ( isHangul(c) )
            {
                count++;
            }
        }
        
        return count;     
    }
    
    
    ///  한글인가? 
    public static boolean isHangul(char c) 
    { 
        return isHangulSyllables(c) || isHangulJamo(c) || isHangulCompatibilityJamo(c); 
    } 
    
    ///  완성된 한글인가? 참조: http://www.unicode.org/charts/PDF/UAC00.pdf
    public static boolean isHangulSyllables(char c) 
    { 
        //      return (c >= (char) 0xAC00 && c <= (char) 0xD7AF); 
        return (c >= (char) 0xAC00 && c <= (char) 0xD7A3); 
    } 
    
    ///  (현대 및 고어) 한글 자모? 참조: http://www.unicode.org/charts/PDF/U1100.pdf
    public static boolean isHangulJamo(char c) 
    { 
        // return (c >= (char) 0x1100 && c <= (char) 0x11FF); 
        return (c >= (char) 0x1100 && c <= (char) 0x1159) 
        || (c >= (char) 0x1161 && c <= (char) 0x11A2) 
        || (c >= (char) 0x11A8 && c <= (char) 0x11F9); 
    } 
    
    ///  (현대 및 고어) 한글 자모? 참조: http://www.unicode.org/charts/PDF/U3130.pdf
    public static boolean isHangulCompatibilityJamo(char c) 
    { 
        // return (c >= (char) 0x3130 && c <= (char) 0x318F); 
        return (c >= (char) 0x3131 && c <= (char) 0x318E); 
    }
    
    public static String replaseStr(String str) {
    	
    	str = str.replace("'", "");
    	str = str.replace(";", "");
    	str = str.replace("$", "");
    	str = str.replace("#", "");
    	
    	return str;
    }
    
    
    private CharUtil() { /** immutable class **/ }
} 
