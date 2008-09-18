package com.struts2.filter;

import org.apache.struts2.dispatcher.FilterDispatcher;
import javax.servlet.*;
import java.io.*;

public class Struts2Fileter extends FilterDispatcher {

	 public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
     throws IOException, ServletException{			 
	     req.setCharacterEncoding("UTF-8");
	     req.getParameter("");
		 super.doFilter(req, res, chain);
	 }
}
