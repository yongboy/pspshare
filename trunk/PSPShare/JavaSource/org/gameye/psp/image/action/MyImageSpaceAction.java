package org.gameye.psp.image.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.gameye.psp.image.action.base.BaseActionSupport;
import org.gameye.psp.image.config.Constants;
import org.gameye.psp.image.entity.Collection;
import org.gameye.psp.image.entity.Image;
import org.gameye.psp.image.entity.LastPlace;
import org.gameye.psp.image.entity.User;
import org.gameye.psp.image.service.ICollectionService;
import org.gameye.psp.image.service.IImageService;
import org.gameye.psp.image.service.ILastPlaceService;
import org.gameye.psp.image.utils.DateHelper;
import org.gameye.psp.image.utils.FileHelper;
import org.springframework.beans.factory.annotation.Autowired;

public class MyImageSpaceAction extends BaseActionSupport {

	private static final long serialVersionUID = -2777727228929358104L;
	@Autowired
	private IImageService imageService;
	@Autowired
	private ICollectionService collectionService;

	@Autowired
	private ILastPlaceService lastPlaceService;

	/**
	 * 进入my的空间首页
	 * 
	 * @return
	 */
	public String Welcome() {
		// 检测用户是否有上次浏览记录
		LastPlace lastPlace = lastPlaceService.getLastTimePlace(getCurrUser());
		if (lastPlace != null) {
			getServletRequest().setAttribute("lastPlace", lastPlace);
		}
		return SUCCESS;
	}

