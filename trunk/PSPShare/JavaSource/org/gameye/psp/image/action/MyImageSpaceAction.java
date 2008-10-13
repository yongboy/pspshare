package org.gameye.psp.image.action;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gameye.psp.image.action.base.BaseActionSupport;
import org.gameye.psp.image.entity.Image;
import org.gameye.psp.image.entity.User;
import org.gameye.psp.image.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;

public class MyImageSpaceAction extends BaseActionSupport {

	private static final long serialVersionUID = -2777727228929358104L;
	@Autowired
	private IImageService imageService;

	/**
	 * 进入my的空间首页
	 * 
	 * @return
	 */
	public String Welcome() {
		return SUCCESS;
	}

	public String MyUpload() {

		if (v < 4)
			v = 6;
		size = v * v;

		User user = getCurrUser();
		Map<Integer, List<Image>> imgMaps = imageService.oneUserImages(page,
				size, user.getId(), order);
		if (imgMaps == null || imgMaps.keySet().size() == 0) {
			total = 0;
			return SUCCESS;
		}
		for (Integer i : imgMaps.keySet()) {
			total = i;
			images = imgMaps.get(i);
		}
		return SUCCESS;
	}

	private Log log = LogFactory.getLog(MyImageSpaceAction.class);

	private int page;
	private int size;
	private int total;
	private String order;

	private List<Image> images;

	private int v;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getV() {
		return v;
	}

	public void setV(int v) {
		this.v = v;
	}

	public int getSize() {
		return size;
	}

	public int getTotal() {
		return total;
	}

	public List<Image> getImages() {
		return images;
	}
}
