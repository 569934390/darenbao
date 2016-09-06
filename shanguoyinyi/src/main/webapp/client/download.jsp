<%@page contentType="text/html; charset=utf-8" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>最特色</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <script src="js/jquery-2.1.4.min.js"></script>
    <script src="js/fea.js"></script>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <!-- <link rel="stylesheet" href="css/callCardCss.css"></link> -->
    <style>
          #shadow{
          width:100%;
          height:100%;
          position:fixed;
          z-index:5;
        }
        .markwrap{
        	width: 100%;
        	height: 100%;
        	background-color: #000;
        	opacity: 0.7;
            position: fixed;
            top: 0;
            left: 0;
        }
        .mark{
            z-index: 10;
        	position: fixed;
        	background: url(<%=path%>/client/img/s3.png)no-repeat;
        	width: 4.37rem;
        	height: 2.34rem;
        	background-size: 100% auto;
            top:0.2rem;
            right: 0.35rem;
            font-family: "微软雅黑";
        }
        .mark {
            font-size: 0.32rem;
            color: #fff;
        }
        .wenzi{
            position: absolute;
            left: 0.55rem;
            top:0.97rem;
            line-height: 0.45rem;

        }
   </style>
</head>
<body class="down">
<div class="appPhone"><img src="img/download/app_phone.jpg"/></div>
<div class="btn">
    <a class="ios" href="http://fir.im/rbcu"></a>
    <a class="Android" href="http://fir.im/9a6k"></a>
</div>
<div id="shadow">
   <div class="markwrap"></div>
     <div class="mark">
        <div class="wenzi">
           <p>点击这里</p>
           <p class="android">通过<span>"浏览器"</span>打开</p>
        </div> 
     </div>
</div>
</body>
<script type="text/javascript">
	function isWeiXin(){
	    var ua = window.navigator.userAgent.toLowerCase();
	    if(ua.match(/MicroMessenger/i) == 'micromessenger'){
	        return true;
	    }else{
	        return false;
	    }
	}
	$(function(){
		if(!isWeiXin()){
			$("#shadow").hide();
		}
	})
</script>
</html>