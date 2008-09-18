package org.gameye.psp.image.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;
import net.sf.dozer.util.mapping.DozerBeanMapper;
import net.sf.dozer.util.mapping.MapperIF;
import com.feipy.framework.web.servlet.SqlDateConverter;
import com.feipy.framework.service.CommonConstants;
import com.feipy.framework.service.log.CommonLogFactory;
import com.feipy.framework.service.log.ILog;

/**
 * 提供处理JavaBean的方法，填充属性等。
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author Majian
 * @version 1.0
 */
public class BeanHelper {
	private static ILog log=CommonLogFactory.getLog(CommonConstants.LOGGER_BUSINESS);
	
	private BeanHelper() {
    }
	
    static{
        ConvertUtils.register(new SqlTimestampConverter(null), java.sql.Timestamp.class);
        ConvertUtils.register(new SqlDateConverter(null), java.sql.Date.class);
    }

    public static void copyBeanProperties(Object destObj, Object oriObj) {
        if(destObj==null||oriObj==null) return;
        try {
           
				
        	TBeanUtils.copyProperties(destObj,oriObj);

        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    public static void copyBeansProperties(Collection destObjs,
                                           Collection oriObjs) {
        if (destObjs.size() != oriObjs.size()) {
            throw new IllegalStateException(
                    "destObjs size  <> oriObjs size!");
        }
        for (int i = 0; i < destObjs.size(); i++) {
            Object destObj = ((List) destObjs).get(i);
            Object oriObj = ((List) oriObjs).get(i);
            copyBeanProperties(destObj, oriObj);
        }
    }

    /**
     * 根据原始实例构造新实例，填充所有属性。
     * @param clazz Class 新实例类新
     * @param oriObj Object 原始实例
     * @return Object 填充所有属性的新实例
     */
    public static Object buildBean(Class clazz, Object oriObj) {
        Object obj = null;
        try {
            obj = clazz.newInstance();
            copyBeanProperties(obj, oriObj);
        } catch (IllegalAccessException ex) {
        	log.error("###################buildBean error##################");        	
            //ex.printStackTrace();           
        } catch (InstantiationException ex) {
        	log.error("###################buildBean InstantiationException##################");              
            //ex.printStackTrace();           
        }
        return obj;
    }

    /**
     * 根据原始实例批量构造新实例，填充所有属性。
     * @param clazz Class 新实例类新
     * @param oriObjs Collection 原始实例的集合
     * @return Collection 填充所有属性的新实例集合
     */
    public static Collection buildBeans(Class clazz, Collection oriObjs) {
        Collection results = new ArrayList();
        if ( oriObjs == null ) return null;
        for (Iterator iter = oriObjs.iterator(); iter.hasNext(); ) {
            Object oriObj = (Object) iter.next();
            results.add(buildBean(clazz, oriObj));
        }
        return results;
    }
   
    public static Object mapBean(Class c, Object oriObj) {
        Object obj = null;
        try {
        	MapperIF mapper = new DozerBeanMapper();
        	obj = mapper.map(oriObj,c);
    		
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return obj;
    }
    
    
    
    public static Object mapBeans(Class c, Collection oriObjs) {
    	 Collection results = new ArrayList();
         if ( oriObjs == null ) return null;
         for (Iterator iter = oriObjs.iterator(); iter.hasNext(); ) {
             Object oriObj = (Object) iter.next();
             results.add(mapBean(c, oriObj));
         }
         return results;
    }
    
    
    /**
     * 该方法用于实现深层拷贝，深层拷贝的级数及映射取决于映射文件的配置
     * @param filename  dozer映射文件名，传入为空则默认拷贝第一层
     * @param c 目标文件的class类型
     * @param oriObj 源文件对象
     * **/
    public static Object copyBean(String filename , Class c, Object oriObj){
    	Object obj = null;
    	if(filename == null || "".equals(filename)){
    		MapperIF mapper = new DozerBeanMapper();
        	obj = mapper.map(oriObj,c);
    	}else{
    		List mappingFiles = new ArrayList();
    		mappingFiles.add("file:"+ConfigurationHelper.getConfigInfo("mappingFileHome")+filename);
    		DozerBeanMapper mapper = new DozerBeanMapper();
    		mapper.setMappingFiles(mappingFiles);
    		obj = mapper.map(oriObj, c);
    	}
    	return obj;
    }
    
    
    public static void main (String[] args){
    	
    }
    
}
