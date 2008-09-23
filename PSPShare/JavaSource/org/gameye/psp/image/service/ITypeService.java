package org.gameye.psp.image.service;

import org.gameye.psp.image.entity.Type;

public interface ITypeService {
	void addType(Type type);

	Type loadType(Integer id);
}
