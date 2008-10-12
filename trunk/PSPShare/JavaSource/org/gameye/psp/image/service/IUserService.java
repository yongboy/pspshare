package org.gameye.psp.image.service;

import org.gameye.psp.image.entity.User;

public interface IUserService {

	void add(User user);

	User getById(Long id);

	User getUserByName(String userName);
}
