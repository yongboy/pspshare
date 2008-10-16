package org.gameye.psp.image.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.gameye.psp.image.dao.IImageDao;
import org.gameye.psp.image.entity.Image;
import org.gameye.psp.image.entity.User;
import org.gameye.psp.image.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("imageService")
@Transactional
public class ImageServiceImpl implements IImageService {
	@Autowired
	private IImageDao imageDao;

	public Image getImage(long id) {
		return imageDao.load(id);
	}

	public void updateImage(Image image) {
		imageDao.update(image);
	}

	public void saveImage(Image image) {
		imageDao.save(image);
	}

	public void saveImages(Collection<Image> images) {
		imageDao.saveOrUpdateAll(images);
	}

	public void updateImages(Collection<Image> images) {
		imageDao.saveOrUpdateAll(images);
	}

	public void delete(Image image) {
		imageDao.delete(image);
	}

	public Map<Integer, List<Image>> pagedImages(int page, int size,
			String order) {
		if (page < 1)
			page = 1;
		if (size < 1)
			size = 1;
		if (StringUtils.isEmpty(order))
			order = "";
		int startIndex = (page - 1) * size;
		int pageSize = size;
		Object[] ob = {};
		String hql = "from org.gameye.psp.image.entity.Image order by date "
				+ order;
		return imageDao.pagedQuery(hql, startIndex, pageSize, ob);
	}

	public List<Image> rssImages(int pageSize) {
		if (pageSize < 1)
			pageSize = 1;
		if (pageSize > 100)
			pageSize = 100;
		Object[] ob = {};
		String hql = "from org.gameye.psp.image.entity.Image order by date desc";
		return imageDao.pagedQueryList(hql, 0, pageSize, ob);
	}

	public Map<Integer, List<Image>> oneTypeImages(int page, int size,
			int typeId, String order) {
		if (typeId == 0)
			return pagedImages(page, size, order);

		if (page < 1)
			page = 1;
		if (size < 1)
			size = 1;
		if (StringUtils.isEmpty(order))
			order = "desc";
		int startIndex = (page - 1) * size;
		int pageSize = size;
		List<Object> params = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder();
		sb.append("from org.gameye.psp.image.entity.Image where ");
		if (typeId < 0)
			sb.append("type = null ");
		else {
			sb.append("type.id = ? ");
			params.add(typeId);
		}
		sb.append("order by date ");
		sb.append(order);
		return imageDao.pagedQuery(sb.toString(), startIndex, pageSize, params
				.toArray());
	}

	public Map<Integer, List<Image>> oneTagImages(int page, int size,
			long tagId, String order) {
		if (tagId < 1)
			return pagedImages(page, size, order);

		if (page < 1)
			page = 1;
		if (size < 1)
			size = 1;
		if (StringUtils.isEmpty(order))
			order = "desc";
		int startIndex = (page - 1) * size;
		int pageSize = size;
		List<Object> params = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder();
		sb.append("from Image where ");
		if (tagId < 1)
			sb.append("tags = null ");
		else {
			sb.append("tags.id = ? ");
			params.add(tagId);
		}
		sb.append("order by date ");
		sb.append(order);
		return imageDao.pagedQuery(sb.toString(), startIndex, pageSize, params
				.toArray());
	}

	public Image getNextImage(int typeId, long currImgId) {
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		sb.append("from org.gameye.psp.image.entity.Image where id > ? ");
		params.add(currImgId);
		if (typeId < 0) {
			sb.append(" and type = null ");
		} else if (typeId > 0) {
			sb.append(" and type.id = ? ");
			params.add(typeId);
		}
		sb.append("order by id");
		List<Image> list = imageDao.pagedQueryList(sb.toString(), 0, 1, params
				.toArray());
		if (list == null || list.size() == 0)
			return null;
		return list.get(0);
	}

	public Image getPreImage(int typeId, long currImgId) {
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		sb.append("from org.gameye.psp.image.entity.Image where id < ? ");
		params.add(currImgId);
		if (typeId < 0) {
			sb.append(" and type = null ");
		} else if (typeId > 0) {
			sb.append(" and type.id = ? ");
			params.add(typeId);
		}
		sb.append("order by id desc");
		List<Image> list = imageDao.pagedQueryList(sb.toString(), 0, 1, params
				.toArray());
		if (list == null || list.size() == 0)
			return null;
		return list.get(0);
	}

	public Map<Integer, List<Image>> oneUserImages(int page, int size,
			User user, String order) {
		if (user == null)
			return null;

		if (page < 1)
			page = 1;
		if (size < 1)
			size = 1;
		if (StringUtils.isEmpty(order))
			order = "desc";
		int startIndex = (page - 1) * size;
		int pageSize = size;
		List<Object> params = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder();
		sb.append("from org.gameye.psp.image.entity.Image where ");
		sb.append("user = ? ");
		params.add(user);
		sb.append("order by id ");
		sb.append(order);
		return imageDao.pagedQuery(sb.toString(), startIndex, pageSize, params
				.toArray());
	}
}
