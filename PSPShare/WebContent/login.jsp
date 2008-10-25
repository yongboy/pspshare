<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri ="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="login.title" /></title>
<style>
.login{width:480px;height:272px;}
.login input{ width:160px;height:18px;}
.password{ width:255px;}
</style>
</head>
<body>
<form method="post" action="CheckLogin.do">
<div class="login">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><font color="#FF0000"><b><s:property value="error" /></b></font><br />&nbsp;</td>
  </tr>
  <s:if test="#session.user == null">
  <tr>
    <td width="15%"></td>
    <td width="85%"><label><s:text name="login.name" /><br>

    <input type="text" name="user.id">
    </label></td>
  </tr>
  <tr>
    <td></td>
    <td><label><s:text name="login.pass" /><br>

      <input class="password" type="password" name="user.password">
    </label></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><label></label></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><input type="submit" value="<s:text name="login.button" />" style="width:90px; height:25px;"></td>
  </tr> 
    <tr>
    <td>&nbsp;</td>
    <td><label></label></td>
  </tr>
    <tr>
    <td>&nbsp;</td>
    <td><label></label></td>
  </tr>
    <tr>
    <td>&nbsp;</td>
    <td><label></label></td>
  </tr>
    <tr>
    <td>&nbsp;</td>
    <td><label><s:text name="register.tip" /></label></td>
  </tr>
   </s:if>
  <s:else>
  	<tr>
    <td></td>
    <td><label><s:text name="login.enter" /></label></td>
  </tr>
  </s:else>
</table>
</div>
</form>
</body>
</html>