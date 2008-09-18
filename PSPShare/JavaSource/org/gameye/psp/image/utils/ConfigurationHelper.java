
package org.gameye.psp.image.utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationFactory;


/**
 * 此类为工具类，主要为读取配置文件的信息
 * @author Majian
 */
public class ConfigurationHelper {

	/**
	 * @roseuid 44EA60F200B6
	 */
	public ConfigurationHelper(String xmlFilePath) {

		ConfigurationFactory factory = new ConfigurationFactory();
		factory.setConfigurationFileName( xmlFilePath);
		try {
			config = factory.getConfiguration();
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static String getConfigInfo (String infoLabel) {
		return config.getString(infoLabel);
   }

	private static Configuration config = null;


	/**
	 *  得到配置文件中日志备份的路径
	 * 
	 * @return String
	 * @roseuid 44E136F3009F
	 */
	public static String getBackupLogFilePath() {
		return config.getString("backupLogFilePath");
	}

	/**
	 *  得到邮件地址ַ
	 * 
	 * @return String
	 * @roseuid 44E919D70116
	 */
	public static String getMailHost() {
		return config.getString("mailHost");
	}

	/**
	 *得到邮件帐户
	 * 
	 * @return String
	 * @roseuid 44E919E70396
	 */
	public static String getMailUser() {
		return config.getString("mailUser");
	}

	/**
	 * 得到邮件密码
	 * 
	 * @return String
	 * @roseuid 44E919F7024D
	 */
	public static String getMailPwd() {
		return config.getString("mailPwd");
	}

	/**
	 *得到邮件附件根路径
	 */
	public static String getMailUploadFilePath() {
		return config.getString("mailUploadFilePath");
	}

	/**
	 * 应用系统标识
	 */
	public static String getSystemId() {
		
		return config.getString("SYSTEMID");
	}



	/**
	 * 获取命名JDBC SQL
	 * 
	 * @param queryName
	 *            -命名的sql名称
	 * @return JDBC SQL
	 */
	public static String getNamedSqlQuery(String queryName) {
		if (namedSqlQueriesMap == null) {
			namedSqlQueriesMap = new HashMap();			
			List names = config.getList("namedSqlQueries.sqlQuery.name");
			List addresses = config
					.getList("namedSqlQueries.sqlQuery.sqlString");
			for (int i = 0; i < names.size(); i++) {
				namedSqlQueriesMap.put(names.get(i), addresses.get(i));
			}
		}

		if (namedSqlQueriesMap.containsKey(queryName)) {
			String sql = (String)namedSqlQueriesMap.get(queryName);			
			return sql;
		} else {
			return null;
		}
	}
	
	/**
	 * 得到dto对象的包结构
	 * */
	public static String getDtoPackage(){
		return config.getString("dtoPackage");
	}
	
	/**
	 * 得到数据库类型
	 * */
	public static String getDbType(){
		return config.getString("dbType");
	}
	/**
	 * 得到pojo的包结构
	 * */
	public static String getPoPackage(){
		return config.getString("poPackage");
	}
	private static Map wsAddressMap = null;

	private static Map namedSqlQueriesMap = null;

	
	/**
	 * 得到所有列表的[配置文件
	 * @return
	 */
	public static File[] getGridFile(){
		String path = config.getString("gridFile");
	    File[] files = FileHelper.getfiles(path);
		return files;
	}

	/**
	 * 得到初始化企业账户角色组
	 * @return
	 */
	public static String getRoleForComp(){
		String roles = config.getString("evalRoleForComp");
		return roles;
	}
	
	/**
	 * 得到初始化企业子账户角色组
	 * @return
	 */
	public static String getRoleForPerson(){
		String roles = config.getString("evalRoleForPerson");
		return roles;
	}
	
	public static String getManager(){
		String manager = config.getString("manager");
		return manager;
	}
	
	/**
	 * 得到登陆session
	 * @return
	 */
	public static String getLoginSession(){
		String seObj = config.getString("sessionObj");
		return seObj;
	}
	
	/**
	 * 获得不允许注册的站点域名
	 */
	public static String[] getForbiddenDomains() {
		return StringHelper.replaceBlank(config.getString("domain-forbidden").replace("|", ",")).split(",");		
	}
}
