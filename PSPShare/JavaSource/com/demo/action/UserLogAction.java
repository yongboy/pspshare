package com.demo.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.demo.bean.IUserLogBean;
import com.demo.pojo.UserLog;
import com.opensymphony.xwork2.ActionSupport;

//@Component
public class UserLogAction extends ActionSupport {
	@Autowired
	// @Qualifier("userLogBean")
	private IUserLogBean userLogBean;

	private List logList;

	public List getLogList() {
		return logList;
	}

	public String list() {
		logList = userLogBean.getUserLogList();
		return SUCCESS;
	}

	private UserLog log;

	public UserLog getLog() {
		return log;
	}

	public void setLog(UserLog log) {
		this.log = log;
	}

	public String add() {
		userLogBean.addUserLog(log);
		return SUCCESS;
	}

	private String id;

	public void setId(String id) {
		this.id = id;
	}

	public String del() {
		userLogBean.delUserLog(id);
		return SUCCESS;
	}

}