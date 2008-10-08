package org.gameye.psp.image.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.gameye.psp.image.action.base.BaseActionSupport;
import org.gameye.psp.image.config.Constants;
import org.gameye.psp.image.entity.Image;
import org.gameye.psp.image.service.IImageService;
import org.gameye.psp.image.utils.SequenceCreator;
import org.gameye.psp.image.utils.UploadTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UploadImage extends BaseActionSupport {

	@Autowired
	private IImageService imageService;
	/**
	 * 
	 */
	private static final long serialVersionUID = 3195051103800176158L;

	public String ZipUpload() {

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
		images = new ArrayList<Image>();
		for (int i = 0; i < myFiles.size(); i++) {
			fileFix = UploadTool.getFileExt(fileNames.get(i));
			nowName = System.currentTimeMillis() + fileFix;
			String descPath = Constants.getImgSavePath() + nowName;

			dst = new File(descPath);
			copyFile(myFiles.get(i), dst);

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

	public static void copyFile(File src, File dst) {
		// 判断上级目录是否为空，否则，直接创建目录
		File dir = new File(dst.getParent());
		if (!dir.exists())
			dir.mkdirs();
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src));
				out = new BufferedOutputStream(new FileOutputStream(dst));
				byte[] buffer = new byte[1024 * 10];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
