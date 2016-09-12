<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.compses.common.Configuration"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.compses.util.JsonUtils"%>
<%@page import="java.util.List"%>
<%@page import="com.compses.common.Constants"%>
<%
	String user=JsonUtils.toJson(request.getSession().getAttribute(Constants.STAFF));
	Map<String,Object> appconfig=new HashMap<String,Object>();
	Iterator<String> keys=Configuration.getKeys();
	while(keys.hasNext()){
		String key=keys.next();
		appconfig.put(key, Configuration.getProperty(key));
	}
	
	String resources=JsonUtils.toJson(request.getSession().getAttribute(Constants.RESOURCE_PRIVILEGE_LIST));
%>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link rel="shortcut icon" href="${ctx}/common/images/favicon.ico" />
<link href="${ctx}/public/ext-4.2.1/resources/css/ext-all.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/common/css/css.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/common/css/ext-ie-patch.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/common/css/toolbarCss.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${ctx}/public/ext-4.2.1/examples/shared/include-ext.js"></script>
<%-- <script type="text/javascript" src="${ctx}/public/ext-4.2.1/examples/shared/options-toolbar.js"></script> --%>
<script type="text/javascript" src="${ctx}/public/ext-4.2.1/locale/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/public/ext-4.2.1/grid-copy-clipboard.js"></script>
<script type="text/javascript" src="${ctx}/common/constants.js"></script>
<script type="text/javascript" src="${ctx}/common/md5.js"></script>
<script type="text/javascript" src="${ctx}/component/view/widget/Persistent.js"></script>
<script type="text/javascript" src="${ctx}/common/uuid.js"></script>
<script type="text/javascript">
	var session = new Object();
	session.user = Ext.decode('<%=user%>');
	session.resources = Ext.decode('<%=resources%>');
	var appconfig=new Object();
	appconfig=Ext.decode('<%=JsonUtils.toJson(appconfig)%>');
	if (typeof window.console == "undefined") {
		console = function() {};
		console.info = function() {};
		console.warm = function() {};
		console.error = function() {};
		console.debug = function() {};
	}
	Ext.BLANK_IMAGE_URL = '${ctx}/public/ext-4.2.1/resources/ext-theme-access/images/tree/s.gif';
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
	Ext.Loader.setConfig({
		enabled : true,
		disableCaching:true,
		paths : {
			'component' : ctx+'/component',
			'Ext.ux':ctx+'/public/ext-4.2.1/examples/ux',
			'workflow':ctx+'/workflow'
		}
	});
</script>
<script type="text/javascript" src="${ctx}/common/ext-fix.js"></script>
<script type="text/javascript" src="${ctx}/common/WEBConstants.js"></script>
<script type="text/javascript" src="${ctx}/common/ExtUtils.js"></script>