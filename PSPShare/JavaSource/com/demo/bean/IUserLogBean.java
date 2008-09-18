package com.demo.bean;

import java.util.List;

import com.demo.pojo.UserLog;

public interface IUserLogBean {

	/**
	 * ����һ���¼
	 * 
	 * @param userMail
	 * @return
	 */
	boolean addUserLog(UserLog userLog);

	/**
	 * ɾ��һ���¼
	 * 
	 * @param linkID
	 * @return
	 */
	boolean delUserLog( String logID );

	/**
	 * �õ���¼�б?����Ϊ��ʡ�£�û��ʹ�÷�ҳ
	 * @return
	 */
	List getUserLogList();
}