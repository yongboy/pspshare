package org.gameye.psp.image.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.gameye.psp.image.action.base.BaseActionSupport;
import org.gameye.psp.image.config.Constants;
import org.gameye.psp.image.entity.Image;
import org.gameye.psp.image.service.IImageHandleService;
import org.gameye.psp.image.service.IImageService;
import org.gameye.psp.image.utils.FileHelper;
import org.gameye.psp.image.utils.UploadTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UploadImage extends BaseActionSupport {
	private static final long serialVersionUID = 3195051103800176158L;

	@Autowired
	private IImageService imageService;

	@Autowired
	private IImageHandleService imageHandleService;

	public String ZipUpload() {
		if (myFiles == null || myFiles.size() == 0)
			return INPUT;
		try {
			ZipFile zipFile = new ZipFile(myFiles.get(0));
			Enumeration e = zipFile.getEntries();

			org.apache.tools.zip.ZipEntry zipEntry = null;
			if (!e.hasMoreElements())
				return INPUT;

			String fileFix = null;
			String nowName = null;
			String descPath = null;
			String thumbnailPath = null;
			images = new ArrayList<Image>();
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
				nowName = System.currentTimeMillis() + fileFix;
				descPath = Constants.getImgSavePath() + nowName;
				thumbnailPath = Constants.thumbnail.realDir.getValue()
						+ nowName;
				FileHelper.copy(zipFile.getInputStream(zipEntry), descPath);
				// 保存缩略图
				try {
					imageHandleService.generate(descPath, thumbnailPath,
							Integer.parseInt(Constants.thumbnail.width
									.getValue()), Integer
									.parseInt(Constants.thumbnail.height
											.getValue()), false);
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}

				image = doSaveInfo(nowName, zipEntry.getName(), fileFix,
						getServletRequest());
				image.setLength(zipEntry.getSize());
				// 得到文件类型，此时可能不是很准确
				image.setContentType("image/" + fileFix);

				imageService.saveImage(image);
				images.add(image);
			}

		} catch (Exception ex) {
			System.out.println("异常：" + ex.getMessage());
		}
		return SUCCESS;
	}

	@Override
	public String execute() {
		if (myFiles == null || myFiles.size() == 0)
			return INPUT;
		// 处理多个文件上传操作...
		File dst = null;
		String fileFix = null;
		String nowName = null;
		String descPath = null;
		String thumbnailPath = null;
		images = new ArrayList<Image>();
		for (int i = 0; i < myFiles.size(); i++) {
			fileFix = UploadTool.getFileExt(fileNames.get(i)).toLowerCase();
			if (!Constants.allowImageSuffix.contains(fileFix))
				continue;
			nowName = System.currentTimeMillis() + fileFix;
			descPath = Constants.getImgSavePath() + nowName;
			thumbnailPath = Constants.thumbnail.realDir.getValue() + nowName;
			dst = new File(descPath);
			FileHelper.copy(myFiles.get(i), dst);
			// 保存缩略图
			try {
				imageHandleService
						.generate(myFiles.get(i).getPath(), thumbnailPath,
								Integer.parseInt(Constants.thumbnail.width
										.getValue()), Integer
										.parseInt(Constants.thumbnail.height
												.getValue()), false);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}

			image = doSaveInfo(nowName, fileNames.get(i), fileFix,
					getServletRequest());
			image.setLength(myFiles.get(i).length());
			image.setContentType(contentTypes.get(i));
			imageService.saveImage(image);
			images.add(image);
		}
		return SUCCESS;
	}

	private Image doSaveInfo(String nowName, String oldName, String fileFix,
			HttpServletRequest request) {
		Image image = new Image();
		image.setUser(getCurrUser());
		// long date = System.currentTimeMillis();
		image.setDate(new Date());
		// image.setId(new SequenceCreator().getUID());
		image.setIp(request.getRemoteAddr());
		image.setNowName(nowName);
		image.setOldName(oldName);
		// 默认情况下，使用原始文件名
		image.setTitle(oldName);
		image.setPostfix(fileFix);

		return image;
	}

	public String getCurrUserId() {
		return "0";
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
