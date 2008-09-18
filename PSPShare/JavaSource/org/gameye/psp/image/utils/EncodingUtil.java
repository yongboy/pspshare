/*
 * Created on 2005-10-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.gameye.psp.image.utils;

import java.security.MessageDigest;

/**
 * @author Majian
 *
 */
public class EncodingUtil 
{
	public static String encodingFromTo(String temp, 
			                            String fromEndoing, 
										String toEncoding)throws Exception{
		String des = new String(temp.getBytes(fromEndoing),toEncoding);
		return des;
	}

	public static String iso88591ToUtf8(String temp)throws Exception{
		String des= new String(temp.getBytes("ISO-8859-1"),"UTF-8");
		
		return des;
		
	}
	
	public static String Utf8Toiso88591(String temp)throws Exception{
		String des= new String(temp.getBytes("UTF-8"),"ISO-8859-1");		
		return des;
		
	}
	
	public static String GbkToUTF(String temp)throws Exception{
		String des= new String(temp.getBytes("GBK"),"UTF-8");		
		return des;
	}
	
	public static String GbkToISO8859(String temp)throws Exception{
		String des= new String(temp.getBytes("GBK"),"ISO-8859-1");		
		return des;
	}
	
	public static String ISO8859ToGbk(String temp)throws Exception{
		String des= new String(temp.getBytes("ISO-8859-1"),"GBK");		
		return des;
	}
	
	public static String ISO8859ToGB2312(String temp)throws Exception{
		String des= new String(temp.getBytes("ISO-8859-1"),"GB2312");		
		return des;
	}
	
	public static String GB2312ToISO8859(String temp)throws Exception{
		String des= new String(temp.getBytes("GB2312"),"ISO-8859-1");		
		return des;
	}
	/*******************************************************
	 * 利用byte2hexString的简单MD5应用
	 * @param sourceString
	 * @return
	 *******************************************************/
	public static String MD5Encode(String sourceString) 
	{     
		String resultString = null;     
		try 
		{        
			resultString=new String(sourceString);        
			MessageDigest md = MessageDigest.getInstance("MD5");        
			resultString=byte2hexString(md.digest(resultString.getBytes()));     
		}
		catch (Exception ex) 
		{     
			ex.printStackTrace();
		}     
		return resultString;  
	}   

	public static final String byte2hexString(byte[] bytes) 
	{   
		StringBuffer buf = new StringBuffer(bytes.length * 2);   
		for (int i = 0; i < bytes.length; i++) 
		{      
			if ( ((int) bytes[i] & 0xff) < 0x10) 
			{         
				buf.append("0");      
			}      
			buf.append(Long.toString((int) bytes[i] & 0xff, 16));   
		}   
		return buf.toString();
	}

	public static void main(String[] args) throws Exception 
	{   
		System.out.println(MD5Encode("abc"));     
		System.out.println(MD5Encode("hello"));     
		System.out.println(MD5Encode("123456"));     
		System.out.println(MD5Encode("你好Blog"));
	}
	/******************************************************
	 * 结果：
	 *	900150983cd24fb0d6963f7d28e17f72
	 *	5d41402abc4b2a76b9719d911017c592
	 *	e10adc3949ba59abbe56e057f20f883e
	 *	2fc0228c7266d61d184dcf2a6b3b81d3
	 *
	 *****************************************************/
	
}
