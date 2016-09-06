define([
	'text!modules/eventmanager/addactivity/templates/addView.html',
	'modules/upload/Uploader',
    'Portal',
    'common/jslibs/club-desktop/third-party/ueditor/ueditor.all.new',
    'css!common/jslibs/club-desktop/third-party/ueditor/themes/default/css/ueditor'
], function(addViewTpl,uploader,Portal,UE) {
	var options = {
	    height: $(window).height()*0.9,
        modal: false,
        draggable: true,
        content: addViewTpl,
        autoResizable: true
    };
    var popup;
    var checkSubmit=0;
    
    var popWin = {};
    var init = function(action,editValue){
    	popWin.activityPic_img = "activityPic_img_container";
    	
    	 $('.AddActivityWindow input[field=datetime]').datetimepicker({
             format: 'yyyy-mm-dd hh:ii:ss'
         });
         var startTime = new Date(),endTime = new Date();
         startTime.setDate(startTime.getDate()-90);
         endTime.setDate(endTime.getDate()+1);
         $('.AddActivityWindow input[name=beginTime]').datetimepicker('value',club.dateutil.format(startTime,'yyyy-mm-dd hh:ii:ss'));
         $('.AddActivityWindow input[name=endTime]').datetimepicker('value',club.dateutil.format(endTime,'yyyy-mm-dd hh:ii:ss'));
         $('.AddActivityWindow input[name=regStartTime]').datetimepicker('value',club.dateutil.format(startTime,'yyyy-mm-dd hh:ii:ss'));
         $('.AddActivityWindow input[name=regEndTime]').datetimepicker('value',club.dateutil.format(endTime,'yyyy-mm-dd hh:ii:ss'));
    	
         initUeditor(action,editValue);
         initImageData(action,editValue);
         initType(action,editValue);
         initSubbranch(action,editValue);
         initData(action,editValue);
    }
    var initData=function(action,editValue){
		 if (action=='check-edit') {
	         console.info(editValue);
	         $('form[name=activity-form]').form('value',editValue);
	         $('form[name=activity-form] input[name=activityLoLa]').val(editValue.activityLongitude+","+editValue.activityLatitude);
	         $('form[name=activity-form] input[name=title]').attr("disabled", "disabled");
	         $('.modal-title').html('编辑活动部落信息');
	     }
    }
    var initUeditor=function(action,editValue){
 	   if(UE){
       	UE.delEditor('editorContent');
       }
   	   var editorContent = UE.getEditor('editorContent');
	   	if (action=='check-edit') {
	        console.info(editValue);
	        //ueditor编辑复制
	        editorContent.ready(function(){  
	        	editorContent.setContent(editValue.content);  
	        });  
	    }
    }
    var initImageData=function(action,editValue){
    	var date={};
    	if(editValue&&editValue.activityPic)
    		date=loadImageData(editValue.activityPic);
    	popWin.activityPicUploader = uploader.create({
			container: popWin.activityPic_img, 
			max_file_count: 99999,
			image_list: date ? date.images : []
    	});
    }
    var loadImageData = function(activityPic){
    	var result;
    	if(activityPic)
    		$.ajax({
    			url: Portal.webRoot+'/event/eventActivity/getImage.do', 
    			type: "post",
    			data: {groupId: activityPic},
    			async: false, 
    			success: function(data){
    				result = data;
    			}
    		});
    	return result || {};
    }
    var initType = function(action,editValue){
    	 var TypeStr="";
    	 if (action=='check-edit') {
    		if(editValue.type==1){
    			TypeStr+="<option value='1'>户外</option><option value='2'>室内</option>";
    		}else{
    			TypeStr+="<option value='1'>户外</option><option value='2' selected='selected'>室内</option>";
    		}
    	 }else{
    		TypeStr+="<option value='1'>户外</option><option value='2'>室内</option>";
    	 }
		 $(".AddActivityWindow select[name=type]").html(TypeStr);
		 
	   	 $.post(Portal.webRoot+'/event/activityType/findList.do',{},function(result){
			 var activityTypeStr="";
			 for(var i=0;i<result.length;i++){
				 if (action=='check-edit'&&result[i].id==editValue.activityTypeId) {
					 activityTypeStr+="<option value='"+result[i].id+"' selected='selected'>"+result[i].name+"</option>";
				 }else{
					 activityTypeStr+="<option value='"+result[i].id+"'>"+result[i].name+"</option>";
				 }
			 }
			 $(".AddActivityWindow select[name=activityTypeId]").html(activityTypeStr);
	     });
    }
    var initSubbranch = function(action,editValue){
    	$.post(Portal.webRoot+'/Subbranch/getSubbranchListForActivity.do',{},function(result){
    		var subbranchStr="<option value='0'>空</option>";
    		for(var i=0;i<result.length;i++){
    			if (action=='check-edit'&&result[i].id==editValue.subbranchId) {
    				subbranchStr+="<option value='"+result[i].id+"' selected='selected'>"+result[i].name+"</option>";
    			}else{
    				subbranchStr+="<option value='"+result[i].id+"'>"+result[i].name+"</option>";
    			}
    		}
    		$(".AddActivityWindow select[name=subbranchId]").html(subbranchStr);
    	});
    }
	return {
		openAddWin:function(listView,action,editValue){
			
            popup = club.popup($.extend({}, options, {
                modal: true
            }))
            
            init(action,editValue);
            
        	$('.AddActivityWindow .close').click(function(){
              	var t= club.confirm('您确定要关闭吗？');
              	t.result.then(function resolve(){
              		popup.close();
              	});
              	return false;
    		});
            $('.AddActivityWindow button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'save-button': 
                         $('form[name=activity-form]').isValid();
                         var isValid = $('form[name=activity-form]').isValid();
                         if (isValid) {
                            var value = $('form[name=activity-form]').form('value');
                            var modelJsonStr=JSON.stringify(value);
                            modelJson=eval('('+modelJsonStr+')');
                            modelJson.activityPic=popWin.activityPicUploader.get();
                      
                            var arr = ($('form[name=activity-form] input[name=activityLoLa]').val()).split(',');
                            if(arr.length<2){
                            	 club.toast('error', "经纬度格式失败，必须以逗号隔开！");
                            	 return false;
                            }
	                        if(arr[0]>180||arr[0]<-180||arr[1]>90||arr[1]<-90){
	                           	 club.toast('error', "请输入正确的经纬度！");
	                        	 return false;
                            }
	                        if(arr[0]==null||arr[1]==null||arr[0]==""||arr[1]==""||arr[0]=="null"||arr[1]=="null"){
	                           	 club.toast('error', "请输入正确的经纬度！");
	                        	 return false;
                           }
                            modelJson.activityLongitude=arr[0];
                            modelJson.activityLatitude=arr[1];
                            
                            if(checkSubmit==1){
                            	club.toast('info', "亲，您点击的太快了！");
                            }else{
                            	checkSubmit=1;
	                            $.post(Portal.webRoot+'/event/eventActivity/saveOrUpdateEventActivity.do',{modelJson:JSON.stringify(modelJson)},function(result){
	                            	if (result.success) {
	                                   // listView.callback.refreshNoCheck();
	                                    listView.callback.refreshCheck();
	                                    popup.close();
	                                }
	                                else{
	                                    club.toast('error', result.msg);
	                                }
	                            	checkSubmit=0;
	                            });
                            }
                         };
                         break;
                    case 'cancel-button': 
	                   	 var t= club.confirm('您确定要关闭吗？');
	        	       	 t.result.then(function resolve(){
	                    	popup.close();
	        	       	 });
                    	 break;
                    case 'clear-button': 
                        $('form[name=activity-form').form('clear');
                        init(action,editValue);
                        break;
                }
            });
		}
	};
});