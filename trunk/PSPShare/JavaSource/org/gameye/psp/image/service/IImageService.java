package org.gameye.psp.image.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.gameye.psp.image.entity.Image;

public interface IImageService {
	void saveImage(Image image);

	void updateImage(Image image);

	Image getImage(long id);

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
	 * 得到当前图片的上一张图片
	 * 
	 * @param currImageId
	 * @return
	 */
	Image getPreImage(int typeId, long currImgId);

	/**
	 * 得到当前图片的下一张图片
	 * 
	 * @param currImageId
	 * @return
	 */
	Image getNextImage(int typeId, long currImgId);
}
