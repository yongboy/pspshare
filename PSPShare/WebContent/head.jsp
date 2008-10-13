<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<div style="margin-top:0px;">
    <div style="float:left; width:80%;">
    <img src="/image/share.png">PSP 壁纸分享 因为分享，所以精彩</div>
    <div style="float:right;vertical-align:text-bottom; margin-top:42px;">
<s:if test="#session.user != null">您好： <a href="/my/Welcome.do" title="进入我的中心！"><b><s:property value="#session.user.id" /></b></a> !</s:if>
<s:else><a href="/login.jsp">登录</a></s:else></div>
    </div>    
</body>
</html>