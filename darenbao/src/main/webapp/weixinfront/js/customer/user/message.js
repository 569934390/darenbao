Date.prototype.format =function(format){
    var o = {
    "M+" : this.getMonth()+1, //month
	"d+" : this.getDate(),    //day
	"h+" : this.getHours(),   //hour
	"m+" : this.getMinutes(), //minute
	"s+" : this.getSeconds(), //second
	"q+" : Math.floor((this.getMonth()+3)/3),  //quarter
	"S" : this.getMilliseconds() //millisecond
    }
    if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
    (this.getFullYear()+"").substr(4- RegExp.$1.length));
    for(var k in o)if(new RegExp("("+ k +")").test(format))
    format = format.replace(RegExp.$1,
    RegExp.$1.length==1? o[k] :
    ("00"+ o[k]).substr((""+ o[k]).length));
    return format;
}
var uploaderImg=function(){
	var default_options={
        runtimes: 'html5,flash,html4',
        browse_button:'',
        container: '',
        tigger_container:'',
        //chunk_size: '4mb',
		uptoken_url: Util.common.baseUrl+"/weixin/qiniu/getToken.do",
//		uptoken:"jiZNnKPEiZpRe5L5jkkTYnAFJQ1TnJ0GOQnIYwQQ:dadglDQc7pqHHd5BWkUVvJP1Pco=:eyJzY29wZSI6InNoYW5ndW95aW55aSIsImRlYWRsaW5lIjoxNTA3NDE1NDU1fQ==",
        domain: 'http://o7o0uv2j1.bkt.clouddn.com/',
        flash_swf_url : 'qiniu/Moxie.swf',
	    silverlight_xap_url : 'qiniu/Moxie.xap',
        multi_selection: !(mOxie.Env.OS.toLowerCase()==="ios"),
        filters : {
            max_file_size : '100mb',
            prevent_duplicates: false,
            mime_types: [
                   {title : "Image files", extensions : "jpg,jpeg,gif,png,bmp"}
            ]
        },
        get_new_uptoken: false,
        auto_start: true,
        log_level: 5,
        resize : {
            width : 200,
            height : 200,
            quality : 72,
            crop: true // crop to exact dimensions
        },
        init: {
            'FilesAdded': function(up, files) {
            	var op=up.getOption();
				var targetContainer=op.tigger_container;
				 plupload.each(files, function(file) {
					var progress = new FileProgress(file, targetContainer);
					progress.bindUploadCancel(up);     	
					up.refresh();
		                   
				});
            },
            'BeforeUpload': function(up, file) {
            	var op=up.getOption();
//				var targetContainer=op.tigger_container;
//              var progress = new FileProgress(file, targetContainer);
//              var chunk_size = plupload.parseSize(this.getOption('chunk_size'));
//              if (up.runtime === 'html5' && chunk_size) {
//                  progress.setChunkProgess(chunk_size);
//              }
				readyImg(file.id);
            },
            'UploadComplete': function() {
                console.log("ssss");
            },
            'FileUploaded': function(up, file, info) {
                var op=up.getOption();
				var targetContainer=op.tigger_container;
                var progress = new FileProgress(file, targetContainer);
				var url=progress.getCompleteImg(up, info, "-img");
				senImg(url,file.id);
				
            },
            'Error': function(up, err, errTip) {
                var op=up.getOption();
				var targetContainer=op.tigger_container;
                var progress = new FileProgress(err.file, targetContainer);
                progress.setError();
                progress.setStatus(errTip);
            },
           'Key': function(up, file) {
              	var key = "";
             	var timestamp=new Date().getTime();
             	var imgType=file.name.substr(file.name.lastIndexOf(".")).toLowerCase();
             	var randomStr = function(len){
			 		var targetStr="";
			 		var hexDigits = "0123456789abcdef";
			 		for(var i=0;i<len;i++){
			 			targetStr+=hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
			 		}
			 		return targetStr;
             	}
             	key=timestamp+randomStr(7)+imgType;
             	return key;
            }
        }
	};
	var _init=function(options){
		return $.extend(true, default_options, options);
	}
	var _create=function(options){
		var op=_init(options);
		var uploader = Qiniu.uploader(op);
		return uploader;
	}
	return {
		createUploader:function(options){
			_create(options);
		}
	}
};
$(function () {
	customer.message.init();
	$("#sendMsgBtn").on("click",function(e){
		var msg =$("#inputContent").val();
		if(customer.message.msgId !="-1" && msg !=""){
			var url = Util.common.baseUrl + "/weixin/message/news/add.do";
			var modelJson={
				messageId:customer.message.msgId,
				senderId:customer.message.userId,
				content:msg,
				type:'0',
				sendType:'0'
			};
			var param = {
				modelJson:JSON.stringify(modelJson)
			};
			var msgobj=addMsg(modelJson);
			if(msgobj.isShowTime){
				var dateTime=$('<div class="svc_time">'+showDate(msgobj.showTime)+'</div>');
				$("#msgContent").append(dateTime);
			}
			var userMsg=$('<div class="svc_left">'+
							'<div class="svc_left_l svc_right_l "><img src="'+customer.message.userImg+'" /></div>'+
							'<div class="svc_left_r rg_red"><i class="svc_right_icon"></i>'+msg+'</div></div>');
			$("#msgContent").append(userMsg);
			$("#bodyContent").scrollTop($("#msgContent").height());
			Util.common.executeAjaxCallback(url, param, function (data) {
				console.log(data);	
				$("#inputContent").val("");
				if(data.msg){
					var msgobj=addMsg(data.msg);
					if(msgobj.isShowTime){
						var dateTime=$('<div class="svc_time">'+showDate(msgobj.showTime)+'</div>');
						$("#msgContent").append(dateTime);
					}
					var userMsg=$('<div class="svc_left">'+
							'<div class="svc_left_l"><img src="'+customer.message.cusImg+'" /></div>'+
							'<div class="svc_left_r"><i class="svc_left_icon"></i>'+data.msg.content+'</div></div>');
					$("#msgContent").append(userMsg);
					$("#bodyContent").scrollTop($("#msgContent").height());
				}
			});
		}
	});
	$("#sendGoodBtn").on("click",function(e){
		var infoObj=customer.message.infoObj;
		var msg ="<p>"+infoObj.name+"</p>"+
				  "<p>商品编号："+infoObj.id+"</p>"+
				  "<p>货品编号："+infoObj.cargoNo+"</p>";
		if(customer.message.msgId !="-1" && msg !=""){
			var url = Util.common.baseUrl + "/weixin/message/news/add.do";
			var modelJson={
				messageId:customer.message.msgId,
				senderId:customer.message.userId,
				content:msg,
				type:'0',
				sendType:'0'
			};
			var param = {
				modelJson:JSON.stringify(modelJson)
			};
			var msgobj=addMsg(modelJson);
			if(msgobj.isShowTime){
				var dateTime=$('<div class="svc_time">'+showDate(msgobj.showTime)+'</div>');
				$("#msgContent").append(dateTime);
			}
			var userMsg=$('<div class="svc_left">'+
							'<div class="svc_left_l svc_right_l "><img src="'+customer.message.userImg+'" /></div>'+
							'<div class="svc_left_r rg_red"><i class="svc_right_icon"></i>'+msg+'</div></div>');
			$("#msgContent").append(userMsg);
			$("#bodyContent").scrollTop($("#msgContent").height());
			Util.common.executeAjaxCallback(url, param, function (data) {
				console.log(data);	
				$("#inputContent").val("");
				if(data.msg){
					var msgobj=addMsg(data.msg);
					if(msgobj.isShowTime){
						var dateTime=$('<div class="svc_time">'+showDate(msgobj.showTime)+'</div>');
						$("#msgContent").append(dateTime);
					}
					var userMsg=$('<div class="svc_left">'+
							'<div class="svc_left_l"><img src="'+customer.message.cusImg+'" /></div>'+
							'<div class="svc_left_r"><i class="svc_left_icon"></i>'+data.msg.content+'</div></div>');
					$("#msgContent").append(userMsg);
					$("#bodyContent").scrollTop($("#msgContent").height());
				}
			});
		}
	});
})
function readyImg(fileName){
	var msg="<img id='"+fileName+"' src='images/hdpi/loadiing.gif'>";
	if(customer.message.msgId !="-1" && msg !=""){
		var modelJson={
			messageId:customer.message.msgId,
			senderId:customer.message.userId,
			content:msg,
			type:'1',
			sendType:'0',
			fileName:fileName
		};
		var msgobj=addMsg(modelJson);
		if(msgobj.isShowTime){
			var dateTime=$('<div class="svc_time">'+showDate(msgobj.showTime)+'</div>');
			$("#msgContent").append(dateTime);
		}
		var userMsg=$('<div class="svc_left">'+
						'<div class="svc_left_l svc_right_l "><img src="'+customer.message.userImg+'" /></div>'+
						'<div class="svc_left_r rg_red"><i class="svc_right_icon"></i>'+msg+'</div></div>');
		$("#msgContent").append(userMsg);
		$("#bodyContent").scrollTop($("#msgContent").height());
	}
}
function senImg(imgUrl,fileName){
	var msg="<img src='"+imgUrl+"'>";
	if(customer.message.msgId !="-1" && msg !=""){
		var msgInfo=null;
		if(customer.message.msgData){
			var msgData=customer.message.msgData;
			if(msgData.contents){
				for(var i=0;i<msgData.contents.length;i++){
					if(msgData.contents[i].type==1|| msgData.contents[i].type=='1'){
						if(msgData.contents[i].fileName&&msgData.contents[i].fileName){
							msgData.contents[i].content=msg;
							msgInfo=msgData.contents[i];
						}
					}
				}
			}
		}
		$("#"+fileName).attr("src",imgUrl);
		var url = Util.common.baseUrl + "/weixin/message/news/add.do";
		if(msgInfo){
			var param = {
			modelJson:JSON.stringify(msgInfo)
		};
		Util.common.executeAjaxCallback(url, param, function (data) {
			console.log(data);	
			$("#inputContent").val("");
			if(data.msg){
				var msgobj=addMsg(data.msg);
				if(msgobj.isShowTime){
					var dateTime=$('<div class="svc_time">'+showDate(msgobj.showTime)+'</div>');
					$("#msgContent").append(dateTime);
				}
				var userMsg=$('<div class="svc_left">'+
						'<div class="svc_left_l"><img src="'+customer.message.cusImg+'" /></div>'+
						'<div class="svc_left_r"><i class="svc_left_icon"></i>'+data.msg.content+'</div></div>');
				$("#msgContent").append(userMsg);
				$("#bodyContent").scrollTop($("#msgContent").height());
			}
		});
		}
		return;
		
	}
}
function isShowDate(obj){
	for(var i=0;i<obj.length;i++){
		var msgTime=obj[i].sendTime;
		msgTime= msgTime.replace(/\-/g,"/");
		if(i==0){
			obj[i].showTime=msgTime;
			obj[i].isShowTime=true;
		}else{
			var msgDate=new Date(msgTime).getTime();
			var lastDate=new Date(obj[i-1].showTime).getTime();
			var flagDate=msgDate-lastDate;
			if(flagDate>360000){
				obj[i].showTime=msgTime;
				obj[i].isShowTime=true;
			}else{
				obj[i].showTime=obj[i-1].showTime;
				obj[i].isShowTime=false;
			}
		}
	}
	return obj;
}
function concatTime(num){
	if(num<10)
		return "0"+num;
	return num;
}
function showDate(showTime){
	var showDate=new Date(showTime);
	var curDate=new Date();
	var yestoday=new Date();
	yestoday.setDate(curDate.getDate()-1);
	var showD={
		year:showDate.getFullYear(),
		month:showDate.getMonth(),
		day:showDate.getDate()
	}
	var curD={
		year:curDate.getFullYear(),
		month:curDate.getMonth(),
		day:curDate.getDate()
	}
	var yesD={
		year:yestoday.getFullYear(),
		month:yestoday.getMonth(),
		day:yestoday.getDate()
	}
	if(showD.day==curD.day&&showD.month==curD.month&&showD.year==curD.year){
		return "今天 "+concatTime(showDate.getHours())+":"+concatTime(showDate.getMinutes());
	}else if(showD.day==yesD.day&&showD.month==yesD.month&&showD.year==yesD.year){
		return "昨天 "+concatTime(showDate.getHours())+":"+concatTime(showDate.getMinutes());
	}

	return concatTime(showDate.getMonth()+1)+"-"+concatTime(showDate.getDate())+" "+concatTime(showDate.getHours())+":"+concatTime(showDate.getMinutes());
}
function addMsg(msg){
	var curDate=new Date().format("yyyy-MM-dd hh:mm:ss");
	msg.sendTime=curDate;
	var msgTime= curDate.replace(/\-/g,"/");
	var msgData=customer.message.msgData;
	if (msgData){
		if(msgData.contents){
			var len=msgData.contents.length;
			var content =msgData.contents[len-1];
			var msgDate=new Date(msgTime).getTime();
			var lastDate=new Date(content.showTime).getTime();
			var flagDate=msgDate-lastDate;
			if(flagDate>360000){
				msg.showTime=msgTime;
				msg.isShowTime=true;
			}else{
				msg.showTime=content.showTime;
				msg.isShowTime=false;
			}
			msgData.contents.push(msg);
		}else{
			var contents=[];
			msg.showTime=msgTime;
			msg.isShowTime=true;
			contents.push(msg);
			msgData.contents=contents;
		}
		customer.message.msgData=msgData
	}
	return msg;
}

