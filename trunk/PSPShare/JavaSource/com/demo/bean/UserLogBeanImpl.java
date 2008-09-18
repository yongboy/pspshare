package com.demo.bean;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.demo.dao.IDataBaseControlDAO;
import com.demo.pojo.UserLog;

@Service("userLogBean")
public class UserLogBeanImpl implements IUserLogBean {

	@Autowired
	@Qualifier("dataBaseControlDAO")
	private IDataBaseControlDAO baseDao;

	public boolean addUserLog(UserLog userLog) {
		try {
			baseDao.setSave(userLog);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delUserLog(String logID) {
		boolean state = false;
		try {
			String hql = "delete from UserLog where id =" + logID;
			baseDao.setUpdate(hql);
			state = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}

	public List getUserLogList() {
		String hql = "from UserLog";
		return baseDao.getSelect(hql);
	}
}