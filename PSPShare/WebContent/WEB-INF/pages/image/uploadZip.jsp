<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri ="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>上传图片</title>
</head>
<%
String url = "UploadZip.do";
String userAgent = request.getHeader("user-agent");
if(userAgent.indexOf("PSP") != -1){
	url = "PSP_UploadZip.do";
}
%>
<body>
<div class="div">
<div style="color:red;"><s:fielderror /><s:property value="typeError" /></div>
<div style="margin-bottom:10px;"><span style="float:left"><a href="upload.do">普通上传</a></span><span style="margin-left:20px;">ZIP 打包上传</span></div>
<form class="form" name="upForm" method="post" enctype="multipart/form-data" action="<%=url %>" style="margin:0;">
			  <div id="myFileDiv">
              请选择PSP壁纸ZIP格式压缩包 :<br />
			    <input name="myFile" size="42" type="file" class="btn"/>
			  </div>
			  <p><span >
			    <input name="submit" type="submit" class="btn" id="submitBtn" value="选择完毕，开始上传" />
			  </span> 
			    <input name="reset" type="reset"  class="btn" value=" 重置 " />
			  </p>
<s:token />
  </form>
</div>
</body>
</html>
