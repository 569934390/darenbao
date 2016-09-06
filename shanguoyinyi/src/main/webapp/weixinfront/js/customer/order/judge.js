$(function(){
    customer.judge.init();
	$(".judge-main-score img").on("click", function(){
        var index = $(this).attr("index");
		var goodIndex = $(this).attr("goodIndex");
		$("#score_"+goodIndex).attr("value", parseInt(index)+1);
        //localStorage.setItem('score', parseInt(index)+1);
        $(this).parent().find("img").each(function(i){
            if(i<=index){
                $(this).attr("src","images/hdpi/wddd_pj_xx.png");
            }else{
                $(this).attr("src","images/hdpi/wddd_pj.png");
            }
        });
    });
    
});
var uploaderImg=function(){
	var default_options={
        runtimes: 'html5,flash,html4',
        browse_button:'',
        container: '',
        tigger_container:'',
        //chunk_size: '4mb',
		uptoken_url: Util.common.baseUrl+"/weixin/qiniu/getToken.do",
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
				var targetContainer=op.tigger_container;
                var progress = new FileProgress(file, targetContainer);
                var chunk_size = plupload.parseSize(this.getOption('chunk_size'));
                if (up.runtime === 'html5' && chunk_size) {
                    progress.setChunkProgess(chunk_size);
                }
            },
            'UploadComplete': function() {
                console.log("ssss");
            },
            'FileUploaded': function(up, file, info) {
                var op=up.getOption();
				var targetContainer=op.tigger_container;
                var progress = new FileProgress(file, targetContainer);
				progress.setComplete(up, info, "-img");
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
var customer = customer || {};
customer.judge = {
    init: function(){
        var data = Util.common.getParameter('goodModel');
        var result = JSON.parse(data);
        for(var i=0;i<result.length;i++) {
            result[i].num = i+1;
        }
        console.log(result);
        customer.judge.loadTemplate('#judge-info', '#good-info-t', result); 
    },
    loadTemplate: function (render, templateId, data) {
        $(render).html($(templateId).tmpl(data));
		setTimeout(function(){
			for(var i=0;i<data.length;i++) {
				var options={
					browse_button: 'pickfiles_'+data[i].num,
					container: 'container_'+data[i].num,
					tigger_container:'showImgContainer_'+data[i].num}
				var uploader = new uploaderImg();
				uploader.createUploader(options);
			}
		},1000);
    },
	GetImgList:function (container){
		var img=$("#"+container+" > div > .default_uploadImg");
		var imgList=[];
		for(var i=0; i<img.length;i++){
			var rel=$('#'+img[i].id).attr("img-rel");
			var src=$('#'+img[i].id).attr("src");
			if(!rel ||rel=='-1')
			   continue;
			if(src && (new RegExp("\\-img$").test(src))) 
				src = src.substring(0, src.length-4);
			imgList.push(src);   
		}
			console.log(imgList);
			return imgList;
		},  
	submit: function(){
        var userid = localStorage.getItem('userid');
        var username = localStorage.getItem('nickname');
        var url = Util.common.baseUrl+ "/weixin/good/addEvaluate.do";
        var model = JSON.parse(Util.common.getParameter('goodModel'));
		var isCheck=$('#nm-judge-radio').is(':checked');
		var pingList=[];
        for (var i = 0, len = model.length; i < len; i++) {
            var modelJson = {};
			var j=i+1;
            var goodSkuId = model[i].goodSkuId;
			var curScore=$("#score_"+j).attr("value");
            var score = curScore || 5;
            var content = $('.judge-content-dl textarea')[i].value;
            
            var  logoList=customer.judge.GetImgList("showImgContainer_"+j);

            modelJson.goodSkuid = goodSkuId;
            modelJson.user = userid;
            if(isCheck){
                modelJson.username = "匿名";
            } else {
                modelJson.username = username;
            }
            modelJson.content = content;
            modelJson.score = score;
            modelJson.indentId = model[i].indentId;
            //modelJson.logo1=logoList;
            var obj={
            	evaluate:modelJson,
            	logo1:logoList
            };
            pingList.push(obj);
        }
        if(pingList.length>0){
        	var param = {"modelJson": JSON.stringify(pingList)};
            console.log(JSON.stringify(param));
            Util.common.executeAjaxCallback(url, param, function (data) {
               console.log(data);
                if (data.success == true) {
                    Util.msg.show('msgId', "评价成功，跳转中...");
                  setTimeout(function () {
                        document.location.href = 'html/customer/order/index.html';
                    }, 2000);
                }
            });
        }
    }

};
