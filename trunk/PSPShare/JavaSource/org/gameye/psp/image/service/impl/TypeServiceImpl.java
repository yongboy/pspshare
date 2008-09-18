package org.gameye.psp.image.service.impl;

import org.gameye.psp.image.dao.ITypeDao;
import org.gameye.psp.image.entity.Type;
import org.gameye.psp.image.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("typeService")
public class TypeServiceImpl implements ITypeService {

	@Autowired
	@Qualifier("typeDao")
	private ITypeDao typeDao;

	public void addType(Type type) {
		typeDao.save(type);
	}

	public Type loadType(int id) {
		return typeDao.load(Integer.valueOf(id));
	}

}
