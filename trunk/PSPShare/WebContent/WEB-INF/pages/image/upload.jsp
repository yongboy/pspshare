<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri ="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>上传图片</title>
<link href="style/global.css" rel="stylesheet"></link>
<link href="style/share.css" rel="stylesheet"></link>
<script src="script/form.js"></script>
</head>
<div class="div">
<div style="color:red;"><s:fielderror /></div>
上传图片：
<form class="form" name="upForm" method="post" enctype="multipart/form-data" action="UploadImage.do" style="margin:0;">
			  <div id="myFileDiv">
			    <input name="myFile" size="42" type="file" class="btn"/>			   
			    <input type="button" onClick="addUpFile()" value="增加文件" class="btn">
			    <br>
			  <input name="myFile" size="42" type="file" class="btn"/><br />
<input name="myFile" size="42" type="file" class="btn"/><br />
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
