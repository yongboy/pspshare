
package org.gameye.psp.image.utils;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;


/**
 * 
 * @author Majian
 *
 */
public class ExceptionCacheHelper 
{
   
   /**
    * 
    */
   private static java.util.Map exceptionMap = null;
   
   /**
    * 
    */
   public  ExceptionCacheHelper(String xmlFilePath) 
   {
	   this.xmlFilePath =  xmlFilePath;
	   this.parseXml();
   }
   
   /**
	 * @param xmlFilePath String xmlFilePath
	 */
	private String xmlFilePath ;
	
	
	
	
	private static XMLConfiguration config = null;

	private void parseXml(){
		config = new XMLConfiguration();
		config.setFileName(xmlFilePath);
		try {
			config.load();
			setExceptionMap();
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
   private void setExceptionMap() {
		// TODO Auto-generated method stub
	   if( exceptionMap == null ){
		   exceptionMap = new HashMap();
			List codes = config.getList("exception.code");
			List msgs = config.getList("exception.msg");
			for(int i=0; i<codes.size(); i++){
				exceptionMap.put(codes.get(i) , msgs.get(i));
			}
		}
	}
/**
    * 
    * @param errorcode 
    * @return String
    * @roseuid 44E1695E0077
    */
   public static String getMessage(String errorcode) 
   {
	   if( exceptionMap.containsKey(errorcode)){
		   return (String)exceptionMap.get(errorcode);
	   }else{
		   return "";
	   }
   }
   
   
}
