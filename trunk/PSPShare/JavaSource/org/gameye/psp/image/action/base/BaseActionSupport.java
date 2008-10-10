package org.gameye.psp.image.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.gameye.psp.image.entity.User;

import com.opensymphony.xwork2.ActionSupport;

public class BaseActionSupport extends ActionSupport implements
		ServletRequestAware, ServletResponseAware {

	public BaseActionSupport() {
		super();
		// ResourceBundle RESOURCE_BUNDLE =
		// ResourceBundle.getBundle("messageResource");
		// ResourceBundleModel rsbm = new
		// ResourceBundleModel(RESOURCE_BUNDLE,new BeansWrapper());
		// ActionContext ctx = ActionContext.getContext();
		// ctx.put("bundle", rsbm);
	}

	private HttpServletRequest request;
	private HttpServletResponse response;

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getServletRequest() {
		return this.request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletResponse getServletResponse() {
		return this.response;
	}

	public void printResponseMes(String str) {
		printResponseMes("text/xml;charset=UTF-8", str);
	}

	/**
	 * 客户端可直接输出JS函数
	 * 
	 * @param str
	 */
	public void printClientJS(String str) {
		printResponseMes("application/x-javascript;charset=UTF-8", str);
	}

	/**
	 * 手动输入到客户端
	 * 
	 * @param contentType
	 *            eg ： text/xml;charset=UTF-8
	 * @param content
	 *            您要输出的内容
	 */
	public void printResponseMes(String contentType, String content) {
		try {
			if (StringUtils.isNotEmpty(content)) {

				getServletResponse().setContentType(contentType);
				getServletResponse().setHeader("Cache-Control", "no-cache");

				PrintWriter out = getServletResponse().getWriter();
				out.print(content);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 当前登陆用户ID
	 * 
	 * @return
	 */
	protected String getCurrentUserUUID() {
		return null;
	}

	/**
	 * 获取当前登陆用户的登陆名称，非用户名
	 * 
	 * @return
	 */
	protected String getCurrentUserLoginName() {
		return null;
	}

	protected User getCurrUser() {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null)
			return null;
		else
			return user;
	}

	protected String json(boolean flag) {
		return json(flag, "");
	}

	protected String json(boolean flag, String input) {
		return "{success : " + flag + ", data : '" + input + "'}";
	}

	/**
	 * 提供一个下载的公共函数
	 * 
	 * @param filePath
	 * @param fileName
	 */
	protected void downloadFile(String filePath, String fileName) {
		try {
			File file = new File(filePath);
			InputStream is = new FileInputStream(file);
			// 只有转化编码才可以被浏览器认识，不至于出现乱码
			// fileName = new
			// String(fileName.getBytes(),"ISO-8859-1");//EncodingUtil.
			// Utf8Toiso88591(fileName);
			HttpServletResponse response = getServletResponse();
			response.setHeader("Content-disposition", "attachment; filename="
					+ fileName);
			int len = -1;
			OutputStream out = response.getOutputStream();
			while ((len = is.read()) != -1) {
				out.write(len);
			}
			is.close();
			out.flush();
			out.close();
		} catch (FileNotFoundException notFound) {
			printResponseMes("您所请求的文件不存在！");
			// logger.log(Level.INFO, "文件下载时，该文件不存在:\n" + notFound.toString()
			// + "\n下载的文件ID为：" + sf.getId());
		} catch (IOException ioe) {
		} catch (Exception e) {
			e.printStackTrace();
			// logger.log(Level.INFO, "文件下载时，出现无法处理的异常：\n" + e.toString()
			// + "\n下载的文件ID为：" + sf.getId());
		}
	}
}
