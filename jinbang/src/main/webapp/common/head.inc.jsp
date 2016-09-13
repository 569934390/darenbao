<%@page pageEncoding="UTF-8" %>
<%@page import="com.compses.util.JsonUtils"%>
<%@page import="java.util.List"%>
<%@page import="com.compses.common.Constants"%>
<%
	String user=JsonUtils.toJson(request.getSession().getAttribute(Constants.STAFF));
%>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link rel="shortcut icon" href="${ctx}/common/images/favicon.ico" />
<link href="${ctx}/common/css/css.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/common/css/toolbarCss.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${ctx}/common/md5.js"></script>
<script type="text/javascript" src="${ctx}/common/uuid.js"></script>
<script type="text/javascript">
	Ext = null;
	var session = new Object();
	session.user = eval('obj=<%=user%>;');
	if (typeof window.console == "undefined") {
		console = function() {};
		console.info = function() {};
		console.warm = function() {};
		console.error = function() {};
		console.debug = function() {};
	}
	var ctx = "${ctx}";
	var webRoot=ctx+"/";
	var sqlmapPrefix="com.compses.dao.sqlmap.";
	Date.patterns = {
		ISO8601Long : "Y-m-d H:i:s",
		ISO8601LongWeekDay:'l Y-m-d H:i:s',
		ISO8601LongString:"YmdHis",
		ISO8601Short : "Y-m-d",
		ShortDate : "n/j/Y",
		LongDate : "l, F d, Y",
		FullDateTime : "l, F d, Y g:i:s A",
		MonthDay : "F d",
		ShortTime : "g:i A",
		LongTime : "g:i:s A",
		Long24Time : "H:i:s",
		SortableDateTime : "Y-m-d\\TH:i:s",
		UniversalSortableDateTime : "Y-m-d H:i:s",
		YearMonth : "F, Y"
	};
</script>