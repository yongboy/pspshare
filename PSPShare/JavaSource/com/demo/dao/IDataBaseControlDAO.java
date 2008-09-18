package com.demo.dao;

import java.util.List;

public interface IDataBaseControlDAO {

	public void setSave(Object obj);
	
	public void setSaveOrUpdate(Object obj);
	
	public void setDelete(Object obj);
	
	public void setUpdate(Object obj);
	
	public void setUpdate(String sql);
	
	public Object getOneAnnal(String path,Integer id);
	public Object getOneAnnal(String path,String id);
	public Object getOneAnnal( String path, Long id );
	
	public List getSelect(String sql);
	
	public List getSelect(String sql,int startIndex, int pageSize);
	
	public List getSearchResult( String hql, int startIndex, int pageSize );
	
	
	/**
	 * ��ݷ�ҳ
	 * @param hql
	 * @param currPage����ǰ�������ҳ��
	 * @param pageSize
	 * @return
	 */
	public List getPageList( String hql, int currPage, int pageSize );
	/**
	 * ���������getPageListһ��ʹ�ã������п���ʧЧ
	 * @return
	 */
	public int getSearchNum();
	
}
