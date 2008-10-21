package org.gameye.psp.image.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.gameye.psp.image.action.base.BaseActionSupport;
import org.gameye.psp.image.utils.DateHelper;

public class DownloadImageAction extends BaseActionSupport {

	private static final long serialVersionUID = 8384564394341339126L;
	
	private static Log log = LogFactory.getLog("DownloadImageAction");

	public void downCompressedFiles() throws Exception {
		String zipName = DateHelper.formatDate(new Date(), "yyyy-MM-dd");
		
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
		File tempFile = null;
		try {
			for (String path : filePaths) {
				tempFile = new File(path);
				FileInputStream fis = new FileInputStream(tempFile);
				origin = new BufferedInputStream(fis, BUFFER);
				// 为被读取的文件创建压缩条目
				ZipEntry entry = new ZipEntry(path);
				byte data[] = new byte[BUFFER];
				int count;
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
}
