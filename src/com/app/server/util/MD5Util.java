package com.app.server.util;

import java.security.MessageDigest;

public class MD5Util {
	 /** 
     * 字符MD5加密 
     * @param str  需要加密的字符 
     * @return 
     */  
    public static String stringToMD5(String str)  
    {  
        try {  
            byte[] strTemp=str.getBytes();  
            MessageDigest md = MessageDigest.getInstance("MD5");  
            md.update(strTemp);  
            return toHexString(md.digest());  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
    /** 
     * 加密 
     * @param md  
     * @return 返回加密后字符 
     */  
    private static String toHexString(byte[] md)  
    {  
        char[] hexDigits={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f',};  
        int j = hexDigits.length;  
        char[] str = new char[j*2];  
        for (int i = 0; i < j; i++) {  
            byte byteo = md[i];  
            str[2*i]=hexDigits[byteo>>>4 & 0xf];  
            str[2*i+1]=hexDigits[byteo & 0xf];  
        }  
        return new String(str);  
    }  
    
    public static void main(String[] args) {  
        String name="1231231231aaaa23";  
        System.out.println("加密前："+name);  
        String MD5_name = MD5Util.stringToMD5(name);  
        System.out.println("加密后: "+MD5_name);  
        System.out.println("加密后: "+MD5_name.length());  
    }  
}