	public String MyUpload() {

		if (v < 4)
			v = 6;
		size = v * v;

		User user = getCurrUser();
		Map<Integer, List<Image>> imgMaps = imageService.oneUserImages(page,
				size, user, order);
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

	public String MyCollection() {
		if (v < 4)
			v = 6;
		size = v * v;

		User user = getCurrUser();
		Map<Integer, List<Collection>> imgMaps = collectionService.pagedImages(
				page, size, user, order);

		if (imgMaps == null || imgMaps.keySet().size() == 0) {
			total = 0;
			return SUCCESS;
		}
		for (Integer i : imgMaps.keySet()) {
			total = i;
			collections = imgMaps.get(i);
		}
		return SUCCESS;
	}

	public String CustomAtom() {
		return SUCCESS;
	}

	public void DownMyUploadZip() throws Exception {

		Map<Integer, List<Image>> imgMaps = imageService.oneUserImages(page, v
				* v, getCurrUser(), order);
		if (imgMaps == null || imgMaps.keySet().size() == 0) {
			return;
		}
		for (Integer i : imgMaps.keySet()) {
			images = imgMaps.get(i);
		}

		List<String> filePaths = new ArrayList<String>();
		List<String> fileNames = new ArrayList<String>();
		String filePath = null;
		for (Image image : images) {
			filePath = Constants.getImgSavePath() + image.getPath()
					+ image.getNowName();
			filePaths.add(filePath);
			fileNames.add(image.getNowName());
		}

		String zipName = DateHelper.formatDate(new Date(), "yyyy-MM-dd");

		DownZipFiles(getServletResponse(), zipName, filePaths, fileNames);
	}

	public void DownCollectionZip() {

		User user = getCurrUser();
		Map<Integer, List<Collection>> imgMaps = collectionService.pagedImages(
				page, v * v, user, order);

		if (imgMaps == null || imgMaps.keySet().size() == 0) {
			return;
		}
		for (Integer i : imgMaps.keySet()) {
			collections = imgMaps.get(i);
		}
		List<String> filePaths = new ArrayList<String>();
		List<String> fileNames = new ArrayList<String>();
		String filePath = null;
		for (Collection coll : collections) {
			filePath = Constants.getImgSavePath() + coll.getImage().getPath()
					+ coll.getImage().getNowName();
			filePaths.add(filePath);
			fileNames.add(coll.getImage().getNowName());
		}
		String zipName = DateHelper.formatDate(new Date(), "yyyy-MM-dd");
		DownZipFiles(getServletResponse(), zipName, filePaths, fileNames);
	}

	private void DownZipFiles(HttpServletResponse response, String zipName,
			List<String> filePaths) {

		if (!zipName.toLowerCase().endsWith(".zip")) {
			zipName += ".zip";
		}

		// HttpServletResponse response = getServletResponse();
		response.setHeader("Content-disposition", "attachment; filename="
				+ zipName);
		int BUFFER = 1024;
		// 创建缓冲输入流BufferedInputStream
		BufferedInputStream origin = null;
		// 创建ZipOutputStream对象，将向它传递希望写入文件的输出流

		OutputStream os;
		try {
			os = response.getOutputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
		ZipOutputStream zipOut = new ZipOutputStream(new BufferedOutputStream(
				os, BUFFER));
		// dirContents[]获取当前目录(myDir)所有文件对象（包括子目录名)
		// 创建临时文件tempFile,使用后删除
		try {
			byte data[] = null;
			String fileName = null;
			ZipEntry entry = null;
			int count;
			for (String path : filePaths) {
				FileInputStream fis = new FileInputStream(new File(path));
				origin = new BufferedInputStream(fis, BUFFER);
				// 为被读取的文件创建压缩条目
				fileName = FileHelper.getFileNameWithExt(path);
				log.info("path:\n" + path + "\nname:\n" + fileName);
				entry = new ZipEntry(fileName);
				data = new byte[BUFFER];
				// 在向ZIP输出流写入数据之前，必须首先使用out.putNextEntry(entry); 方法安置压缩条目对象
				zipOut.putNextEntry(entry);

				// 向ZIP 文件写入数据
				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					zipOut.write(data, 0, count);
				}
				// tempFile是临时生成的ZIP文件,删除它
			}
			// 关闭输入流
			origin.close();
			zipOut.close();

		} catch (FileNotFoundException notFound) {
			log.info("批量文件下载时，该文件不存在:\n" + notFound.toString());
		} catch (IOException ioe) {
			String message = null;
			String exceptionStr = ioe.toString();// ClientAbortException
			if (exceptionStr.indexOf("ClientAbortException") != -1) {
				message = "用户取消下载";
			} else {
				message = "批量文件下载时出现异常，异常原因:" + exceptionStr;
			}
			log.info(message);
		} catch (Exception e) {
			log.info("批量文件下载时，出现无法处理的异常：\n" + e.toString());
		}
	}

	private void DownZipFiles(HttpServletResponse response, String zipName,
			List<String> filePaths, List<String> fileNames) {

		if (!zipName.toLowerCase().endsWith(".zip")) {
			zipName += ".zip";
		}

		response.setHeader("Content-disposition", "attachment; filename="
				+ zipName);
		int BUFFER = 1024;
		// 创建缓冲输入流BufferedInputStream
		BufferedInputStream origin = null;
		// 创建ZipOutputStream对象，将向它传递希望写入文件的输出流

		OutputStream os;
		try {
			os = response.getOutputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
		ZipOutputStream zipOut = new ZipOutputStream(new BufferedOutputStream(
				os, BUFFER));
		try {
			byte data[] = null;
			ZipEntry entry = null;
			int count;
			for (int i = 0; i < filePaths.size(); i++) {
				FileInputStream fis = new FileInputStream(new File(filePaths
						.get(i)));
				origin = new BufferedInputStream(fis, BUFFER);
				// 为被读取的文件创建压缩条目
				entry = new ZipEntry(fileNames.get(i));
				data = new byte[BUFFER];
				// 在向ZIP输出流写入数据之前，必须首先使用out.putNextEntry(entry); 方法安置压缩条目对象
				zipOut.putNextEntry(entry);

				// 向ZIP 文件写入数据
				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					zipOut.write(data, 0, count);
				}
			}
			// 关闭输入流
			origin.close();
			zipOut.close();

		} catch (FileNotFoundException notFound) {
			log.info("批量文件下载时，该文件不存在:\n" + notFound.toString());
		} catch (IOException ioe) {
			String message = null;
			String exceptionStr = ioe.toString();// ClientAbortException
			if (exceptionStr.indexOf("ClientAbortException") != -1) {
				message = "用户取消下载";
			} else {
				message = "批量文件下载时出现异常，异常原因:" + exceptionStr;
			}
			log.info(message);
		} catch (Exception e) {
			log.info("批量文件下载时，出现无法处理的异常：\n" + e.toString());
		}
	}

	private Log log = LogFactory.getLog(MyImageSpaceAction.class);

	private int page;
	private int size;
	private int total;
	private String order;

	private List<Image> images;

	private List<Collection> collections;

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

	public List<Collection> getCollections() {
		return collections;
	}

}
