package org.gameye.psp.image.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.gameye.psp.image.action.base.BaseActionSupport;
import org.gameye.psp.image.config.Constants;
import org.gameye.psp.image.entity.Image;
import org.gameye.psp.image.entity.Tag;
import org.gameye.psp.image.entity.Type;
import org.gameye.psp.image.service.IImageService;
import org.gameye.psp.image.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;

public class ImageHandle extends BaseActionSupport {

	@Autowired
	private IImageService imageService;
	@Autowired
	private ITypeService typeService;

	public String upload() {
		return SUCCESS;
	}

	public void Show() {
		Image image = imageService.getImage(id);
		String imagePath = "images/" + image.getNowName();
		printResponseMes(imagePath);
	}

	// 图片上传之后的一些属性信息的批量更新
	public String AddInfos() {
		if (images == null || images.size() == 0) {
			return "failure";
		}
		Image img = null;
		for (Image i : images) {
			img = imageService.getImage(i.getId());
			img.setTitle(i.getTitle());
			img.setDescription(i.getDescription());
			if (i.getType() != null && i.getType().getId() > 0) {
				// Type type = typeService.loadType(i.getType().getId());
				img.setType(typeService.loadType(i.getType().getId()));
				// do something...
			}
			// if (StringUtils.isNotEmpty(i.getTags())) {
			// img.setTags(i.getTags());
			// }
			if (i.getTags() != null) {
				String tagStr = null;
				List<Tag> sets = i.getTags();
				for (Tag t : sets) {
					tagStr = t.getName();
				}
				if (StringUtils.isNotEmpty(tagStr)) {
					sets.clear();
					String[] tags = tagStr.trim().split(" ");
					Tag tag = null;
					for (String t : tags) {
						tag = new Tag();
						tag.setName(t);
						tag.setDate(new Date());
						tag.setAuthor(img.getAuthor());

						tag.setImage(img);

						sets.add(tag);
					}
					img.setTags(sets);
				}
			}

			imageService.updateImage(img);
		}
		return SUCCESS;
	}

	public String List() {
		if (page < 1)
			page = 1;
		if (size < 1)
			size = 1;
		Map<Integer, List<Image>> imgMaps = imageService.pagedImages(page,
				size, order);
		for (Integer inter : imgMaps.keySet()) {
			total = inter;
			images = imgMaps.get(inter);
		}
		return SUCCESS;
	}

	public void Download() {
		Image image = imageService.getImage(id);
		if (image == null)
			return;

		try {
			String filePath = Constants.savePath + "/" + image.getNowName();
			File file = new File(filePath);
			FileInputStream is = new FileInputStream(file);
			String fileName = image.getNowName();
			// 只有转化编码才可以被浏览器认识，不至于出现乱码
			// fileName = StringUtil.gb2iso(fileName);
			getServletResponse().setHeader("Content-disposition",
					"attachment; filename=" + fileName);
			int len = -1;
			OutputStream out = getServletResponse().getOutputStream();
			while ((len = is.read()) != -1) {
				out.write(len);
			}
			is.close();
			out.flush();
			out.close();

			// 这里使用户所下载的文件下载数加1，但是耦合性很强，暂时如此，如果可以放在拦截器里面，最好不过了
			int downNum = image.getDown();
			if (downNum < 0)
				downNum = 0;
			image.setDown(downNum + 1);
			imageService.updateImage(image);
		} catch (FileNotFoundException notFound) {
			logger.log(Level.INFO, "文件下载时，该文件不存在:\n" + notFound.toString()
					+ "\n下载的文件ID为：" + image.getId());
		} catch (IOException ioe) {
			String message = null;
			String exceptionStr = ioe.toString();// ClientAbortException
			if (exceptionStr.indexOf("ClientAbortException") != -1) {
				message = "用户取消下载";
			} else {
				message = "文件下载出现异常，异常原因:" + exceptionStr + "\n下载的文件ID为："
						+ image.getId();
			}
			logger.log(Level.INFO, message);
		} catch (Exception e) {
			logger.log(Level.INFO, "文件下载时，出现无法处理的异常：\n" + e.toString()
					+ "\n下载的文件ID为：" + image.getId());
		}

	}

	private Logger logger = Logger.getLogger(this.getClass().getName());

	private int page;
	private int size;
	private int total;
	private String order;
	private String id;

	private List<Image> images;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}