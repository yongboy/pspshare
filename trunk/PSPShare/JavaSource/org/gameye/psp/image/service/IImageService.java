package org.gameye.psp.image.service;

import java.util.List;
import java.util.Map;

import org.gameye.psp.image.entity.Image;

public interface IImageService {
	void saveImage(Image image);

	void updateImage(Image image);

	Image getImage(String id);

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
}
