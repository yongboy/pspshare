<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><decorator:title default="PSP Image Share For You !" /></title>
<decorator:head />
</head>
<body>
<div class="topdiv">
<%
String userAgent = request.getHeader("user-agent");
if(userAgent.indexOf("PSP") == -1){
%>
<jsp:include page="/head.jsp"></jsp:include>
<%} %>
<decorator:body />
<div>&nbsp;</div>
<div>
<!-- foot div -->
<jsp:include page="/foot.jsp"></jsp:include>
</div>
</div>
</body>
</html>