package org.gameye.psp.image.utils;

/* 解压ZIP文件，解決了中文的問題
 * @refer @http://bbs.chinajavaworld.com/message.jspa?threadID=637074
 * 注意事项需要ant.jar包的支持
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
 // import java.util.Map;
// import java.util.HashMap;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

public class ZipTool {

	/**
	 * 解压处理
	 * 
	 * @param zipFileName
	 *            要进行压缩处理的文件(d:/aa.zip")
	 * @param outputDirectory
	 *            压缩处理后的文件路径(d:/)
	 * @return 返回解压后的文件路径数组，假如解压不正确或者出现异常，将返回null数组
	 */
	public static List<String> unZip(String zipFileName, String outputDirectory) {
		List<String> list = null;
		try {
			ZipFile zipFile = new ZipFile(zipFileName);
			Enumeration e = zipFile.getEntries();

			org.apache.tools.zip.ZipEntry zipEntry = null;
			if (e.hasMoreElements())
				list = new ArrayList();
			while (e.hasMoreElements()) {
				zipEntry = (ZipEntry) e.nextElement();
				if (zipEntry.isDirectory()) {
					String name = zipEntry.getName();
					name = name.substring(0, name.length() - 1);
					File f = new File(outputDirectory + File.separator + name);
					f.mkdirs();
				} else {
					File f = new File(outputDirectory + File.separator
							+ zipEntry.getName());
					list.add(f.getAbsolutePath());
					File f2 = new File(f.getParent());
					if (!f2.exists())
						f2.mkdirs();

					f.createNewFile();
					InputStream in = zipFile.getInputStream(zipEntry);
					FileOutputStream out = new FileOutputStream(f);
					// --------解决了图片失真的情况
					int c;
					byte[] by = new byte[1024];
					while ((c = in.read(by)) != -1) {
						out.write(by, 0, c);
					}
					out.close();
					in.close();

				}
			}

		} catch (Exception ex) {
			System.out.println("异常：" + ex.getMessage());
		}
		return list;
	}

	/**
	 * 得到一个zip压缩包内置文件列表
	 * 
	 * @param file
	 * @return
	 */
	public List<String> getZipFileList(File file) {
		List<String> list = null;
		try {
			ZipFile zipFile = new ZipFile(file);
			Enumeration e = zipFile.getEntries();
			org.apache.tools.zip.ZipEntry zipEntry = null;
			if (e.hasMoreElements())
				list = new ArrayList();
			while (e.hasMoreElements()) {
				zipEntry = (ZipEntry) e.nextElement();
				if (!zipEntry.isDirectory()) {
					list.add(zipEntry.getName());
				}
			}
		} catch (Exception ex) {
			System.out.println("异常：" + ex.getMessage());
		}
		return list;
	}

	public List<String> getZipFileList(String zipPath) {
		return getZipFileList(new File(zipPath));
	}

	/**
	 * 解压文件
	 * 
	 * @param oldFile
	 *            要进行解压的File对象
	 * @param outputDirectory
	 * @return
	 */
	public static List<String> unZip(File oldFile, String outputDirectory) {
		List<String> list = null;
		try {
			ZipFile zipFile = new ZipFile(oldFile);
			Enumeration e = zipFile.getEntries();

			org.apache.tools.zip.ZipEntry zipEntry = null;
			if (e.hasMoreElements())
				list = new ArrayList();
			while (e.hasMoreElements()) {
				zipEntry = (ZipEntry) e.nextElement();
				if (zipEntry.isDirectory()) {
					String name = zipEntry.getName();
					name = name.substring(0, name.length() - 1);
					File f = new File(outputDirectory + File.separator + name);
					f.mkdirs();
				} else {
					File f = new File(outputDirectory + File.separator
							+ zipEntry.getName());
					list.add(f.getAbsolutePath());
					File f2 = new File(f.getParent());
					if (!f2.exists())
						f2.mkdirs();

					f.createNewFile();
					InputStream in = zipFile.getInputStream(zipEntry);
					FileOutputStream out = new FileOutputStream(f);
					// --------解决了图片失真的情况
					int c;
					byte[] by = new byte[1024];
					while ((c = in.read(by)) != -1) {
						out.write(by, 0, c);
					}
					out.close();
					in.close();

				}
			}

		} catch (Exception ex) {
			System.out.println("异常：" + ex.getMessage());
		}
		return list;

	}
}
