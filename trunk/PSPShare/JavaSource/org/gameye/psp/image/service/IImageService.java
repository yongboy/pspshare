package org.gameye.psp.image.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.gameye.psp.image.entity.Image;
import org.gameye.psp.image.entity.User;

public interface IImageService {
	void saveImage(Image image);

	void updateImage(Image image);

	void saveImages(Collection<Image> images);

	void updateImages(Collection<Image> images);

	Image getImage(long id);

	void delete(Image image);

	/**
	 * 分页函数
	 * 
	 * @param page
	 *            用户当前请求的页码，最小值为1
	 * @param size
	 *            每页数量，最小为1
	 * @param order
	 *            排序方式；默认为正序排列
	 * @return 包含分页结果
	 */
	Map<Integer, List<Image>> pagedImages(int page, int size, String order);

	/**
	 * 为RSS提供数据源
	 * 
	 * @param pageSize
	 *            每次提取数据数目，数值范围在于(1-100)
	 * @return
	 */
	List<Image> rssImages(int pageSize);

	/**
	 * 提取一个分类下面所有图片
	 * 
	 * @param page
	 * @param size
	 * @param typeId
	 * @param order
	 * @return
	 */
	Map<Integer, List<Image>> oneTypeImages(int page, int size, int typeId,
			String order);

	/**
	 * 一个标签下所有的图片列表
	 * 
	 * @param page
	 * @param size
	 * @param tagId
	 * @param order
	 * @return
	 */
	Map<Integer, List<Image>> oneTagImages(int page, int size, long tagId,
			String order);

	/**
	 * 得到当前图片的上一张图片（图片ID是自增长类型的）
	 * 
	 * @param currImageId
	 * @return
	 */
	Image getPreImage(long currImgId);

	/**
	 * 得到当前类型下上一张图片，根据图片的id(自增长类型)
	 * 
	 * @param typeId
	 * @param currImgId
	 * @return
	 */
	Image getPreImage(int typeId, long currImgId);

	/**
	 * 得到用户上传的上一张图片
	 * 
	 * @param userId
	 * @param currImgId
	 * @return
	 */
	Image getPreImage(String userId, long currImgId);

	/**
	 * 得到当前图片的下一张图片
	 * 
	 * @param currImageId
	 * @return
	 */
	Image getNextImage(long currImgId);

	/**
	 * 得到当前类型下的图片
	 * 
	 * @param typeId
	 * @param currImgId
	 * @return
	 */
	Image getNextImage(int typeId, long currImgId);

	/**
	 * 得到用户上传的下一张图片
	 * 
	 * @param userId
	 * @param currImgId
	 * @return
	 */
	Image getNextImage(String userId, long currImgId);

	/**
	 * 得到一个用户的所有图片
	 * 
	 * @param page
	 * @param size
	 * @param userId
	 * @param order
	 * @return
	 */
	Map<Integer, List<Image>> oneUserImages(int page, int size, User user,
			String order);
}
