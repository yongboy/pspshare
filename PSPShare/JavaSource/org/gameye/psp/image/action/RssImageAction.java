package org.gameye.psp.image.action;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.gameye.psp.image.action.base.BaseActionSupport;
import org.gameye.psp.image.entity.Image;
import org.gameye.psp.image.service.IImageService;
import org.gameye.psp.image.utils.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;

public class RssImageAction extends BaseActionSupport {

	private static final long serialVersionUID = 5386591929297610305L;
	@Autowired
	private IImageService imageService;

	public String Welcome() {
		return SUCCESS;
	}

	private static Set<Integer> allowInt = null;

	public String ForPSP() {
		if (allowInt == null) {
			allowInt = new HashSet<Integer>();
			allowInt.add(15);
			allowInt.add(20);
			allowInt.add(30);
			allowInt.add(50);
		}
		// 获取请求的用户
		String userId = getServletRequest().getParameter("user");
		if (StringUtils.isEmpty(userId)) {
			return INPUT;
		}
		if (!allowInt.contains(size)) {
			return INPUT;
		}

		return pspRss();
	}

	public String PSP() {
		size = 10;
		return pspRss();
	}

	private String pspRss() {
		if (urlPrefix == null) {
			initUrlPrefix();
		}

		images = imageService.rssImages(size);
		return SUCCESS;
//		StringBuffer sb = new StringBuffer();
//
//		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
//		sb.append("<rss version=\"2.0\">");
//		sb.append("<channel>");
//		sb.append("<title>PSP壁纸分享</title>");
//		sb.append("<link>").append(urlPrefix).append("</link>");
//		sb
//				.append("<description><![CDATA[PSP壁纸,壁纸分享,分享壁纸，为你为我]]></description>");
//		sb.append("<language>zh-CN</language>");
//
//		sb.append("<image>");
//		sb.append("<url>" + urlPrefix + "image/rss_image.png</url>");
//		sb.append("<title>Share Images For You!</title>");
//		sb.append("</image>");
//		sb
//				.append("<copyright>&#xA9; 2008 Forshare.Org,Publicer Yongboy. All rights reserved.</copyright>");
//		String imgUrl = null;
//		String smallImgUrl = null;
//		for (Image img : images) {
//			sb.append("<item>");
//
//			sb.append("<title><![CDATA[");
//			if (StringUtils.isNotEmpty(img.getTitle()))
//				sb.append(img.getTitle());
//			sb.append("]]></title>");
//
//			sb.append("<link>").append(urlPrefix).append("</link>");
//
//			sb.append("<description><![CDATA[");
//			if (StringUtils.isNotEmpty(img.getDescription())
//					&& !img.getDescription().trim().equals("null")) {
//				sb.append(img.getDescription());
//			}
//			sb.append("]]></description>");
//
//			sb.append("<author>");
//			if (img.getUser() != null) {
//				sb.append(img.getUser().getId());
//			}
//			sb.append("</author>");
//			// EEE, d MMM yyyy HH:mm:ss Z
//			sb.append("<pubDate>").append(
//					DateHelper.formatDate(img.getDate(),
//							"EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH))
//					.append("</pubDate>");
//
//			imgUrl = urlPrefix + "images/" + img.getPath() + img.getNowName();
//			sb.append("<enclosure url=\"" + imgUrl + "\"");
//			// 图片的length 和 type可以不用输出
//			// sb.append("length="500000" type="image/jpeg" />";
//			sb.append(" />");
//			smallImgUrl = urlPrefix + "images/" + img.getPath() + "thumbnail/"
//					+ img.getNowName();
//			// media 可以不输出
//			sb.append("<media:thumbnail url = \"");
//			sb.append(smallImgUrl);
//			sb.append("\" width=\"80\" height=\"45\" />");
//
//			// .append(
//			// "<a href=\"#\"><img src=\"").append(urlPrefix).append(
//			// "images/").append(img.getNowName()).append(
//			// "\" width=\"480px\" height=\"272px\" border=\"0\"></a>");
//
//			sb.append("</item>");
//		}
//
//		sb.append("</channel>");
//		sb.append("</rss>");
//
//		printResponseMes(sb.toString());
	}

	public String PC() {

		size = 10;
		images = imageService.rssImages(size);

		if (urlPrefix == null) {
			initUrlPrefix();
		}

		// getServletRequest().setAttribute("urlPrefix", urlPrefix);

		return SUCCESS;

		/*
		 * 
		 * StringBuffer sb = new StringBuffer();
		 * 
		 * sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		 * sb.append("<rss version=\"2.0\">"); sb.append("<channel>");
		 * sb.append("<title>PSP壁纸分享</title>");
		 * sb.append("<link>").append(urlPrefix).append("</link>"); sb
		 * .append("<description><![CDATA[PSP壁纸,壁纸分享,分享壁纸，为你为我]]></description>"
		 * ); sb.append("<language>zh-CN</language>");
		 * 
		 * for (Image img : images) { sb.append("<item>");
		 * sb.append("<title><![CDATA["); if
		 * (StringUtils.isNotEmpty(img.getTitle())) sb.append(img.getTitle());
		 * else sb.append(""); sb.append("]]></title>");
		 * sb.append("<pubDate>").append(img.getDate().toString()).append(
		 * "</pubDate>"); sb.append("<author>"); if (img.getUser() != null) {
		 * sb.append(img.getUser().getId()); } sb.append("</author>");
		 * sb.append("<link>").append(urlPrefix).append("</link>");
		 * sb.append("<description><![CDATA[").append(
		 * "<a href=\"#\"><img src=\"").append(urlPrefix).append(
		 * "images/").append(img.getPath() + img.getNowName()).append(
		 * "\" width=\"480px\" height=\"272px\" border=\"0\"></a>");
		 * 
		 * if (StringUtils.isNotEmpty(img.getDescription()) &&
		 * !img.getDescription().trim().equals("null")) { sb.append("<br />" +
		 * img.getDescription()); } sb.append("]]></description>");
		 * sb.append("</item>"); }
		 * 
		 * sb.append("</channel>"); sb.append("</rss>");
		 * 
		 * printResponseMes(sb.toString());
		 */

	}

	private void initUrlPrefix() {
		urlPrefix = "http://" + getServletRequest().getServerName();
		if (getServletRequest().getServerPort() != 80) {
			urlPrefix += ":" + getServletRequest().getServerPort();
		}
		if (!urlPrefix.endsWith("/"))
			urlPrefix += "/";
	}

	private static String urlPrefix;

	private int size;

	private List<Image> images;

	public List<Image> getImages() {
		return images;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getUrlPrefix() {
		return urlPrefix;
	}

}
