package org.gameye.psp.image.action;

import org.gameye.psp.image.action.base.BaseActionSupport;
import org.gameye.psp.image.entity.Image;
import org.gameye.psp.image.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;

public class ImageHandle extends BaseActionSupport {

	@Autowired
	private IImageService imageService;

	public String upload(){
		return SUCCESS;
	}
	
	public void Show() {
		Image image = imageService.getImage(id);
		String imagePath = "images/" + image.getNowName();
		printResponseMes(imagePath);
	}
	
	// 图片上传之后的一些属性信息的批量更新
	public String AddInfos(){
		return SUCCESS;
	}
	
	public String List(){
		return SUCCESS;
	}
	
	

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}