var customer = customer || {};
customer.message = {
	userId:'-1',
	shopId:'-1',
	goodId:'-1',
	msgId:"-1",
	msgData:{},
	userImg:'',
	cusImg:'',
	infoObj:{},
	init:function(){
		customer.message.goodId = Util.common.getParameter("goodId");
		customer.message.userId = localStorage.getItem("userid");
//		customer.message.userId ="255687948111192064";
		customer.message.shopId = localStorage.getItem("shopId");
//		customer.message.shopId="277050247008686080";
		var options={
					browse_button: 'pickfiles',
					container: 'container',
					tigger_container:''}
		var uploader = new uploaderImg();
		uploader.createUploader(options);
		if(customer.message.goodId){
			var goodInfoObj=localStorage.getItem('goodInfoObj');
			if(goodInfoObj){
				var infoObj=JSON.parse(goodInfoObj);
				customer.message.infoObj=infoObj;
				var goodInfo=$('<div class="svc_good" >'
				+'<div class="svc_good_t">'
				+'<img src="'+infoObj.imgUrl+'" />'
				+'<div class="svc_good_t_r">'
				+'<h3>'+infoObj.name+'</h3>'
				+'<div>￥'+infoObj.salePrice+'</div>'
				+'</div>'
				+'</div>'
				+'<div class="svc_good_m" id="sendGoodBtn">发送宝贝信息</div>'
				+'</div>');
				$("#goodContent").append(goodInfo);
				$("#msgContent").css("margin-top","16.6rem");
			}
		}
		var url = Util.common.baseUrl + "/weixin/message/news/queryMessage.do";
		var param = {
			"clientId":customer.message.userId ,
			"storeId":customer.message.shopId
		};
		Util.common.executeAjaxCallback(url, param, function (data) {
			console.log(data);	
			if(data){
//				$("#pageTitle").val(data.storeName);
				Util.common.setWxTitle(data.storeName);
				customer.message.msgId=data.id;
				if(data.clientPic){
					customer.message.userImg=data.clientPic;
				}else{
					customer.message.userImg="images/test-img/details/svc_photo.jpg";
				}
//				if(data.storePic){
//					customer.message.cusImg=data.storePic;
//				}else{
				customer.message.cusImg="images/test-img/details/svc_photo2.jpg";
//				}
				if(data.contents){
					var contents=isShowDate(data.contents);
					data.contents=contents;
					for(var i=0;i<contents.length;i++){
						if(contents[i].senderId==customer.message.userId){
							if(contents[i].isShowTime){
								var dateTime=$('<div class="svc_time">'+showDate(contents[i].showTime)+'</div>');
								$("#msgContent").append(dateTime);
							}
							var userMsg=$('<div class="svc_left">'+
							'<div class="svc_left_l svc_right_l "><img src="'+customer.message.userImg+'" /></div>'+
							'<div class="svc_left_r rg_red"><i class="svc_right_icon"></i>'+contents[i].content+'</div></div>');
							
						}else{
							var userMsg=$('<div class="svc_left">'+
							'<div class="svc_left_l"><img src="'+customer.message.cusImg+'" /></div>'+
							'<div class="svc_left_r"><i class="svc_left_icon"></i>'+contents[i].content+'</div></div>');
						}
						$("#msgContent").append(userMsg);
					}
					setTimeout(function(){
						$("#bodyContent").scrollTop($("#msgContent").height()+500);
					},1000);
				}
				customer.message.msgData=data;
			}
		});
	}
}