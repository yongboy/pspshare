package org.gameye.psp.image.dao.impl;

import org.gameye.psp.image.dao.IUserDao;
import org.gameye.psp.image.entity.User;
import org.springframework.stereotype.Repository;

import com.common.dao.base.BaseDaoImpl;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User, String> implements IUserDao {
}
