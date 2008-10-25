package org.gameye.psp.image.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.gameye.psp.image.action.base.BaseActionSupport;
import org.gameye.psp.image.config.Constants;
import org.gameye.psp.image.entity.Image;
import org.gameye.psp.image.service.IImageHandleService;
import org.gameye.psp.image.service.IImageService;
import org.gameye.psp.image.utils.DateHelper;
import org.gameye.psp.image.utils.FileHelper;
import org.gameye.psp.image.utils.UploadTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UploadImage extends BaseActionSupport {
	private static final long serialVersionUID = 3195051103800176158L;
	private static Log log = LogFactory.getLog(UploadImage.class);

	@Autowired
	private IImageService imageService;

	@Autowired
	private IImageHandleService imageHandleService;

	public String uploadOther() {
		return SUCCESS;
	}

	public String PSPZipUpload() {
		if (!FileHelper.getFileExt(fileNames.get(0).toLowerCase()).endsWith(
				Constants.zipPrefix)) {
			getServletRequest().setAttribute("typeError", "您要上传的ZIP压缩文件类型不正确！");
			return INPUT;
		}

		return ZipUpload();
	}

	public String ZipUpload() {
		if (myFiles == null || myFiles.size() == 0)
			return INPUT;
		log.info("ZiP 格式 文件 contentType = " + contentTypes.get(0));
		try {
			ZipFile zipFile = new ZipFile(myFiles.get(0));
			Enumeration e = zipFile.getEntries();

			org.apache.tools.zip.ZipEntry zipEntry = null;
			if (!e.hasMoreElements())
				return INPUT;

			String fileFix = null;
			String nowName = null;
			String descPath = null;
			// 得到文件的相对路径
			String imgDir = getImgDir();
			// 得到文件的真实路径
			String imgDirPath = Constants.getImgSavePath() + imgDir;
			String imgSmallDirPath = imgDirPath
					+ Constants.thumbnail.path.getValue();
			String thumbnailPath = null;
			images = new ArrayList<Image>();
			Image image = null;
			while (e.hasMoreElements()) {
				zipEntry = (ZipEntry) e.nextElement();
				if (zipEntry.isDirectory()) {
					continue;
				}
				fileFix = UploadTool.getFileExt(zipEntry.getName())
						.toLowerCase();
				// 假如不包含，继续循环
				if (!Constants.allowImageSuffix.contains(fileFix))
					continue;

				try {
					image = doSaveInfo( zipEntry.getName(), fileFix,
							getServletRequest());
					image.setLength(zipEntry.getSize());
					// 得到文件类型，此时可能不是很准确
					image.setContentType("image/" + fileFix.replaceFirst(".", ""));
					image.setPath(imgDir);
					imageService.saveImage(image);

					nowName = image.getId() + fileFix;
					descPath = imgDirPath + nowName;
					thumbnailPath = imgSmallDirPath + nowName;

					FileHelper.copy(zipFile.getInputStream(zipEntry), descPath);
					// 保存缩略图
					imageHandleService.generate(descPath, thumbnailPath,
							Integer.parseInt(Constants.thumbnail.width
									.getValue()), Integer
									.parseInt(Constants.thumbnail.height
											.getValue()), false);
					// 这里会自动更新
//					image.setNowName(nowName);
				} catch (IOException ioe) {
					log.fatal("文件进行操作时出现严重问题 ");
					ioe.printStackTrace();
					if (image == null) {
						log.fatal("image对象为空！");
					} else {
						log.fatal("image 不为空！");
						imageService.delete(image);
					}
				}

				images.add(image);
			}
			// 批量更新图片属性信息
			// imageService.updateImages(images);
		} catch (Exception ex) {
			System.out.println("异常：" + ex.getMessage());
		}
		
		String type = getServletRequest().getParameter("type");
		log.info("type : \n" + (type==null?"is null":type));
		if(StringUtils.isNotEmpty(type)){
			getServletRequest().setAttribute("siteUrl", getSiteUrl());
			return "forbbs";
		}
		return SUCCESS;
	}

	@Override
	public String execute() {
		if (myFiles == null || myFiles.size() == 0)
			return INPUT;
		// 处理多个文件上传操作...
		String fileFix = null;
		String nowName = null;
		String descPath = null;
		// 得到文件的相对路径
		String imgDir = getImgDir();
		// 得到文件的真实路径
		String imgDirPath = Constants.getImgSavePath() + imgDir;
		// 缩略图的真实路径
		String imgSmallDirPath = imgDirPath
				+ Constants.thumbnail.path.getValue();
		String thumbnailPath = null;
		images = new ArrayList<Image>();
		Image image = null;
		for (int i = 0; i < myFiles.size(); i++) {
			fileFix = UploadTool.getFileExt(fileNames.get(i)).toLowerCase();
			if (!Constants.allowImageSuffix.contains(fileFix))
				continue;

			// 保存缩略图
			try {
				// 有限保存image属性信息
				image = doSaveInfo(fileNames.get(i), fileFix,
						getServletRequest());
				image.setLength(myFiles.get(i).length());
				image.setContentType(contentTypes.get(i));
				image.setPath(imgDir);
				imageService.saveImage(image);

				// 计算文件路径以及文件名称
				nowName = image.getId() + fileFix;
				descPath = imgDirPath + nowName;
				thumbnailPath = imgSmallDirPath + nowName;

				// 文件进行拷贝等操作
				FileHelper.copy(myFiles.get(i), descPath);
				// 文件进行缩略图制作
				imageHandleService
						.generate(myFiles.get(i).getPath(), thumbnailPath,
								Integer.parseInt(Constants.thumbnail.width
										.getValue()), Integer
										.parseInt(Constants.thumbnail.height
												.getValue()), false);
				// 这里会自动更新
//				image.setNowName(nowName);
			} catch (IOException ioe) {
				log.fatal("文件进行操作时出现严重问题 ");
				ioe.printStackTrace();
				if (image == null) {
					log.fatal("image对象为空！");
				} else {
					log.fatal("image 不为空！");
					imageService.delete(image);
				}
			}

			images.add(image);
			log.info("contentTypes = " + contentTypes.get(i));
		}
		// 批量更新图片属性信息
		imageService.updateImages(images);

		return SUCCESS;
	}

	/**
	 * PSP上传使用
	 */
	public String PSPUpload() {
		if (myFiles == null || myFiles.size() == 0)
			return INPUT;
		for (String s : fileNames) {
			if (!Constants.allowImageSuffix.contains(FileHelper.getFileExt(s
					.toLowerCase()))) {
				getServletRequest().setAttribute("typeError", "您要上传的文件类型不正确！");
				return INPUT;
			}
		}

		return execute();
	}

	private Image doSaveInfo(String oldName, String fileFix,
			HttpServletRequest request) {
		Image image = new Image();
		image.setUser(getCurrUser());
		// long date = System.currentTimeMillis();
		image.setDate(new Date());
		// image.setId(new SequenceCreator().getUID());
		image.setIp(request.getRemoteAddr());
//		image.setNowName(nowName);
		image.setOldName(oldName);
		// 默认情况下，使用原始文件名
		image.setTitle(oldName);
		image.setPostfix(fileFix);

		return image;
	}

	// 得到文件的相对路径 2008/10/13/23/
	private String getImgDir() {
		StringBuilder sb = new StringBuilder();
		sb.append(DateHelper.formatDate(new Date(), "yyyy/MM/dd/"));
		if (getCurrUser() != null)
			sb.append(getCurrUser().getId());
		else
			sb.append("0");
		sb.append("/");
		return sb.toString();
	}

	private List<File> myFiles;

	private List<String> fileNames;

	private List<String> contentTypes;

	private Image image;

	private List<Image> images;

	public void setMyFileFileName(List<String> fileNames) {
		this.fileNames = fileNames;
	}

	public void setMyFileContentType(List<String> contentTypes) {
		this.contentTypes = contentTypes;
	}

	public List<String> getFileNames() {
		return fileNames;
	}

	public void setMyFile(List<File> myFiles) {
		this.myFiles = myFiles;
	}

	public Image getImage() {
		return image;
	}

	public List<Image> getImages() {
		return images;
	}
}
