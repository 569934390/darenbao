<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html  lang="cn" manifest="${ctx }/cache.manifest">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Dcc消息运维平台</title>
<script type="text/javascript">
var ctx = "${ctx}";
if(window.top.location.href!=ctx+'/main/login.jsp'){
	window.top.location.href=ctx+'/main/login.jsp';
}
</script>
<%@ include file="/common/head.ext4.inc.jsp"%>
<script type="text/javascript" src="js/login.js"></script>
<style>
* {
	padding:0;
	margin:0;
}
.bg {
	background:url(img/login_01.jpg) repeat-x top;
	height:500px;
	text-align:center;
}

.banner {
	background:url(img/banner.png) no-repeat center;
	width:432px;
	height:332px;
	float:left;
}

.form-block {
	background:url(img/form.png) no-repeat center;
	width:380px;
	height:160px;
	float:right;
	margin-top:245px;
	position:relative;
}

.inner-block {
	width:885px;
	margin:0 auto;
	padding-top:110px;
}

.login_button {
	width:95px;
	height:57px;
	border:none;
	background:none;
	position:absolute;
	top:71px;
	right:3px;
}

.cursor{
	cursor:pointer;
}

.input1,.input2 {
	width:192px;
	height:23px;
	line-height:22px;
	padding:0 5px;
	border:none;
	background:none;	
	outline:none;
}

.input1 {
	position:absolute;
	top:72px;
	left:59px;
}

.input2 {
	position:absolute;
	top:102px;
	left:59px;
}

.text-1 {
	background:url(img/text_01.png) no-repeat center;
	position:absolute;
	width:535px;
	height:80px;
	top:-90px;
	left:-50px;
}

.text-2 {
	background:url(img/text_02.png) no-repeat center;
	position:absolute;
	width:330px;
	height:14px;
	top:200px;
	left:100px;	
}
</style>
</head>
<body>
	<div class="bg">
		<div class="inner-block">
			<!-- TemplBeginEditable name="body" -->	
				<div class="banner"></div>
				<div class="form-block">
					<div class="form-block-panel">
						<form id="loginForm" onSubmit="return false;">
							<input id="username" type="text" class="input1" style="behavior:url(#default#userData);border:0；" value="" size="13" maxlength="20">
							<input id="password" type="password" class="input2" size="13" maxlength="20"  style=" border:0；"> 
							<input type="submit" id="login" onClick="loginOper.login()" class="login_button cursor" value=" "> 
						</form>
						<div class="text-1"></div>
						<div class="text-2"></div>
					</div>
				</div>
			<!-- TemplEndEditable -->
		</div>
	</div>
</body>
</html>