package org.gameye.psp.image.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gameye.psp.image.action.base.BaseActionSupport;
import org.gameye.psp.image.config.Constants;
import org.gameye.psp.image.entity.Collection;
import org.gameye.psp.image.entity.Commentary;
import org.gameye.psp.image.entity.DownHistory;
import org.gameye.psp.image.entity.Image;
import org.gameye.psp.image.entity.ScoreHistory;
import org.gameye.psp.image.entity.Tag;
import org.gameye.psp.image.entity.Type;
import org.gameye.psp.image.service.ICollectionService;
import org.gameye.psp.image.service.ICommentaryService;
import org.gameye.psp.image.service.IDownHistoryService;
import org.gameye.psp.image.service.IImageService;
import org.gameye.psp.image.service.IScoreHistoryService;
import org.gameye.psp.image.service.ITypeService;
import org.gameye.psp.image.utils.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ImageHandle extends BaseActionSupport {
	private static final long serialVersionUID = 4070596295096869764L;
	@Autowired
	private IImageService imageService;
	@Autowired
	private ITypeService typeService;
	@Autowired
	private IScoreHistoryService scoreHistoryService;
	@Autowired
	private IDownHistoryService downHistoryService;
	@Autowired
	private ICommentaryService commentaryService;
	@Autowired
	private ICollectionService collectionService;

	public String upload() {
		return SUCCESS;
	}

	public String Show() {
		image = imageService.getImage(id);
		// 获取当前图片对应的评论列表
		if (image != null) {
			Map<Integer, List<Commentary>> cmtMaps = commentaryService
					.pagedImages(1, 3, image.getId(), order);
			for (Integer i : cmtMaps.keySet()) {
				totalComment = i;
				commentaries = cmtMaps.get(i);
			}
		} else {
			totalComment = 0;
			commentaries = null;
		}

		// 得到当前分类下 上一张图片ID以及下一张图片的ID
		// 当前分类不为空情况下
		preImage = imageService.getPreImage(typeId, image.getId());
		nextImage = imageService.getNextImage(typeId, image.getId());

		return SUCCESS;
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
				Type type = typeService.loadType(i.getType().getId());
				img.setType(type);
			}
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
						tag.setUser(img.getUser());

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
		// 获取当前图片对应的评论列表
		if (images != null && images.size() > 0) {
			Map<Integer, List<Commentary>> cmtMaps = commentaryService
					.pagedImages(1, 3, images.get(0).getId(), order);
			for (Integer i : cmtMaps.keySet()) {
				totalComment = i;
				commentaries = cmtMaps.get(i);
			}
		} else {
			totalComment = 0;
			commentaries = null;
		}
		log.info("totalComment:" + totalComment);
		return SUCCESS;
	}

	public void Download() {
		Image image = imageService.getImage(id);
		if (image == null)
			return;

		try {
			String filePath = Constants.getImgSavePath() + image.getNowName();
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

			DownHistory downHistory = new DownHistory();
			downHistory.setDate(new Date());
			downHistory.setImage(image);
			downHistory.setUser(getCurrUser());

			downHistoryService.add(downHistory);

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

	public void RSS() {
		if (size < 1)
			size = 10;
		if (size > 20)
			size = 20;
		images = imageService.rssImages(size);

		String urlPrefix = "http://" + getServletRequest().getServerName();
		if (getServletRequest().getServerPort() != 80) {
			urlPrefix += ":" + getServletRequest().getServerPort();
		}
		if (!urlPrefix.endsWith("/"))
			urlPrefix += "/";

		StringBuffer sb = new StringBuffer();

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<rss version=\"2.0\">");
		sb.append("<channel>");
		sb.append("<title>PSP壁纸分享</title>");
		sb.append("<link>").append(urlPrefix).append("</link>");
		sb
				.append("<description><![CDATA[PSP壁纸,壁纸分享,分享壁纸，为你为我]]></description>");
		sb.append("<language>zh-CN</language>");

		sb.append("<image>");
		sb.append("<url>" + urlPrefix + "image/rss_image.png</url>");
		sb.append("<title>Share Images For You!</title>");
		sb.append("</image>");
		sb
				.append("<copyright>&#xA9; 2008 Forshare.Org,Publicer Yongboy. All rights reserved.</copyright>");
		String imgUrl = null;
		String smallImgUrl = null;
		for (Image img : images) {
			sb.append("<item>");

			sb.append("<title><![CDATA[");
			if (StringUtils.isNotEmpty(img.getTitle()))
				sb.append(img.getTitle());
			sb.append("]]></title>");

			sb.append("<link>").append(urlPrefix).append("</link>");

			sb.append("<description><![CDATA[");
			if (StringUtils.isNotEmpty(img.getDescription())
					&& !img.getDescription().trim().equals("null")) {
				sb.append(img.getDescription());
			}
			sb.append("]]></description>");

			sb.append("<author>");
			if (img.getUser() != null) {
				sb.append(img.getUser().getName());
			}
			sb.append("</author>");
			// EEE, d MMM yyyy HH:mm:ss Z
			sb.append("<pubDate>").append(
					DateHelper.formatDate(img.getDate(),
							"EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH))
					.append("</pubDate>");

			imgUrl = urlPrefix + "images/" + img.getNowName();
			sb.append("<enclosure url=\"" + imgUrl + "\"");
			// 图片的length 和 type可以不用输出
			// sb.append("length="500000" type="image/jpeg" />";
			sb.append(" />");
			smallImgUrl = urlPrefix + "images/small/" + img.getNowName();
			// media 可以不输出
			sb.append("<media:thumbnail url = \"");
			sb.append(smallImgUrl);
			sb.append("\" width=\"80\" height=\"45\" />");

			// .append(
			// "<a href=\"#\"><img src=\"").append(urlPrefix).append(
			// "images/").append(img.getNowName()).append(
			// "\" width=\"480px\" height=\"272px\" border=\"0\"></a>");

			sb.append("</item>");
		}

		sb.append("</channel>");
		sb.append("</rss>");

		printResponseMes(sb.toString());
	}

	public String RSS2() {
		if (size < 1)
			size = 10;
		if (size > 20)
			size = 20;
		images = imageService.rssImages(size);
		return "psprss";
	}

	public String Score() {
		// if (StringUtils.isEmpty(id)) {
		// return "failure";
		// }
		if (id < 1)
			return "failure";

		Image image = imageService.getImage(id);
		int score;
		try {
			score = image.getScore();
		} catch (Exception e) {
			score = 0;
		}
		image.setScore(score + 1);
		imageService.updateImage(image);

		// 分值记录保存进数据库
		ScoreHistory scoreHistory = new ScoreHistory();
		scoreHistory.setDate(new Date());
		scoreHistory.setImage(image);
		scoreHistory.setScore(1);
		scoreHistory.setUser(getCurrUser());

		scoreHistoryService.add(scoreHistory);

		return SUCCESS;
	}

	// 收藏当前图片
	public String Collect() {
		// if (StringUtils.isEmpty(id)) {
		// return "failure";
		// }
		if (id < 1)
			return "failure";

		Image image = imageService.getImage(id);
		image.setCollect(image.getCollect() + 1);
		imageService.updateImage(image);

		Collection collection = new Collection();
		collection.setDate(new Date());
		collection.setImage(imageService.getImage(id));
		collection.setUser(getCurrUser());

		collectionService.add(collection);

		return SUCCESS;
	}

	// 增加评论
	public String AddComment() {
		if (commentary == null) {
			return "failure";
		}
		if (commentary.getImage() == null) {
			return "failure";
		}
		commentary.setImage(imageService
				.getImage(commentary.getImage().getId()));
		commentary.setDate(new Date());

		commentary.setUser(getCurrUser());

		commentaryService.add(commentary);

		return SUCCESS;
	}

	public String TypeList() {
		int typeId = -1;
		try {
			typeId = Integer.parseInt(Long.toString(id));
		} catch (NumberFormatException nfe) {
		}
		if (v < 4)
			v = 6;
		size = v * v;

		Map<Integer, List<Image>> imgMaps = imageService.oneTypeImages(page,
				size, typeId, order);
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

	public String TagList() {

		return SUCCESS;
	}

	public String ListComment() {
		if (page < 1)
			page = 1;
		if (size < 1)
			size = 10;
		image = imageService.getImage(id);
		if (image == null) {
			totalComment = 0;
			commentaries = null;
			return SUCCESS;
		}

		Map<Integer, List<Commentary>> cmtMaps = commentaryService.pagedImages(
				page, size, image.getId(), order);
		for (Integer i : cmtMaps.keySet()) {
			totalComment = i;
			commentaries = cmtMaps.get(i);
		}
		return SUCCESS;
	}

	private Logger logger = Logger.getLogger(this.getClass().getName());

	private int page;
	private int size;
	private int total;
	private String order;
	private long id;

	private List<Image> images;

	private int totalComment;
	private List<Commentary> commentaries;

	private Commentary commentary;

	private int v;

	private Image image;

	private int typeId;

	private Image preImage;
	private Image nextImage;

	// private String next;
	private String back;

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public int getTotalComment() {
		return totalComment;
	}

	public List<Commentary> getComments() {
		return commentaries;
	}

	public Commentary getComment() {
		return commentary;
	}

	public void setComment(Commentary commentary) {
		this.commentary = commentary;
	}

	public int getV() {
		return v;
	}

	public void setV(int v) {
		this.v = v;
	}

	public Image getImage() {
		return image;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public Image getPreImage() {
		return preImage;
	}

	public Image getNextImage() {
		return nextImage;
	}

	public String getBack() {
		return back;
	}

	public void setBack(String back) {
		this.back = back;
	}

	private static Log log = LogFactory.getLog(ImageHandle.class);
}