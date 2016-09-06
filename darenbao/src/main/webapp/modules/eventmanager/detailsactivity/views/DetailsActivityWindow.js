define([
	'text!modules/eventmanager/detailsactivity/templates/detailView.html',
	'modules/upload/Uploader',
    'Portal'
], function(detailViewTpl,uploader,Portal) {
	var options = {
	    height: $(window).height()*0.9,
        modal: false,
        draggable: true,
        content: detailViewTpl,
        autoResizable: true
    };
    var popup;
    
    var popWin = {};
    var init = function(action,editValue){
    	 popWin.activityPic_img = "activityPic_img_container";
         initImageData(action,editValue);
         initData(action,editValue);
    }
    var initData=function(action,editValue){
    	$.post(Portal.webRoot+'/event/eventActivity/findEventActivityById.do',{
   			id:editValue.id},function(result){
   				console.log(result);
   				$(".DetailsActivityWindow div[name=typeName]").html(result.typeName);
   				$(".DetailsActivityWindow div[name=title]").html(result.title);
   				$(".DetailsActivityWindow span[name=participation]").html(result.participation);
   				if(result.subbranchName==null||result.subbranchName==''){
   					$(".DetailsActivityWindow .subbranchNameDiv").hide();
   				}else{
   	   				$(".DetailsActivityWindow div[name=subbranchName]").html(result.subbranchName);
   				}
   				$(".DetailsActivityWindow div[name=activityTypeName]").html(result.activityTypeName);
   				$(".DetailsActivityWindow div[name=sponsorName]").html(result.sponsorName);
   				$(".DetailsActivityWindow div[name=sponsorTel]").html(result.sponsorTel);
   				$(".DetailsActivityWindow div[name=beginTime]").html(result.beginTime);
   				$(".DetailsActivityWindow div[name=endTime]").html(result.endTime);
   				$(".DetailsActivityWindow div[name=regStartTime]").html(result.regStartTime);
   				$(".DetailsActivityWindow div[name=regEndTime]").html(result.regEndTime);
   				$(".DetailsActivityWindow div[name=activityStatusName]").html(result.activityStatusName);
   				$(".DetailsActivityWindow div[name=createAddress]").html(result.createAddress);
   				$(".DetailsActivityWindow div[name=activityAddress]").html(result.activityAddress);
   				$(".DetailsActivityWindow div[name=pointNum]").html(result.pointNum);
   				$(".DetailsActivityWindow div[name=content]").html(result.content);
         });
    }
    var initImageData=function(action,editValue){
    	var date={};
    	if(editValue&&editValue.activityPic)
    		date=loadImageData(editValue.activityPic);
    	popWin.activityPicUploader = uploader.create({
			container: popWin.activityPic_img, 
			max_file_count: 99999,
			image_list: date ? date.images : [],
			read_only: true
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
  
	return {
		openAddWin:function(listView,action,editValue){
			
            popup = club.popup($.extend({}, options, {
                modal: true
            }))
            
            init(action,editValue);
            
            $('.DetailsActivityWindow button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'cancel-button': popup.close();break;
                }
            });
		}
	};
});