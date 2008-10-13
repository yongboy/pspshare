package org.gameye.psp.image.dao.impl;

import org.gameye.psp.image.dao.IUserDao;
import org.gameye.psp.image.dao.base.BaseDaoImpl;
import org.gameye.psp.image.entity.User;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User, String> implements IUserDao {
}
