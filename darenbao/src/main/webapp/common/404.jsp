<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
   
%>
<c:set var="ctx" value="<%=basePath %>" />
<html>
<head>
    <title>404 NOT FOUND</title>
</head>
<body>
<h2>找不到页面</h2>
<div style="display: none;">
<h2>本来自己定义了一个错误页面，不过在ie中怎么也显示不出来，谷歌浏览器一点问题都没有，
几经查询才知道原来是ie自作聪明给换掉了， 对错误页面的处理在ie来看页面大小<1024b 会被认为十分不友好，
所以ie就将改页面给替换成自己的错误提示页面了，解决办法就是充实一下页面,让大小超过1024即可</h2>
</div>
</body>
</html>
