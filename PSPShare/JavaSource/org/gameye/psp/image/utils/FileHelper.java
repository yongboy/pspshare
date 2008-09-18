package org.gameye.psp.image.utils;

import java.io.File;
import java.io.FilenameFilter;
/*******************************************************************************
 * 文件处理帮助类 
 * @author Majian
 * 
 ******************************************************************************/
public class FileHelper

{
	/***************************************************************************
	 * 根据传入的合法路径得到目录下的文件数组
	 * 
	 * @param path
	 *            传入的路径,如带扩展名称E:\\log\\*.log，则获取目录下扩展名为log的文件对象集合，
	 *            如不带扩展名，则获取目录下所有文件对象集合
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

	public static void main(String[] args) {
		 File[] files = FileHelper.getfiles("E:\\log\\*.txt");
//		File[] files = FileHelper.getfiles("E:\\log\\");
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				File f = files[i];
			}
		}
	}

}