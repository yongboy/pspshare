package org.gameye.psp.image.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class UploadTool {

	public static boolean checkFormatName(String formatName) {
		if (formatName == null || "".equals(formatName))
			return false;
		return formatName.trim().toLowerCase().matches(
				"\\d{2,3}\\s*.\\s*\\d{2,3}\\.*.*");
	}

	/**
	 * 从图片命名中，获取图片的宽和高数字,比如 22 x 32.jpg ，那么宽将是 22， 高将是32
	 * 
	 * @param formatName
	 *            文件名称，比如 22x32.jpg
	 * @return 返回 Map<宽，高>
	 */
	public static Map<Integer, Integer> getFormatInfo(String formatName) {
		if (formatName == null || "".equals(formatName))
			return null;

		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		String patternString = "(\\d{2,3})\\s*.\\s*(\\d{2,3})\\.*.*";

		Pattern pattern = null;
		try {
			pattern = Pattern.compile(patternString);
		} catch (PatternSyntaxException pse) {
			pse.printStackTrace();
		}
		Matcher matcher = pattern.matcher(formatName);
		String widthStr = null;
		String heightStr = null;
		while (matcher.find()) {
			widthStr = matcher.group(1);
			heightStr = matcher.group(2);
		}
		int width = 0;
		try {
			width = Integer.parseInt(widthStr);
		} catch (NumberFormatException nfe) {
		}
		int height = 0;
		try {
			height = Integer.parseInt(heightStr);
		} catch (NumberFormatException nfe2) {
		}
		map.put(width, height);
		return map;
	}
	
	/**
	 * 检测文件名称是否符合规格，只能适用于图片
	 * @param formatName
	 * @return
	 */
	public static boolean checkInFormats(String formatName) {
		if (formatName == null || "".equals(formatName))
			return false;

		String fileName = getFileName(formatName);
		if ("".equals(fileName))
			return false;
		String sixFormats = "96x65,101x80,120x160,128x128,176x144,240x320";
		return (sixFormats.indexOf(fileName.toLowerCase().trim().replaceAll(
				" ", "")) != -1);
	}
	
	public static String getMinPixelImgName(java.util.List<String>imgNames){
		//list数据不同规格数据无序可遵守
		
		if(imgNames.size()==1)return imgNames.get(0);
		
		int width = -1;
		int height = -1;
		int tmpWidth = -1;
		int tmpHeight = -1;
		String targetName = null;
		String fileName = imgNames.get(0).trim().toLowerCase().replaceAll(" ", "");
		fileName = getFileName(fileName);
		try{
			width = Integer.parseInt(fileName.split("x")[0]);
		}catch(NumberFormatException e){				
		}
		try{
			height = Integer.parseInt(fileName.split("x")[1]);
		}catch(NumberFormatException e1){				
		}
		for(String img : imgNames){
			img = img.toLowerCase().trim().replaceAll(" ", "");
			fileName = getFileName(img);
//			得到图片的规格数值
			try{
				tmpWidth = Integer.parseInt(fileName.split("x")[0]);
			}catch(NumberFormatException e){				
			}
			try{
				tmpHeight = Integer.parseInt(fileName.split("x")[1]);
			}catch(NumberFormatException e1){				
			}
			
			if(width > tmpWidth && height > tmpHeight){
				width = tmpWidth;
				height = tmpHeight;
				targetName = img;
			}
		}
		if(targetName ==null)return imgNames.get(0);
		return targetName;
	}
	
	/**
	 * 检查给定的文件列表，是否含有一致的文件后缀，若是一致，返回true。没有文件后缀的返回false，含有一个文件返回true，其它返回false
	 * 
	 * @param fileNames
	 * @return
	 */
	public static boolean checkIfHasSameExt(java.util.List<String> fileNames) {
		if (fileNames.size() == 1)
			return true;
		// 得到第一个文件的后缀
		String tmpExt = getFileExt(fileNames.get(0));
		if ("".equals(tmpExt))
			return false;
		for (String fn : fileNames) {
			if (!tmpExt.equalsIgnoreCase(getFileExt(fn)))
				return false;
		}
		return true;
	}
	
	public static boolean checkIfImage(String fileName) {
		if (fileName == null || "".equals(fileName))
			return false;
		return fileName.toLowerCase().matches(
				".*\\.("
						+ Configuration.getInstance().getValue(
								"allow.media.image") + ")");
	}

	public static boolean checkIfAudio(String fileName) {
		if (fileName == null || "".equals(fileName))
			return false;
		return fileName.toLowerCase().matches(
				".*\\.("
						+ Configuration.getInstance().getValue(
								"allow.media.audio") + ")");
	}

	public static boolean checkIfVideo(String fileName) {
		if (fileName == null || "".equals(fileName))
			return false;
		return fileName.toLowerCase().matches(
				".*\\.("
						+ Configuration.getInstance().getValue(
								"allow.media.video") + ")");
	}

//	/**
//	 * 传入文件名称得到相对应的类型id，假如没有对应类型，则返回 "0"
//	 * 
//	 * @param fileName
//	 * @return
//	 */
//	public static String getFileTypeId(String fileName) {
//		if (checkIfImage(fileName))
//			return Constants.ResourceType.Image.getValue();
//		return checkAudioAndVideo(fileName);
//	}
//
//	/**
//	 * 加入符合要求的图片文件命名方式，返回文件类型。没有匹配，返回"0" 要求文件名称类似于 23x36.png
//	 * ，只有符合该要求的文件名称才会返回对应的图片类型id
//	 * 
//	 * @param fileName
//	 * @return
//	 */
//	public static String getFileTypeIdWithSpec(String fileName) {
//		if (checkIfImage(fileName) && checkFormatName(fileName))
//			return Constants.ResourceType.Image.getValue();
//		return checkAudioAndVideo(fileName);
//	}
//
//	/**
//	 * 返回音频和视频文件对应的类型id，没有找到，则返回 "0"
//	 * 
//	 * @param fileName
//	 * @return
//	 */
//	private static String checkAudioAndVideo(String fileName) {
//		if (checkIfAudio(fileName))
//			return Constants.ResourceType.Audio.getValue();
//		if (checkIfVideo(fileName))
//			return Constants.ResourceType.Video.getValue();
//		return Constants.ResourceType.Other.getValue();
//	}

	/**
	 * 计算文件路径
	 * 
	 * @param compUUID
	 *            企业ID
	 * @param typeID
	 *            资源类型
	 * @param formatUID
	 *            资源规格ID
	 * @param resUUID
	 *            资源ID
	 * @param fileExt
	 *            文件后缀，eg ： .txt
	 * @return 组合成类似于 <b>公司ID后两位/公司ID/资源分类ID/资源ID后两位/资源ID/资源文件名(规格ID+后缀名)</b>的字符串
	 *         注意，返回路径，不是以 / 开头
	 */
	public static String compFilePath(String compUUID, String typeID,
			String formatUID, String resUUID, String fileExt) {
		// 路径前缀/公司ID后两位/公司ID/资源分类ID/资源ID后两位/资源ID/资源文件名(规格ID+后缀名)
		StringBuffer sb = new StringBuffer();
		sb.append(compUUID.substring(compUUID.length() - 2, compUUID.length()));
		sb.append("/");
		sb.append(compUUID);
		sb.append("/");
		sb.append(typeID);
		sb.append("/");
		if (resUUID.length() > 1) {
			sb
					.append(resUUID.substring(resUUID.length() - 2, resUUID
							.length()));
		} else {// 在用户未选择资源分类ID时，resUUID 可能为0值
			sb.append(resUUID);
		}
		sb.append("/");
		sb.append(resUUID);
		sb.append("/");
		sb.append(formatUID);
		if (!fileExt.startsWith("."))
			sb.append(".");
		sb.append(fileExt);

		return sb.toString();
	}
	
	/**
	 * 计算文件所在的相对路径，eg：/公司ID后两位/公司ID/资源分类ID/资源ID后两位/资源ID/
	 * @param compUUID
	 * @param typeID
	 * @param resUUID
	 * @return
	 */
	public static String compFileDirPath(String compUUID, String typeID,
			String resUUID) {
		StringBuffer sb = new StringBuffer();
		sb.append(compUUID.substring(compUUID.length() - 2, compUUID.length()));
		sb.append("/");
		sb.append(compUUID);
		sb.append("/");
		sb.append(typeID);
		sb.append("/");
		if (resUUID.length() > 1) {
			sb
					.append(resUUID.substring(resUUID.length() - 2, resUUID
							.length()));
		} else {// 在用户未选择资源分类ID时，resUUID 可能为0值
			sb.append(resUUID);
		}
		sb.append("/");
		sb.append(resUUID);
		sb.append("/");

		return sb.toString();
	}

	public static String getFileExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}
	
	public static String getFileName(String fileName) {
		return fileName.substring(0,fileName.lastIndexOf("."));
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
	
	public static void main(String [] args){
//		String fn = ".JPG";
//		System.out.println(getFileName(fn));
//		System.out.println(checkInFormats(fn));
		
		java.util.List <String> fileNames = new java.util.ArrayList<String>();
		fileNames.add("55x78.jpg");
		fileNames.add("35x35.jpg");
		fileNames.add("366x3546.JPG");
		System.out.println(checkIfHasSameExt(fileNames));
		
		fileNames.add("2355x235.png");
		
		System.out.println(checkIfHasSameExt(fileNames));
		
		System.out.println("最终结果" + getMinPixelImgName(fileNames));
	}
}
