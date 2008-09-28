package org.gameye.psp.image.config;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class InitParameters extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public InitParameters() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		if (Constants.getWebRootPath() == null) {
			String appRootPath = config.getServletContext().getRealPath("/");
			Constants.setWebRootPath(appRootPath);
		}
	}

	public void destroy() {
		super.destroy();
	}
}
