package org.gameye.psp.image.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
					image = doSaveInfo(nowName, zipEntry.getName(), fileFix,
							getServletRequest());
					image.setLength(zipEntry.getSize());
					// 得到文件类型，此时可能不是很准确
					image.setContentType("image/" + fileFix);
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
					image.setNowName(nowName);
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
				image = doSaveInfo(nowName, fileNames.get(i), fileFix,
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
				image.setNowName(nowName);
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
	  * 过滤文件类型
	  * @param types 系统所有允许上传的文件类型
	  * @return 如果上传文件的文件类型允许上传，返回null，否则返回input字符串
	  */
	private String allowTypes;
	 public String filterType(String[] types)
	 {
	  //取得上传文件的文件类型。
	  String fileType = contentTypes.get(0);
	  for (String type : types)
	  {
	   if (type.equals(fileType))
	   {
	    return null;
	   }
	  }
	  return INPUT;
	 }
	 
	 public String getAllowTypes()
	 {
	  return allowTypes;
	 }
	 public void setAllowTypes(String allowTypes)
	 {
	  this.allowTypes = allowTypes;
	 }
	
	/**
	 * 		<action name="DemoUpload" class="org.gameye.psp.image.action.UploadImage" method="DemoUpload">			
			<!-- 验证上传文件的类型 -->
			<param name="allowTypes">image/bmp,image/png,image/gif,image/jpeg</param>
             <!-- 如果加入了验证上传文件的类型,必须要加input -->  
             <result name="success" type="freemarker">/WEB-INF/pages/image/upload_result.html</result>
             <result name="input">/WEB-INF/pages/image/upload.jsp</result>
             <result name="invalid.token">/WEB-INF/pages/image/upload.jsp</result>
		</action>
	 * @return
	 */
	public String DemoUpload(){

		if (myFiles == null || myFiles.size() == 0)
			return INPUT;
		
//		String s = filterType(".jpg,.png".split(","));
//		if(s != null){
//			
//		}
		log.info("goes here....:\n" + getAllowTypes());
		//将允许上传文件类型的字符串以英文逗号（,）分解成字符串数组。
		  //从而判断当前文件类型是否允许上传
		  String filterResult = filterType(getAllowTypes().split(","));
		  //如果当前文件类型不允许上传
		  if (filterResult != null)
		  {log.info("上传文件类型有误！");
		   getServletRequest().setAttribute("typeError" , "您要上传的文件类型不正确！");
		   return INPUT;
		  }
		
		
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
				image = doSaveInfo(nowName, fileNames.get(i), fileFix,
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
				image.setNowName(nowName);
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
