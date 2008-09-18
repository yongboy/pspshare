package org.gameye.psp.image.dao.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.lang.WordUtils;

/**
 * 操作类
 * 
 * @author Majian
 * @date Jun 20, 2008 2:10:31 PM
 */
public class ReflectObjectHelper {
	/**
	 * 得到对象实例
	 * 
	 * @param name
	 * @return
	 */
	public static Object getObjectByFullname(String name) {
		try {
			return Class.forName(name).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param poobj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> transferPoToMap(Object poobj) {
		Map<String, Object> pomap = new Hashtable<String, Object>();
		try {
			Class poClass = poobj.getClass();
			Field[] fields = poClass.getDeclaredFields();
			for (int j = 0; j < fields.length; j++) {
				Field field = fields[j];
				String name = field.getName();
				String methodName = getMethodName(name);
				Method method = poClass.getDeclaredMethod(methodName, null);
				Object value = method.invoke(poobj, null);
				if (value != null) {
					pomap.put(name, value);
				} else {
					pomap.put(name, "");
				}
			}
		} catch (Exception ce) {
			ce.printStackTrace();
		}
		return pomap;
	}

	/**
	 * 得到方法名
	 * 
	 * @param name
	 * @return
	 */
	private static String getMethodName(String name) {
		String methodName = "get" + WordUtils.capitalize(name);
		return methodName;
	}

	/**
	 * 获得定义Class时声明的父类的范型参数的类型
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Class getSuperClassGenricType(Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 获得定义Class时声明的父类的范型参数的类型
	 * 
	 * @param clazz
	 * @param index
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Class getSuperClassGenricType(Class clazz, int index) {
		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[index];
	}
}
