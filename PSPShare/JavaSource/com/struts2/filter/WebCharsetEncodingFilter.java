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

public class WebCharsetEncodingFilter implements Filter {
	// private FilterConfig config = null;

	private String defaultEncode = "UTF-8";

	public void init(FilterConfig config) throws ServletException {
		if (config.getInitParameter("Charset") != null) {
			defaultEncode = config.getInitParameter("Charset");
		}
	}

	public void destroy() {
		// this.config = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		try {
			req.setCharacterEncoding(defaultEncode);
			chain.doFilter(request, response);

		} catch (Exception ce) {
		}
	}
}
