<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/common/head.inc.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>门户展示</title>
<link href="${ctx}/portal/css/portal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	var constans = {pageNo:1,pageSize:6};
</script>
<script type="text/javascript" src="${ctx}/portal/js/jq.js"></script>
<script type="text/javascript" src="${ctx}/portal/js/macDock_v3.js"></script>
<script type="text/javascript" src="${ctx}/portal/js/utils.js"></script>
<script type="text/javascript" src="${ctx}/portal/js/jquery.mousewheel.js"></script>
<script type="text/javascript" src="${ctx}/portal/js/returnCodeChart.js"></script>
<script type="text/javascript" src="${ctx}/portal/js/businessChart.js"></script>
<script type="text/javascript" src="${ctx}/portal/js/delayChart.js"></script>
<script type="text/javascript" src="${ctx}/portal/js/heartChart.js"></script>
<script type="text/javascript" src="${ctx}/portal/js/box.js"></script>
<script type="text/javascript" src="${ctx}/portal/js/dock.js"></script>
<script type="text/javascript" src="${ctx}/portal/js/toolbar.js"></script>
<script type="text/javascript" src="${ctx}/portal/js/chart_v1.js"></script>
<script type="text/javascript" src="${ctx}/portal/js/style.js"></script>
<script type="text/javascript">
	var menus = [];
	var taskMenus = [
	                 {id:'charts',title:'健康报告图表合集',icon:'OCS'},
	                 {id:6,title:'消息查询',url:'debug/debug.jsp',icon:'messageSearch'},
	                 {id:8,title:'功能测试',url:'testDcc/testDcc.jsp',icon:'testDcc'},
	                 {id:9,title:'场景测试',url:'simulator/dccSimulator.jsp',icon:'simulator'},
	                 {id:11,title:'自动化测试',url:'automation/automation.jsp',icon:'automation'},
	                 {id:14,title:'任务管理',url:'task/task.jsp',icon:'task'},
	                 {id:10,title:'性能测试',url:'performance/performance.jsp',icon:'performance'},
	                 {id:15,title:'HDFS',url:'automation/automation.jsp',icon:'hdfs'},
	                 {id:16,title:'权限、角色、用户管理',url:'task/task.jsp',icon:'privilegeManager'},
	                 {id:17,title:'远程登录工具',url:'automation/automation.jsp',icon:'cmd'},
	                 {id:'setting',title:'设置',url:'performance/performance.jsp',icon:'setting'}
	                 ];
</script>
</head>
<body unselectable="on" onselectstart="return false;" style="-moz-user-select:none;">
	<script type="text/javascript" src="${ctx}/public/ECharts/echarts-plain.js"></script>
	<div id="banner">
		<div id="logobar"></div>
		<div id="toolbar"></div>
		<div id="logout"></div>
		<div id="staffInfo">欢迎登陆!</div>
	</div>
</body>
</html>