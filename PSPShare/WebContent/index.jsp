<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="style/global.css" rel="stylesheet"></link>
<link href="style/share.css" rel="stylesheet"></link>
<title>PSP 图片分享</title>
</head>
<body>
<div class="topdiv">
<table width="480" height="272" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="66" colspan="2">

<jsp:include page="/head.jsp"></jsp:include>

</td>
  </tr>
  <!--tr>
    <td>&nbsp;</td>
    <td></td>
  </tr-->
  <tr>
    <td height="152" valign="top"><span style="float:left;"><a href="Image_List.do"><img src="image/home_explore.png" width="230" height="152"></a></span></td>
    <td valign="top"><span style="float:right;"><a href="upload.do"><img src="image/home_upload.png" width="230" height="152"></a></span></td>
  </tr>
  <tr>
    <td valign="middle" align="right">    
      <a href="about.html"><img src="image/home_tour_button.png" width="195" height="39"></a>   </td>
   <td><div style="font-size:9px; margin-left:5px;">浏览一下有关分享的创意，或许您有更好的建议，或许您也想参与进来，为他人，为乐趣 :))</div></td>
  </tr>
</table>
<jsp:include page="/foot.jsp"></jsp:include>
</div>
</body>
</html>