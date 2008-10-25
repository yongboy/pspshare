<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div style="margin-top:0px;">
    <div style="float:left; width:80%;">
    <img src="/image/share.png"><s:text name="head.title" /></div>
    <div style="float:right;vertical-align:text-bottom; margin-top:42px;">
<s:if test="#session.user != null">
<s:text name="head.login.welcome">
	<s:param><s:property value="#session.user.id" /></s:param>
</s:text>
</s:if>
<s:else><a href="/login.jsp"><s:text name="login.button" /></a></s:else></div>
    </div>