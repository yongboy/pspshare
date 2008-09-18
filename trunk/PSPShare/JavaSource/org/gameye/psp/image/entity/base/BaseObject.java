package org.gameye.psp.image.entity.base;

import java.io.Serializable;

public abstract class BaseObject implements Serializable { 
	
	/**
     * 以key=value形式返回对象值
     * @return String
     */
	public abstract String toString();
	
	/**
     * 对比对象
     * @param o object to compare to
     * @return true/false
     */
	public abstract boolean equals(Object o);
	
	/**
     * 返回对象hash码
     * @return hashCode
     */
	public abstract int hashCode();

}
