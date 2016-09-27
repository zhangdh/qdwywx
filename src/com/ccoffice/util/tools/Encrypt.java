package com.ccoffice.util.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {
	 public static String SHA1(String inStr) {
	        MessageDigest md = null;
	        String outStr = null;
	        try {
	            md = MessageDigest.getInstance("SHA-1");     //选择SHA-1，也可以选择MD5
	            byte[] digest = md.digest(inStr.getBytes());       //返回的是byet[]，要转化为String存储比较方便
	            outStr = bytetoString(digest);
	        }
	        catch (NoSuchAlgorithmException nsae) {
	            nsae.printStackTrace();
	        }
	        return outStr;
	    }
	 public static String bytetoString(byte[] digest) {
	        String str = "";
	        String tempStr = "";	        
	        for (int i = 1; i < digest.length; i++) {
	            tempStr = (Integer.toHexString(digest[i] & 0xff));
	            if (tempStr.length() == 1) {
	                str = str + "0" + tempStr;
	            }
	            else {
	                str = str + tempStr;
	            }
	        }
	        return str.toLowerCase();
	  }
	 public static String MD5(String s){
		 char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd','e', 'f'};
		  try {
		    byte[] strTemp = s.getBytes();
		    MessageDigest mdTemp = MessageDigest.getInstance("MD5");
		    mdTemp.update(strTemp);
		    byte[] md = mdTemp.digest();
		    int j = md.length;
		    char str[] = new char[j * 2];
		    int k = 0;
		    for (int i = 0; i < j; i++) {
		      byte byte0 = md[i];
		      str[k++] = hexDigits[byte0 >>> 4 & 0xf];
		      str[k++] = hexDigits[byte0 & 0xf];
		    }
		    return new String(str);
		  }
		  catch (Exception e) {
		    return null;
		  }
	 }
}
