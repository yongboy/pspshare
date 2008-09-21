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

    <div style="margin-top:0px;">
    <div style="float:left; width:80%;">
    <img src="image/share.png">PSP 壁纸分享 因为分享，所以精彩</div>
    <div style="float:right;vertical-align:text-bottom; margin-top:42px;"><!-- a href="#">注册</a> |  <a href="#">登录</a--></div>
    </div>

<decorator:body />
<div></div>
<div style="width:480px; height:auto; color:#CCCCCC; background-color:#F5F5F5;">
<div><a href="/">首页</a>  |  <a href="upload.do"> 上传</a>  |  <a href="Image_List.do">全部</a><!-- |  <a href="image/#"> 风景</a> |  <a href="image/#">人物</a> | <a href="image/#"> 动漫</a> |  <a href="image/#">美女</a> | <a href="image/#">游戏</a> |  <a href="image/#">艺术</a> |  <a href="image/#">娱乐</a> | <a href="image/#">影视</a> |  <a href="image/#">其它</a> --><br>
Copyright @ 2008 gameye.org. All rights reserved
</div>
</div>
</div>
</body>
</html>