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

	public enum thumbnail {
		path("thumbnail/"),
		realDir(getWebRootPath() + path),
		width("80"),
		height("45");

		private String value;

		thumbnail(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
}