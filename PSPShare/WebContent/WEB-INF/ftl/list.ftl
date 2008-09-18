<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>日志列表</title>
<link href="style/douban1880.css" rel="stylesheet" type="text/css" />
</head>
<body>
<#list logList as log>
<div>
${log.title}
<p>
${log.content}
<div style="float:right"><a href='del.do?id=${log.id}'>删除</a></div><div>&nbsp;</div>
</p>
</div>
	     <div class="fdiv">
		<div style="color:#777;float:left;"></div>		
	</div>	
</#list>


<br>
日志添加:
<form action="add.do" method="post">
<div>
	日志标题<br>
	<input type="text" name="log.title"><br>
日志内容<br>

<textarea name="log.content" rows="10" cols="50"></textarea>
<br>
<input type="submit" value="提交">
</div>
</form>
<br>
<br>
<br>

</body>
</html>