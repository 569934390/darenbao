<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>500 - 系统内部错误</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
</head>
<body>
	<div style="overflow: auto">
        <h2>500 - 系统发生内部错误</h2>
        <br />
        <div>错误详细信息：</div>
        <div class="alert alert-error">
<%--             <%=ex.getClass().getName() + "(" + ex.getLocalizedMessage() + ")"%><br />
            <%
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                out.print(sw);
            %>
 --%>        </div>
        <a class="btn btn-primary" href="${ctx}/main/index.jsp?theme=classic">返回首页</a>
    </div>
</body>
</html>