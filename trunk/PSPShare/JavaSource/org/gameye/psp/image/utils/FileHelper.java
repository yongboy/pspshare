package org.gameye.psp.image.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

/*******************************************************************************
 * 文件处理帮助类
 * 
 * @author Majian
 * 
 ******************************************************************************/
public class FileHelper

{
	/***************************************************************************
	 * 根据传入的合法路径得到目录下的文件数组
	 * 
	 * 
	 * @param path
	 *            传入的路径,如带扩展名称E:\\log\\*.log，则获取目录下扩展名为log的文件对象集合，
	 *            如不带扩展名，则获取目录下所有文件对象集合
	 * 
	 * @return 返回文件对象集合
	 **************************************************************************/
	public static File[] getfiles(String path) {
		String extension = "";
		FilenameFilter filter = null;
		if (path.indexOf("*.") != -1) { // 获取指定扩展名的文件
			extension = path.substring(path.indexOf("*.") + 2, path.length());
			path = path.substring(0, path.indexOf("*."));
			filter = new FileNameFilterImpl(extension);
		}
		File f = new File(path);
		if (f.exists()) {
			File[] children = (filter == null) ? f.listFiles() : f
					.listFiles(filter);
			return children;
		} else
			return null;

	}

	private static String encodin = "UTF8";
	private static String encodout = "UTF8";

	public static void writeOutput(String filePath, String str) {
		try {
			FileOutputStream fos = new FileOutputStream(filePath);
			Writer out = new OutputStreamWriter(fos, encodout);
			out.write(str);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String readInput(String filePath) {
		StringBuffer buffer = new StringBuffer();
		try {
			FileInputStream fis = new FileInputStream(filePath);
			InputStreamReader isr = new InputStreamReader(fis, encodin);
			Reader in = new BufferedReader(isr);
			int ch;
			while ((ch = in.read()) > -1) {
				buffer.append((char) ch);
			}
			in.close();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getFileExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	public static String getFileName(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf("."));
	}

	public static void copy(File src, File dst) {
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

	public static void copy(InputStream in, String target) throws IOException {
		File f = new File(target);
		File f2 = new File(f.getParent());
		if (!f2.exists())
			f2.mkdirs();

		f.createNewFile();
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

	public static void main(String[] args) {
		File[] files = FileHelper.getfiles("E:\\log\\*.txt");
		// File[] files = FileHelper.getfiles("E:\\log\\");
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				File f = files[i];
			}
		}
	}

}