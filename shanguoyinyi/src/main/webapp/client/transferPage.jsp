<%@page contentType="text/html; charset=utf-8" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

java.util.Map<String, Object> result = (java.util.Map<String, Object>)request.getAttribute("result");
if(result==null)
	result = new java.util.HashMap<String, Object>();
%>
<!DOCTYPE html>
<html>
<head>
    <meta name="format-detection" content="telephone=no" />
    <meta charset="UTF-8">
    <title>最特色</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <script src="js/jquery-2.1.4.min.js"></script>
    <script src="js/fea.js"></script>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
  
</head>
<body class="register">
<div class="text">
    <p>最特色移动互联网全球创业平台，探索出“F2C + O2O + VIP会员制电商”商业模式，开创国内会员倍增式消费型创业，0门槛，0风险，消费一次就创业！</p>
</div>

<div class="content">
    <%-- <div class="Personal_info">
        <a><img src="<%=path%>/upload.do?getthumb=${result.ICON}&size=90"></a>
        <ol>
            <dd>${result.CLIENT_NAME}</dd>
            <dt>${result.PHONE}</dt>
        </ol>
        {MONEY=9000.0, PROVICE=1147, levelMoney=99.0, CLIENT_NAME=MrZzz, PHONE=13860472997, SEX=1, STATE_TIME=2016-03-18 18:16:14.0, STATE=00A, 
        LEVEL_ID=2, IS_PROVICE_PROXY=2, PARENT_CLIENT_ID=-1, ICON=1458278962863.jpg, SUB_NUMS=0, CLIENT_NUMBER=13860472997, AGE=99999, 
        levelIcon=1458280530378.png, BANK_NUMBER=56666, BIZ_ID=1011, levelName=充值99, REGION=1163, CREATE_DATE=2016-03-17 15:55:45.0, 
        PROVICE_PROXY=1, PASSWORD=321, CITY=1162, BANK_USER=国家级, BANK_AREA=中国建设银行}
    </div> --%>
     	<div class="potrait">
						<div class="potrait-left">
							<img src="<%=path%>/upload.do?getthumb=<%=result.get("icon") %>&size=90">
						</div>
						<div class="potrait-right">
						     <p><%=result.get("clientName") %></p>
						     <p><input type="text" style="font: 0.24rem/0.42rem '宋体';background:transparent;border:1px solid #ffffff ;border:0px;" id="phone" value="<%=result.get("phone") %>" ></p>
						</div>
					</div>
					
    <div class="ul">
        <div class="li"><span class="top">会员等级</span><span class="botton"><%=result.get("levelName") %></span></div>
        <span class="fg"></span>
        <div class="li"><span class="top">总销售额排名</span><span class="botton">0</span></div>
    </div>

</div>

<div class="btn">
<div class="regin"></div>
    <a class="regin" href="<%=path%>/client/complete_register.do?userId=<%=result.get("clientNumber") %>">注册</a>
    <span class="fg"></span>
    <a class="dow" href="<%=path%>/client/download.do">下载</a>
</div>

</body>

<script type="text/javascript">
$(function(){ 
	
    var phone = $("#phone").val();
    var mphone =phone.substr(3,4);
    var lphone = phone.replace(mphone,"****");
    $("#phone").attr("value", lphone);
    });
</script>

</html>