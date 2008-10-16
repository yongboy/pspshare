package org.gameye.psp.image.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.gameye.psp.image.entity.LastPlace;
import org.gameye.psp.image.entity.ScoreHistory;
import org.gameye.psp.image.entity.Tag;
import org.gameye.psp.image.entity.Type;
import org.gameye.psp.image.service.ICollectionService;
import org.gameye.psp.image.service.ICommentaryService;
import org.gameye.psp.image.service.IDownHistoryService;
import org.gameye.psp.image.service.IImageService;
import org.gameye.psp.image.service.ILastPlaceService;
import org.gameye.psp.image.service.IScoreHistoryService;
import org.gameye.psp.image.service.ITagService;
import org.gameye.psp.image.service.ITypeService;
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
	@Autowired
	private ILastPlaceService lastPlaceService;
	@Autowired
	private ITagService tagService;

	public String upload() {
		return SUCCESS;
	}

	public String uploadZip() {
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
						Set<Image> imgSet = new HashSet<Image>();
						imgSet.add(img);
						tag.setImages(imgSet);

						sets.add(tag);
					}
					img.setTags(sets);
				}
			}

			imageService.updateImage(img);
		}
		return SUCCESS;
	}

	public String AddZipInfo() {
		if (images == null || images.size() == 0) {
			return "failure";
		}
		Image target = images.get(0);

		String ids = target.getPostfix();
		if (StringUtils.isEmpty(ids)) {
			return "failure";
		}
		if (ids.endsWith(","))
			ids = ids.substring(0, ids.length() - 1);
		String[] imgIds = ids.split(",");
		Image img = null;
		List<Tag> sets = null;
		Set<Image> imageSet = new HashSet<Image>();
		int index = 0;
		for (String imgId : imgIds) {
			img = imageService.getImage(Long.parseLong(imgId));
			img.setTitle(target.getTitle() + (++index));
			img.setDescription(target.getDescription());
			if (target.getType() != null && target.getType().getId() > 0) {
				Type type = typeService.loadType(target.getType().getId());
				img.setType(type);
			}
			if (target.getTags() != null) {
				if (sets == null) {
					String tagStr = null;
					sets = target.getTags();
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

							sets.add(tag);
							// 保存进数据库
							tagService.addTags(sets);
						}
					}
				}
				img.setTags(sets);
			}
			imageSet.add(img);
		}
		imageService.updateImages(imageSet);
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
		return SUCCESS;
	}

	public void Download() {
		Image image = imageService.getImage(id);
		if (image == null)
			return;

		try {
			String filePath = Constants.getImgSavePath() + image.getPath() +image.getNowName();
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

	public String SaveMyPlace() {
		
		String lastPlace = getServletRequest().getHeader("referer");
		log.info("获得用户最好浏览位置:\n" + lastPlace);
		if(StringUtils.isEmpty(lastPlace)){
			return INPUT;
		}
		
		LastPlace place = new LastPlace();
		place.setDate(new Date());
		place.setIp(getServletRequest().getRemoteAddr());
		
		
		place.setPlace(lastPlace);
		
		place.setUser(getCurrUser());

		lastPlaceService.save(place);

		return SUCCESS;
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

	private List<String> tags;

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

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	private static Log log = LogFactory.getLog(ImageHandle.class);
}