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
</head>
<body class="com">

<div class="form">
    <form>
        <div class="text">
            <label><input type="text" placeholder="输入您的手机号" id="mobile"/><span class="jz"></span></label>
            <label><input type="password" placeholder="＊＊＊＊＊＊" id="password"/><span class="jz"></span></label>
            <div class="yz">
                <label><input type="text" placeholder="请输入验证码" id="code" /><span class="jz"></span></label>
                <label><input type="button" value="获取验证码" id="btncaptcha" /><span class="jz"></label>
            </div>
        </div>
        <div class="button">
            <label><input type="button" value="注  册" id="sub" class="input"/></label>
             <label><input type="button" value="APP下载" id="download" class="input" /></label>
              <label><input type="hidden" value="${userId}" id="idString" /></label>
           <%--  <label><a class="input" href="<%=path%>/client/download.do"/>APP下载</a></label> --%>
        </div>
    </form>
</div>

</body>
<script type="text/javascript">
$(function(){
    var width=$(window).width();
    if(width>414){
      width=375;
    }
    size=width/640*100+"px";
    $("html").css("font-size",size);
 });   


</script>

<script type="text/javascript">

 

	CDT = {
			smsSent: false,
			countDownNum:0,
			counter:void 0,
			formSent : true
		};
	
		function countingFunc(){
			if(CDT.countDownNum>0){
				$('#btncaptcha').text(--CDT.countDownNum+'秒后可重发');
			}else{
				$('#btncaptcha').removeAttr('disabled').text('重新发送');
				clearInterval(CDT.counter);
				CDT.counter = void 0;
				CDT.countDownNum = 0;
			}
		}
      var shadow=document.getElementById("shadow");
      $("#download").click(
  	    	function () {
  	    			location.href="<%=path%>/client/download.do";
  	    		}

  	    );
      $("#sub").click(function(){
      	// 校验表单参数
			if (validator_form()) {
				if(confirm("确认注册")){
					var clientJson ={
							"phone":$("#mobile").val(),
							"verifyCode":$("#code").val(),
							"token":$("#password").val(),
							"bizId":$("#idString").val()
					}
					$.ajax({
						type:'post',
						url:'<%=path%>/client/register.do',
						data:{
							"phone":$("#mobile").val(),
							"verifyCode":$("#code").val(),
							"password":$("#password").val(),
							"parentClientId":$("#idString").val(), 
							"token":"token"
					},
						dataType:'json',
						success:function(data){
							console.log(data);

							alert(data.msg);
                         if(data.success){
								location.href="<%=path%>/client/download.do";
							}
						}
					});
				}
			}

      });
      
    //获取验证码
		$('#btncaptcha').click(function(){
			CDT.smsSent = false;
			// 当其他校验通过之后才可以发送
			var mobile = $("#mobile").val();
			var regExp = new RegExp("^0{0,1}(13[0-9]|14[6|7]|15[0-3]|15[5-9]|18[0-9]|17[0-9])[0-9]{8}$");
			if(! regExp.test(mobile)){
				alert("手机号码不正确");
				return false;
			}
			$.ajax({
				type:'post',
				url:'<%=path%>/index/verifyPhone.do',
				data:{"phoneCode":mobile},
				dataType:'json',
				success:function(data){
					if(data.success){
						$('#btncaptcha').attr('disabled','disabled');
						CDT.countDownNum = 60;
						CDT.counter = setInterval(countingFunc,1000);
						CDT.smsSent = true;
						alert('验证短信已发送，请稍等！');
					}else{
						alert(data.msg) ;
						return;
					}
				}
			});
			
		});
    
      function validator_form(){
			
			var mobile = $("#mobile").val();
			var regExp = new RegExp("^0{0,1}(13[0-9]|14[6|7]|15[0-3]|15[5-9]|18[0-9]|17[0-9])[0-9]{8}$");
			if(! regExp.test(mobile)){
				alert("手机号码不正确");
				return false;
			}
			
			if($("#code").val()==""){
				alert("请输入验证码");
				return false;
			}
			var password = $("#password").val();
			var regExp = new RegExp("^\\S{6,19}$");
			if(! regExp.test(password)){
				alert("密码至少6位字符");
				return false;
			}
			
			return true;
		}
</script>
</html>