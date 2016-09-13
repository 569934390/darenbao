<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@ include file="/common/head.inc.jsp"%>
<link href="${ctx}/portal/css/portal.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>门户首页</title>
<script type="text/javascript" src="${ctx }/component/view/widget/Functions.js"></script>
<script type="text/javascript" src="${ctx }/portal/lib/jq.js"></script>
<script data-main="${ctx}/portal/app" src="${ctx}/public/amd/require.js"></script>
</head>
<body unselectable="on" onselectstart="return false;" style="-moz-user-select:none;">
	<div id="banner">
		<div id="logobar"></div>
		<div id="toolbar"></div>
		<div id="logout"></div>
		<div id="staffInfo">欢迎登陆!</div>
	</div>
</body>
</html>