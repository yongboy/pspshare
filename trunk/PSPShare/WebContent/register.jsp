<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri ="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="register.title" /></title>
<style>
.login{width:480px;height:272px;}
.login input{ width:160px;height:18px;}
.login td{height:50px;}
</style>
</head>
<body>
<form method="post" action="Register.do">
<div class="login">
<span><s:text name="register.title" /></span>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>&nbsp;</td>
    <td colspan="2"><font color="#FF0000"><b><s:property value="error" /></b></font></td>
  </tr>
  <tr>
    <td width="15%">&nbsp;</td>
    <td width="85%" colspan="2"><label>    <strong><s:text name="login.name" /></strong><br>
    <input type="text" name="user.id">
    <s:text name="register.name.tip" /></label></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><label><strong><s:text name="login.pass" /></strong><br>
      <input style=" width:120px;" type="password" name="user.password">
      <br>
      <s:text name="register.pass.tip" />
    </label></td>
    <td><strong><s:text name="register.pass2" /></strong>
      <label>
      <br>
      <input style=" width:120px;" type="password" name="password2">
      </label>
      <br></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td colspan="2"><label><strong><s:text name="register.mail" />
        </strong><br>
        <input type="text" name="user.mail">        
        <br>
    </label></td>
  </tr>
    <tr>
    <td>&nbsp;</td>
    <td colspan="2"><label>
      <input type="submit" value="<s:text name="register.button" /> " style="width:90px; height:25px;">
    </label></td>
  </tr>
    <tr>
    <td>&nbsp;</td>
    <td colspan="2"><label></label></td>
  </tr>    
</table>
</div>
</form>
</body>
</html>