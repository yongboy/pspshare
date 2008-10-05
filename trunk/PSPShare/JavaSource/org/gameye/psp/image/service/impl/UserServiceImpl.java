package org.gameye.psp.image.service.impl;

import org.gameye.psp.image.dao.IUserDao;
import org.gameye.psp.image.entity.User;
import org.gameye.psp.image.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDao userDao;

	public void add(User user) {
		userDao.save(user);
	}

	public User getById(Long id) {
		return userDao.load(id);
	}

}
