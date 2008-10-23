<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri ="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><s:text name="upload.title1" /></title>
<script src="script/form.js"></script>
</head>
<%
String url = "UploadImage.do";
String version = "PC";
String userAgent = request.getHeader("user-agent");
if(userAgent.indexOf("PSP") != -1){
	url = "PSP_UploadImage.do";
	version = "PSP";
}
%>
<body>
<div class="div">
<div style="color:red;"><s:fielderror /><s:property value="typeError" /></div>
<div style="margin-bottom:10px;"><span style="float:left"><s:text name="upload.tip_title1" /></span><span style="margin-left:20px;"><a href="uploadZip.do"><s:text name="upload.tip_title2" /></a></span></div>
<form class="form" name="upForm" method="post" enctype="multipart/form-data" action="<%=url %>" style="margin:0;">
			  <div id="myFileDiv">
			    <input name="myFile" size="42" type="file" class="btn"/>			   
			    <%if(!version.equals("PSP")){ %><input type="button" onClick="addUpFile()" value="<s:text name="upload.add_file" />" class="btn"><%} %>
			    <br>
			  <input name="myFile" size="42" type="file" class="btn"/><br />
<input name="myFile" size="42" type="file" class="btn"/><br />
<input name="myFile" size="42" type="file" class="btn"/><br />
<input name="myFile" size="42" type="file" class="btn"/>
			  </div>
			  <p><span >
			    <input name="submit" type="submit" class="btn" value="<s:text name="upload.submit" />" />
			  </span> 
			    <input name="reset" type="reset"  class="btn" value=" <s:text name="button.reset" />  " />
			  </p>
<s:token />
  </form>
</div>
</body>
</html>
