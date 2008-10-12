package org.gameye.psp.image.config;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
		realDir(getImgSavePath() + path),
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
	private static String imgSuffix = ".jpg,.jpeg,.png,.bmp,.gif";
	public static final Set<String> allowImageSuffix = new HashSet<String>(
			Arrays.asList(imgSuffix.split(",")));
}