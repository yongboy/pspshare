package org.gameye.psp.image.service.impl;

import javax.annotation.PostConstruct;

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

	public Type loadType(Integer id) {
		return typeDao.load(id);
	}

	@PostConstruct
	public void initTags() {
		Type t1 = new Type();
		t1.setId(1);
		t1.setTitle("风景");
		typeDao.save(t1);

		t1 = new Type();
		t1.setId(2);
		t1.setTitle("人物");
		typeDao.save(t1);

		t1 = new Type();
		t1.setId(3);
		t1.setTitle("动漫");
		typeDao.save(t1);

		t1 = new Type();
		t1.setId(4);
		t1.setTitle("美女");
		typeDao.save(t1);

		t1 = new Type();
		t1.setId(5);
		t1.setTitle("游戏");
		typeDao.save(t1);

		t1 = new Type();
		t1.setId(6);
		t1.setTitle("艺术");
		typeDao.save(t1);

		t1 = new Type();
		t1.setId(7);
		t1.setTitle("娱乐");
		typeDao.save(t1);

		t1 = new Type();
		t1.setId(8);
		t1.setTitle("其它");
		typeDao.save(t1);
	}
}
