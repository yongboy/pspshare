package org.gameye.psp.image.utils; 

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Filename:	Configuration.java
 * Description: 
 * Copyright:   Copyright (c)2008
 * Company:     feipy
 * @author:     yz.Wu
 * @version:    1.0
 * Create on:   2008-7-5 下午01:44:30
 *
 * Modification History:
 * Date			Author		Version		Description
 * ------------------------------------------------------------------
 * 2008-7-5	    yz.Wu		1.0			Initialize Version.
 */
public class Configuration {
	private Properties properties;
	private final static Configuration cfg = new Configuration();
	private final static String configFile = "/spring/spring.properties";
	private Map propertiesMap = new HashMap();
    private long configFileLastModifyTime = 0L;
    
    private void init() {
        properties = new Properties();
        InputStream is = null;
        try {
            is = getClass().getResourceAsStream(configFile);
            properties.load(is);
            propertiesMap.putAll(properties);
        } catch (Exception exception) {
            System.out.println("Can't read the properties file. ");
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException exception) {
                // ignored
            }
        }        
    }
    
    public static void reset() {
        cfg.init();
    }
    
	/**
	 * Use singleton pattern, only return one instance of Configuration.
	 * @return Configuration
	 */
	public static Configuration getInstance() {
		return cfg;
	}
	
	/**
	 * Retun a value for certain key.
	 * @param key a certain key define in properties file.
	 * @return value
	 */
	public String getValue(String key) {
	    checkLoad();
		return properties.getProperty(key);
	}

	public String getValue(String key, String defaultValue) {
	    checkLoad();
		String value = properties.getProperty(key);
		if (value == null)
			value = defaultValue;
		return value;
	}

	public boolean getValue(String key, boolean defaultValue) {
		String str = getValue(key, "false");
		boolean value = str.equalsIgnoreCase("true");
		return value;
	}

    /**
     * 检查配置文件修改时间，动态装载配置文件内容
     */
	private void checkLoad() { 
        try {
	        File cf = new File(getClass().getResource(configFile).toString());
	        if(configFileLastModifyTime != cf.lastModified()) {
	            init();
	        }
	        configFileLastModifyTime = cf.lastModified();
        } catch(Exception e) {
            e.printStackTrace();
        }
        if(properties == null) {
            init();
        }
    }

	/**
	 * 获取配置文件内容map
	 * @return
	 */
	public Map getPropertiesMap() {
		checkLoad();
		return propertiesMap;
	}
}
 