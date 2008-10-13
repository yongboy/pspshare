package org.gameye.psp.image.service.impl;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.gameye.psp.image.dao.IUserDao;
import org.gameye.psp.image.entity.User;
import org.gameye.psp.image.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDao userDao;

	public void add(User user) {
		userDao.save(user);
	}

	public User getById(String userId) {
		return userDao.load(userId);
	}

	// public User getUserByName(String userName) {
	// String hql = "from org.gameye.psp.image.entity.User where name = ? ";
	// Object[] values = { userName };
	// List<User> users = userDao.queryList(hql, values);
	// if (users == null || users.size() == 0)
	// return null;
	// else
	// return users.get(0);
	// }

	@PostConstruct
	private void initDemoUser() {
		if (userDao.load("yongboy") != null) {
			return;
		}
		User user = new User();
		user.setNumber(1L);
		// user.setId(1L);
		user.setDate(new Date());
		user.setMail("yongboy@gmail.com");
		user.setId("yongboy");
		user.setPassword("e10adc3949ba59abbe56e057f20f883e");
		user.setNickName("yongboy");

		userDao.save(user);
	}
}
