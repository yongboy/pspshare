package org.gameye.psp.image.service;

import org.gameye.psp.image.entity.User;

public interface IUserService {

	void add(User user);

	User getById(String userId);
}
