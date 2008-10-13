package com.struts2.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 流程为： 请求图片或者样式表、js文件、首页的时候，会把请求权限直接过滤掉
 * 
 * @author nieyong
 */

public class CheckUserIfLogin implements Filter {

	private static String loginUrl = null;
	private static String sessionName = null;

	private FilterConfig filterConfig = null;

	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
		loginUrl = filterConfig.getInitParameter("loginUrl");
		sessionName = filterConfig.getInitParameter("sessionName");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) {
		HttpServletRequest req = (HttpServletRequest) request;
		try {
			// 判断用户是否登录，在第二次请求时候会检测从资源站点反馈回来的用户信息是否正确
			HttpSession session = req.getSession();
			Object userObj = session.getAttribute(sessionName);

			// 在用户没有登录的情况下，直接转向登录页面
			if (userObj == null) {
				HttpServletResponse res = (HttpServletResponse) response;

				String loginPage = loginUrl;

				loginPage += "?back=";
				String url = req.getQueryString();
				StringBuffer reqUrl = req.getRequestURL();
				if (url != null)
					url = reqUrl.append("?" + url).toString();
				else
					url = reqUrl.toString();

				url = res.encodeURL(url);
				loginPage += url;

				res.sendRedirect(loginPage);
				return;
			}
			filterChain.doFilter(request, response);
		} catch (ServletException sx) {
			filterConfig.getServletContext().log(sx.getMessage());
		} catch (IOException iox) {
			filterConfig.getServletContext().log(iox.getMessage());
		}
	}

	public void destroy() {
	}
}
