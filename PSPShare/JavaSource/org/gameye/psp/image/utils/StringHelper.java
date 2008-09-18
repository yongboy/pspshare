/*
 * Created on 2005-10-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.gameye.psp.image.utils;

import java.security.MessageDigest;

import org.apache.commons.lang.StringUtils;

/**
 * @author Majian
 *
 */
public class StringHelper 
{
	/**   
     *   用来判断邮箱是否合法   
     *   @param   email   String   
     *   @return   boolean   
     */   
   public static boolean VerifyEmail(String email){   
       //String   patten   =   "^([a-zA-Z0-9_-]){3,}@([a-zA-Z0-9_-]){3,}(.([a-zA-Z0-9]){2,4}){1,2}$";   
	   String patten = "^(([0-9a-zA-Z]+)|([0-9a-zA-Z]+[_.0-9a-zA-Z-]*[0-9a-zA-Z]+))@([a-zA-Z0-9-]+[.])+([a-zA-Z]{2}|net|NET|com|COM|gov|GOV|mil|MIL|org|ORG|edu|EDU|int|INT)$"; 
	   if(email.length()!=0)   
           if(!email.matches(patten)){   
             return   false;   
         }   
     return   true;   
   }
   
   public static boolean VerifyUserName(String name){
	   String patten = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$";
	   String pattenNum = "^[1-9]\\d*$";
	   if(StringUtils.isNotEmpty(name)){
		   if(name.length() > 0){
			   if(!name.matches(pattenNum)){ 
				   if(!StringHelper.VerifyEmail(name)){
					   if(!name.matches(patten)){
						   return false;
					   }
				   }
			   }
		   }
	   }
	   return true;
   }
   
   /**
	 * 验证传入字符串是否全为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str){
		boolean sign = false;
		if(StringUtils.isNotEmpty(str)){
			sign = str.matches("\\d+");
		}
		return sign;
	}
	
	/**
	 * 为字符串中元素加上单引号
	 * @param str
	 * @param sign
	 * @return
	 */
	public static String getInSqlStr(String str, String sign){
		String inStr = "";
		if(StringUtils.isNotEmpty(str)){
			if(StringUtils.isEmpty(sign)){
				sign = ",";
			}
			inStr = str.replace(sign, "'"+sign+"'");
			inStr = "'"+inStr+"'";
		}
		return inStr;
	}
	
	public static String replaceBlank(String input){
		input = input.replaceAll("\n", " ");
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < input.length(); i++) {
			if (StringUtils.isNotEmpty(String.valueOf(input.charAt(i)).trim())) {
				str.append(input.charAt(i));
			}
		}
		return str.toString();
	}
	
	public static void main(String[] args){
		String str = "a,b,c,d";
		String sign = "www|epcool|feipy|domain|		mail|bug|cnn|java|com|msn|wap|		net";
		System.out.println(replaceBlank(sign));
		//System.out.println(StringHelper.getInSqlStr(str, sign));
		//String name="`-=\[];',./<>?:{}|+_)(*&^%$#@!~";
		//System.out.println(StringHelper.VerifyUserName(name));
	}
		
}
