<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>��־�б�</title>
<link href="style/douban1880.css" rel="stylesheet" type="text/css" />
</head>
<body>
<#list logList as log>
<div>
${log.title}
<p>
${log.content}
<div style="float:right"><a href='del.do?id=${log.id}'>ɾ��</a></div><div>&nbsp;</div>
</p>
</div>
	     <div class="fdiv">
		<div style="color:#777;float:left;"></div>		
	</div>	
</#list>


<br>
��־���:
<form action="add.do" method="post">
<div>
	��־����<br>
	<input type="text" name="log.title"><br>
��־����<br>

<textarea name="log.content" rows="10" cols="50"></textarea>
<br>
<input type="submit" value="�ύ">
</div>
</form>
<br>
<br>
<br>

</body>
</html>