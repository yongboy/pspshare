<%@ page language="java" contentType="text/xml;charset=UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<rss version="2.0">
<channel>
<title>PSP壁纸分享</title>
<link>http://forshare.org</link>
<description><![CDATA[PSP壁纸,壁纸分享,分享壁纸，为你为我]]></description>
<language>zh-CN</language>

<#list images as img>
<item>
<title><![CDATA[<#if img.title??>${img.title}<#else>暂无</#if>]]></title>
<pubDate>${img.date?string("yyyy-MM-dd HH:mm")}</pubDate>
<author><#if img.author! != '0'> ${img.author!}</#if></author>
<link>http://hzhfm.fotolog.com.cn/2051220.html</link>
<description><![CDATA[<a href="#"><img src="images/${img.nowName}" width="480px" height="272px" border="0"></a><br /><#if img.description! != ''>${img.description!}<#else>无</#if>]]></description>
</item>
</#list>

</channel>
</rss>