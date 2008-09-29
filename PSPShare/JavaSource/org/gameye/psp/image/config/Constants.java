package org.gameye.psp.image.config;

public class Constants {
	public static String savePath = null;

	protected static void setWebRootPath(String rootPath) {
		savePath = rootPath;
	}

	public static String getWebRootPath() {
		return savePath;
	}

	public static String getImgSavePath() {
		return getWebRootPath() + "images\\";
	}
}