define([
	'text!modules/message/detail/templates/replyDetail.html',
	'modules/upload/Uploader',
    'Portal'
], function(replyDetailTpl,Uploader,Portal) {
	var options = {
        height: $(window).height()*0.8,
        modal: false,
        draggable: false,
        content: replyDetailTpl,
        autoResizable: true
    };
    var popup,page,limit = 10;
    var logoUploader, max_file_count = 1,image_list = [];
	return {
		reply:function(listView,value){
			page = 0;
			var loadImgUrl = function() {
				logoUploader = Uploader.create({
					container : "small_img_container",
					max_file_count : max_file_count,
					image_list : image_list
				});
			}
			var createHtml = function(name,text,type){
				//type 0:用户, 1:客服
				var html = '<div class="col-md-12">';
				if(type == 0){
					html += '<label class="col-md-12" style="text-align:left;color:green">'+name+'</label>';
					html += '<label class="col-md-10" style="text-align:left;max-width: 65%;float: left;"><div class="inputDiv">'+text+'</div></label>';
				}else{
					html += '<label class="col-md-12" style="text-align:right;color: blue;">'+name+'</label>';
					html += '<label class="col-md-2"></label>';
					html += '<label class="col-md-10" style="text-align:left;max-width: 65%;float: right;">'+text+'</label>';
				}
				html += '</div>';
				return html;
			}
			
			var loadDate = function(type){
				$.post(Portal.webRoot+'/message/news/contents.do',{messageId:value.id,startIndex:page*limit},function(result){
					if(result){
		            	page++;
	            		if(result.length < limit){
	            			$("#loadDate").html("");
	            		}
	            		var html = '';
	            		for(var i = result.length-1; i >= 0 ; i--){
	            			var content = result[i];
	            			if(content.senderId == value.clientId){
	                			html += createHtml(value.clientName,content.content,0);
	            			}else{
	                			html += createHtml('客服',content.content,1);
	            			}
	            		}
	            		//type 1:向上加载数据 0:初始化数据
	            		if(type){
	            			$("#outputContent").prepend(html);
	            		}else{
	            			$("#outputContent").append(html);
	            			$('#outputContent').scrollTop( $('#outputContent')[0].scrollHeight);
	            		}
	            	}
	            });
			}
			
            popup = club.popup($.extend({}, options, {
                modal: true
            }));
            
            $(".replyWindow").on('click.dismiss.data-api', '[data-dismiss]', function (e) {
            	listView.search();
            });
            
            $('.modal-title').html('回复  '+value.clientName);
            $('.replyWindow input[name=messageId]').val(value.id);
            $('.replyWindow input[name=senderId]').val(value.storeId);
            
            loadImgUrl();
            loadDate();
            
            $("#loadDate").click(function(){
                loadDate(1);
            });
            
            $('.replyWindow').keypress(function(e){ 
	            if(e.ctrlKey && e.which == 13 || e.which == 10) { 
	            	$('#inputContent button[action=send]').click();            	
                    $(this).body.focus(); 
	            }
            });
            $('#inputContent button[action=send]').click(function(){
            	var imgUrl = logoUploader.get()[0];
				var imgUrlValue = '';
				if(imgUrl != undefined && imgUrl.url != undefined){
					imgUrlValue = imgUrl.url;
				}
				if(imgUrlValue == ''){
					var content = $('#inputContent #messageContent').val();
	            	if(!content || content == ''){
	                    club.toast('warn', '回复内容不可为空！');
	            		return false;
	            	}
	            	var value = $('form[name=reply-form').form('value');
	            	value.content=content;
	            	value.type = 0;
            		value.sendType = 1;
	            	$.post(Portal.webRoot+'/message/news/add.do',{modelJson:JSON.stringify(value)},function(result){
	            		if (result.code == 1) {
	            			$("#outputContent").append(createHtml("客服",value.content,1));
	            			$('#outputContent').scrollTop( $('#outputContent')[0].scrollHeight );
	            			$('#inputContent #messageContent').val('');
	            		}
	            		else{
					       club.toast('error', result.msg);
				       }
	            	});
				}else{
					var value = $('form[name=reply-form').form('value');
	            	value.content='<img src="'+imgUrlValue+'" class="outputImg"/>';
	            	value.type = 1;
	            	$.post(Portal.webRoot+'/message/news/add.do',{modelJson:JSON.stringify(value)},function(result){
	            		if (result.code == 1) {
	            			$("#outputContent").append(createHtml("客服",value.content,1));
							image_list.splice(0, image_list.length);
				            loadImgUrl();	            			
	            			var content = $('#inputContent #messageContent').val();
	    	            	if(content){
	    	            		value.type = 0;
	    	            		value.content = content
	    	            		value.sendType = 1;
	    	            		$.post(Portal.webRoot+'/message/news/add.do',{modelJson:JSON.stringify(value)},function(result){
	    		            		if (result.code == 1) {
	    		            			$("#outputContent").append(createHtml("客服",value.content,1));
	    		            			$('#outputContent').scrollTop( $('#outputContent')[0].scrollHeight);
	    		            			$('#inputContent #messageContent').val('');
	    		            		}
	    		            		else{
	    						       club.toast('error', result.msg);
	    					       }
	    		            	});
	    	            	}else{
	    	            		$('#outputContent').scrollTop( $('#outputContent')[0].scrollHeight);
	    	            	}
	            		}
	            		else{
					       club.toast('error', result.msg);
				       }
	            	});
				}            	
            });
		}		
	};
});