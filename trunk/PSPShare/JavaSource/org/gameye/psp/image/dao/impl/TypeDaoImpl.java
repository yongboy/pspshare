package org.gameye.psp.image.dao.impl;

import org.gameye.psp.image.dao.ITypeDao;
import org.gameye.psp.image.entity.Type;
import org.springframework.stereotype.Repository;

import com.common.dao.base.BaseDaoImpl;

@Repository("typeDao")
public class TypeDaoImpl extends BaseDaoImpl<Type, Integer> implements ITypeDao {
}
